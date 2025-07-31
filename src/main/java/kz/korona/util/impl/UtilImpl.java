package kz.korona.util.impl;

import kz.korona.util.StatService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Slf4j
@SpringBootApplication
@Data
public class UtilImpl implements CommandLineRunner, kz.korona.util.Util {
    private final StatService statService;
    private final Scanner scanner = new Scanner(System.in);
    private String defaultInputDataPath = "src/main/java/kz/korona/data/in1.txt";
    private String defaultOutputDataPath = "src/main/java/kz/korona/results/";
    private String prefix = "";
    private String defaultIntResults = "integers.txt";
    private String defaultFloatResults = "float.txt";
    private String defaultStringResults = "string.txt";
    private final Map<String, Runnable> actionMap = new HashMap<>();
    private final Map<String, String> stats = new HashMap<>();

    @Override
    public void proccess() {
        log.info("Application Started");
        init();
        while (true) {
            System.out.println("""
                    Меню:
                    1. Введите "-o" для установки пути сохранения результатов;
                    2. Введите "-p" для установки префикса файлов;
                    3. Укажите тип статистики: -s (краткая), -f (полная);
                    4. Введите "start" для сортировки строк;
                    5. Введите "Exit" для выхода из программы
                    6. Введите "-f" для получения полной статистики и -"s" для получения краткой статистики после сортировки строк;
                    """);
            String nextLine = scanner.nextLine();
            if (nextLine.equalsIgnoreCase("exit")) {
                break;
            }
            Runnable runnable = actionMap.get(nextLine);
            if (runnable != null) {
                runnable.run();
            } else {
                System.out.println("Invalid input");
            }

        }

    }


    private void init() {
        actionMap.put("-o", () -> {
            System.out.println("Введите путь:");
            String path = scanner.nextLine();
            if (path.endsWith("/")) {
                defaultOutputDataPath = path;
            } else {
                defaultOutputDataPath = path.concat("/");
            }
        });

        actionMap.put("-p", () -> {
            System.out.println("Введите префикс:");
            prefix = scanner.nextLine();
        });

        actionMap.put("-f", () -> {
            statService.setStatisticMode("-f");
            log.info("Установлен режим статистики \"-f\"");
        });

        actionMap.put("-s", () -> {
            statService.setStatisticMode("-s");
            log.info("Установлен режим статистики \"-s\"");
        });


        actionMap.put("start", () -> {
            log.info("Начата сортировка строк.");
            separateLinesAndShowStats();
        });
    }

    private void separateLinesAndShowStats() {
        try (BufferedReader reader = new BufferedReader(new FileReader(defaultInputDataPath))) {
            reader.lines().forEach(this::writeResults);
        } catch (Exception ex) {
            log.error("aSiKrZ9B :: {}", ex.getMessage());
        } finally {
            log.info("Сорттировка строк завершена");
        }
        statService.showStats();
        statService.resetStats();
    }

    private void writeResults(String line) {

        if (checkInt(line)) {
            write(line, defaultOutputDataPath.concat(prefix).concat(defaultIntResults));
            statService.intLineSetStats(line);
        } else if (checkFloat(line)) {
            write(line, defaultOutputDataPath.concat(prefix).concat(defaultFloatResults));
            statService.floatLineSetStats(line);
        } else {
            write(line, defaultOutputDataPath.concat(prefix).concat(defaultStringResults));
            statService.stringLineSetStats(line);
        }
    }


    private void write(String line, String path) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
            writer.write(line);
            writer.write(System.lineSeparator());
        } catch (Exception e) {
            log.error("CZkpAnI4ig :: error during writing data: {}", e.getMessage());
        }
    }

    private boolean checkFloat(String srt) {
        boolean result = false;
        try {
            Float.parseFloat(srt);
            result = true;
        } catch (NumberFormatException e) {
            log.error("Error parsing to float value");
        }
        return result;
    }

    private boolean checkInt(String srt) {
        boolean result = false;
        try {
            Integer.parseInt(srt);
            result = true;
        } catch (NumberFormatException e) {
            log.error("Error parsing to int value");
        }
        return result;
    }

    @Override
    public void run(String... args) throws Exception {
        proccess();
    }
}

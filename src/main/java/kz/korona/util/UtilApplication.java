package kz.korona.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;

@Slf4j
@SpringBootApplication
public class UtilApplication {
    public static final String DEFAULT_PATH = "src/main/java/kz/korona/data/in1.txt";
    public static final String DEFAULT_INT_RESULTS = "src/main/java/kz/korona/results/integers.txt";
    public static final String DEFAULT_FLOAT_RESULTS = "src/main/java/kz/korona/results/float.txt";
    public static final String DEFAULT_STRING_RESULTS = "src/main/java/kz/korona/results/string.txt";


    public static void main(String[] args) {
        log.info("Application Started");
        try (BufferedReader reader = new BufferedReader(new FileReader(DEFAULT_PATH))) {
            reader.lines().forEach(UtilApplication::writeResults);
        } catch (Exception ex) {
           log.error("aSiKrZ9B :: {}", ex.getMessage());
        }
    }

    private static void writeResults(String line) {
        if (checkInt(line)) {
            write(line, DEFAULT_INT_RESULTS);
        } else if (checkFloat(line)) {
            write(line, DEFAULT_FLOAT_RESULTS);
        } else {
            write(line, DEFAULT_STRING_RESULTS);
        }
    }

    private static void write(String line, String path) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
            writer.write(line);
            writer.write(System.lineSeparator());
        } catch (Exception e) {
            log.error("CZkpAnI4ig :: error during writing data: {}", e.getMessage());
        }
    }

    private static boolean checkFloat(String srt) {
        boolean result = false;
        try {
            Float.parseFloat(srt);
            result = true;
        } catch (NumberFormatException e) {
            log.error("Error parsing to float value");
        }
        return result;
    }

    private static boolean checkInt(String srt) {
        boolean result = false;
        try {
            Integer.parseInt(srt);
            result = true;
        } catch (NumberFormatException e) {
            log.error("Error parsing to int value");
        }
        return result;
    }
}

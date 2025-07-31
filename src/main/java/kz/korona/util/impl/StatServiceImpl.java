package kz.korona.util.impl;

import kz.korona.util.StatService;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class StatServiceImpl implements StatService {
    private int linesCount = 0;
    private int intMinValue = 0;
    private int intMaxValue = 0;
    private int intTotalValue = 0;
    private int averageIntValue = 0;
    private int intLinesCount = 0;
    private float floatMinValue = 0;
    private float floatMaxValue = 0;
    private float floatTotalValue = 0;
    private float averageFloatValue = 0;
    private int floatLinesCount = 0;
    private int stringMaxLength = 0;
    private int stringMinLength = Integer.MAX_VALUE;
    private String statisticMode = "-s";


    @Override
    public void stringLineSetStats(String line) {
        linesCount++;
        stringMaxLength = Math.max(stringMaxLength, line.length());
        stringMinLength = Math.min(stringMinLength, line.length());
    }

    @Override
    public void floatLineSetStats(String line) {
        float parsedFloat = Float.parseFloat(line);
        linesCount++;
        floatMinValue = Math.min(floatMinValue, parsedFloat);
        floatMaxValue = Math.max(floatMaxValue, parsedFloat);
        floatTotalValue = floatTotalValue + parsedFloat;
        floatLinesCount++;
        averageFloatValue = floatTotalValue / floatLinesCount;
    }

    @Override

    public void intLineSetStats(String line) {
        int parsedInt = Integer.parseInt(line);
        linesCount++;
        intMinValue = Math.min(intMinValue, parsedInt);
        intMaxValue = Math.max(intMaxValue, parsedInt);
        intTotalValue = intTotalValue + parsedInt;
        intLinesCount++;
        averageIntValue = intTotalValue / intLinesCount;
    }

    @Override
    public void showStats() {
        if (statisticMode.equalsIgnoreCase("-s")) {
            System.out.println(String.format("""
                    Количество записанных элементов: %s
                    """, linesCount));
        }
        if (statisticMode.equalsIgnoreCase("-f")) {
            System.out.printf("""
                            Статистика:
                            1.Количество записанных элементов: %d;
                            2.Сумма целых чисел: %d;
                            3. Максимальное целое число: %d;
                            4. Минимальное целое число: %d;
                            5. Среднее целое число: %d;
                            6. Количество целых чисел: %d;
                            7. Сумма чисел с плавающей точкой: %.3f;
                            8. Максимальное число с плавающей точкой: %.3f;
                            9. Минимальное число с плавающей точкой: %.3f;
                            10. Среднее число с плавающей точкой: %.3f;
                            11. Количество чисел с плавающей точкой: %d;
                            
                            """,
                    linesCount, intTotalValue, intMaxValue, intMinValue, averageIntValue, intLinesCount,
                    floatTotalValue, floatMaxValue, floatMinValue, averageFloatValue, floatLinesCount);
        }
    }

    public void resetStats() {
        linesCount = 0;

        intMinValue = Integer.MAX_VALUE;
        intMaxValue = Integer.MIN_VALUE;
        intTotalValue = 0;
        averageIntValue = 0;
        intLinesCount = 0;

        floatMinValue = Float.MAX_VALUE;
        floatMaxValue = Float.MIN_VALUE;
        floatTotalValue = 0f;
        averageFloatValue = 0f;
        floatLinesCount = 0;

        stringMaxLength = 0;
        stringMinLength = Integer.MAX_VALUE;
    }

}

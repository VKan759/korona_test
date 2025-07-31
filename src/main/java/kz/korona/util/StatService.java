package kz.korona.util;

public interface StatService {
    void stringLineSetStats(String line);

    void floatLineSetStats(String line);

    void intLineSetStats(String line);

    void showStats();

    void resetStats();
    void setStatisticMode(String statisticMode);
}

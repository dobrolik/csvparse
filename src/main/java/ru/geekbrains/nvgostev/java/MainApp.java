package ru.geekbrains.nvgostev.java;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MainApp {
    public static void main(String[] args) {
        AppData appData = new AppData(Paths.get("data.csv"));
        System.out.printf("data.csv:\n%s\n", appData.toString());

        AppData appData1 = new AppData(generateData(4,5));
        appData1.saveData(Paths.get("data1.csv"));
        System.out.printf("data1.csv:\n%s\n", appData1.toString());

    }

    public static List<String> generateData(int numberOfHeaders, int numberOfLines) {
        List<String> data = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 1; i < numberOfHeaders; ++i) {
            stringBuilder.append("Value ").append(i).append(";");
        }
        stringBuilder.append("Value ").append(numberOfHeaders);
        data.add(stringBuilder.toString());

        for (int i = 1; i < numberOfLines + 1; ++i) {
            stringBuilder = new StringBuilder();
            for (int j = 1; j < numberOfHeaders; ++j) {
                stringBuilder.append(i).append(j).append(";");
            }
            stringBuilder.append(i).append(numberOfHeaders);
            data.add(stringBuilder.toString());
        }
        return data;
    }
}

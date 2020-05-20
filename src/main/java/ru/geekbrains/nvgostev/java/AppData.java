package ru.geekbrains.nvgostev.java;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class AppData {
    private String[] header;
    private int[][] data;

    public void getData(List<String> data) throws RuntimeException {
        if (data.isEmpty()) {
            throw new RuntimeException("File is empty");
        }
        header = data.get(0).split(";");
        this.data = new int[data.size() - 1][header.length];

        for (int i = 1; i < data.size(); ++i) {
            String[] dataLine = data.get(i).split(";");
            if (dataLine.length != header.length) {
                throw new RuntimeException("Number of cells not equal to number of header columns");
            }
            for (int j = 0; j < dataLine.length; ++j) {
                this.data[i - 1][j] = Integer.parseInt(dataLine[j]);
            }
        }
    }

    public AppData(List<String> data) {
        try {
            getData(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AppData(Path path){
        read(path);
    }


    public void read(Path path) {
        read(path, Charset.defaultCharset());
    }

    public void read(Path path, Charset charset) {
        try {
            getData(Files.readAllLines(path, charset));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkData(){
        if(header == null || data == null){
            throw new NullPointerException("Data is empty");
        }
    }

    private String getHeader() {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0;i<header.length-1;++i){
            stringBuilder.append(header[i]).append(";");
        }
        stringBuilder.append(header[header.length-1]).append("\n");
        return stringBuilder.toString();
    }

    public void saveData(Path path) {
        try (PrintWriter out = new PrintWriter(new FileOutputStream(path.toFile()), true)) {
            checkData();
            out.println(getHeader());
            StringBuilder stringBuilder = new StringBuilder();
            for (int[] dataArr : data) {
                stringBuilder.setLength(0);
                for (int i = 0; i < dataArr.length; ++i) {
                    stringBuilder.append(dataArr[i]).append(";");
                }
                stringBuilder.append(dataArr[dataArr.length-1]).append("\n");
                out.println(stringBuilder.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        @Override
        public String toString() {
        checkData();
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i<header.length;++i){
            stringBuilder.append(header[i]).append(":\t");
            for (int[] values : data) {
                stringBuilder.append(values[i]).append(";");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}

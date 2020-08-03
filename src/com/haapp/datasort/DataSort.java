package com.haapp.datasort;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class DataSort {

    public static void main(String[] args) {

        List<String> data01 = Arrays.asList("1", "3", "2", "18", "9");
        List<String> data02 = Arrays.asList("17-01/15", "17-01/10", "24-16/32", "02-03/13", "79-04/18", "25-11/11");
        List<String> data03 = Arrays.asList("23-Л", "4-П", "12-К", "3-Ж");
        List<String> data04 = Arrays.asList("С-26", "К-5", "А-15");
        List<String> data05 = Arrays.asList("55/АР", "10/Д", "100/ДК", "99/ДО");

        System.out.println("Start: " + data01);
        System.out.println("Finish: " + sortedData(data01));
        System.out.println("________________________");

        System.out.println("Start: " + data02);
        System.out.println("Finish: " + sortedData(data02));
        System.out.println("________________________");

        System.out.println("Start: " + data03);
        System.out.println("Finish: " + sortedData(data03));
        System.out.println("________________________");

        System.out.println("Start: " + data04);
        System.out.println("Finish: " + sortedData(data04));
        System.out.println("________________________");

        System.out.println("Start: " + data05);
        System.out.println("Finish: " + sortedData(data05));
        System.out.println("________________________");
    }

    public static List<String> sortedData(List<String> data) {
        char splitLast = 0;
        char splitFirst = 0;
        int index = 0;
        String reg = "[1-9]+[0-9]*";
        for (String str : data) {
            if (str.matches(reg)) {
                data.sort(Comparator.comparingInt(Integer::parseInt));
                return data;
            }
            int indexStart = 0;
            splitFirst = 0;
            for (int i = 0; i < str.length(); i++) {
                if (str.substring(indexStart, i + 1).matches(reg)) {
                    index = indexStart;
                } else {
                    splitLast = str.charAt(i);
                    if (splitFirst == 0) {
                        splitFirst = str.charAt(i);
                    }
                    indexStart = i + 1;
                }
            }
        }
        if (index > 0) {
            String splLast = String.valueOf(splitLast);
            data.sort(Comparator.comparingInt(s -> Integer.parseInt(s.substring(s.lastIndexOf(splLast) + 1))));
        } else {
            String splFirst = String.valueOf(splitFirst);
            data.sort(Comparator.comparingInt(s -> Integer.parseInt(s.substring(0, s.lastIndexOf(splFirst)))));
        }
        return data;
    }
}

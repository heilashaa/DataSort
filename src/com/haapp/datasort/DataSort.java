package com.haapp.datasort;

import java.util.*;
import java.util.stream.Collectors;

public class DataSort {

    public static void main(String[] args) {

//        List<String> data01 = Arrays.asList("1", "3", "2", "18", "9");
//        List<String> data02 = Arrays.asList("17-01/15", "17-01/10", "24-16/32", "02-03/13", "79-04/18", "25-11/11");
//        List<String> data03 = Arrays.asList("23-Л", "4-П", "12-К", "3-Ж");
//        List<String> data04 = Arrays.asList("С-26", "К-5", "А-15");
//        List<String> data05 = Arrays.asList("55/АР", "10/Д", "100/ДК", "99/ДО");

        List<String> data01 = Arrays.asList("1", " 5");
        List<String> data02 = Arrays.asList("С", "К-5", "А-15");
        List<String> data03 = Arrays.asList("С 26", "К-5", "А-15");
        List<String> data04 = Arrays.asList("С-[26]", "К-5", "А-15");
        List<String> data05 = Arrays.asList("55 /АР", "10/Д", "100/ДК", "99/ДО");
        List<String> data06 = Arrays.asList(" 55/АР", "10/Д", "100/ДК", "99/ДО");

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

        System.out.println("Start: " + data06);
        System.out.println("Finish: " + sortedData(data06));
        System.out.println("________________________");
    }

    public static List<String> sortedData(List<String> data) {
        List<String> trimData = data.stream().map(s -> s
                .trim()                                     //если допустимы только начальные и конечные пробелы
                .replaceAll("\\s+", "")    //если допустимы любые пробелы
        ).collect(Collectors.toList());

        try {
            char splitLast = 0;
            char splitFirst = 0;
            int index = 0;

            Character[] splitLastTemp = new Character[data.size()];
            Character[] splitFirstTemp = new Character[data.size()];
            int dataCounter = 0;

            String reg = "[1-9]+[0-9]*";
            for (String str : trimData) {

                if (trimData.stream().filter(s -> s.matches(reg)).count() == trimData.size()) {
                    trimData.sort(Comparator.comparingInt(Integer::parseInt));
                    for (int j = 0; j < data.size(); j++) {
                        if (data.get(j).length() != trimData.get(j).length()) {
                            System.out.println("!Внимание! В элементе " + data.get(j) + " найдены и убраны лишние пробелы");
                        }
                    }
                    return trimData;
                }

                int indexStart = 0;
                splitFirst = 0;
                for (int i = 0; i < str.length(); i++) {
                    if (str.substring(indexStart, i + 1).matches(reg)) {
                        index = indexStart;
                    } else {
                        splitLast = str.charAt(i);
                        splitLastTemp[dataCounter] = splitLast;
                        if (splitFirst == 0) {
                            splitFirst = str.charAt(i);
                            splitFirstTemp[dataCounter] = splitFirst;
                        }
                        indexStart = i + 1;
                    }
                }
                dataCounter++;
            }

            for (int j = 0; j < data.size(); j++) {
                if (data.get(j).length() != trimData.get(j).length()) {
                    System.out.println("!Внимание! В элементе " + data.get(j) + " найдены и убраны лишние пробелы");
                }
            }

            if (index > 0) {
                Set<Character> hs = new HashSet<>(Arrays.asList(splitLastTemp));
                if (hs.size() == 1) {
                    String splLast = String.valueOf(splitLast);
                    trimData.sort(Comparator.comparingInt(s -> Integer.parseInt(s.substring(s.lastIndexOf(splLast) + 1))));
                } else {
                    throw new MyException(splitLastTemp,trimData);
                }
            } else {
                Set<Character> hs = new HashSet<>(Arrays.asList(splitFirstTemp));
                if (hs.size() == 1) {
                    String splFirst = String.valueOf(splitFirst);
                    trimData.sort(Comparator.comparingInt(s -> Integer.parseInt(s.substring(0, s.lastIndexOf(splFirst)))));
                } else {
                    throw new MyException(splitFirstTemp, trimData);
                }
            }
            return trimData;
        } catch (MyException ex) {
            System.out.println("Сортировка не осуществлена. Найдены различия в индексах для сортировки:");
            ex.printErrors();
        }
        return data;
    }

    private static class MyException extends Exception {

        private final Character[] splitArray;

        private final List<String> data;

        public MyException(Character[] splitArray, List<String> data) {
            this.data=data;
            this.splitArray=splitArray;
        }

        public void printErrors(){
            for (int i =0; i < splitArray.length; i++){
                System.out.println("разделитель: |" + splitArray[i] + "| элемент: " + data.get(i));
            }
        }
    }
}

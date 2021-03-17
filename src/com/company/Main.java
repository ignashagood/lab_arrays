package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        String path = "C:\\Users\\ignat\\Desktop\\arrays.txt";
        File file = new File(path);
        if (!file.exists()) {
            System.out.println("Файл не найден. Программа завершает работу с ошибкой.");
            return;
        }
        Scanner scanner = new Scanner(file);
        int sizeSetA, sizeSetB;

        try {
            sizeSetA = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException exception) {

            System.out.println("Первая строка в файле не содержит число. Программа завершает работу с ошибкой.");
            return;
        }

        String line = scanner.nextLine();
        int[] setA = convertStringToArray(sizeSetA, line);
        if(setA.length == 0) {
            System.out.println("Множество A пусто.");
            return;
        }

        System.out.print("Первое множество: ");
        for (int j : setA) System.out.print(j + " ");
        System.out.println();
        try {
            sizeSetB = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException exception) {
            System.out.println("Третья строка в файле не содержит число. Программа завершает работу с ошибкой.");
            return;
        }

        line = scanner.nextLine();
        int[] setB = convertStringToArray(sizeSetB, line);
        if(setB.length == 0) {
            System.out.println("Множество B пусто.");
            return;
        }
        System.out.print("Второе множество: ");
        for (int j : setB) System.out.print(j + " ");
        System.out.println();
        int[] setOfIntersections = intersectionSets(setA, setB);
        System.out.print("Множество пересечений: ");
        for (int j : setOfIntersections) System.out.print(j + " ");
        System.out.println();
        int[] combiningSets = unificationSets(setA, setB);
        System.out.print("Множество объединений: ");
        for (int j : combiningSets) System.out.print(j + " ");
        System.out.println();
        int[] differenceSets = subtractionSets(setA, setB);
        System.out.print("Множество разности: ");
        for (int j : differenceSets) System.out.print(j + " ");
        System.out.println();
        if(affiliationNumberToSet(9, setA)) System.out.println("Число принадлежит множеству");
        else System.out.println("Число не принадлежит множеству");

    }

    private static int[] convertStringToArray(int length, String line) {
        String[] numbers = line.split(" ");
        int[] array = new int[length];
        int counter = 0;
        outer:
        for (String number : numbers) {
            if (counter == length) break;
            int checker = Integer.parseInt(number);
            for (int i = 0; i < counter; i++) {
                if (array[i] == checker) continue outer;
            }
            array[counter++] = checker;
        }
        if (counter < length) {
            int[] finalArray = new int[counter];
            System.arraycopy(array, 0, finalArray, 0, counter);
            return finalArray;
        }
        return array;
    }

    private static int[] intersectionSets(int[] setA, int[] setB) {
        int k = 0, counter = 0;
        int[] supportArray = new int[Math.max(setA.length, setB.length)];
        for (int j : setA) {
            for (int value : setB) {
                if (j == value) {
                    counter++;
                    supportArray[k++] = value;
                }
            }
        }
        int[] finalArray = new int[counter];
        System.arraycopy(supportArray, 0, finalArray, 0, counter);
        if(counter == 0) {
            System.out.println("Пересечение множеств A и B пусто.");
            return finalArray;
        }
        else return finalArray;
    }

    private static int[] unificationSets(int[] setA, int[] setB) {
        int k = 0, counter = 0, sumOfLengths = setA.length + setB.length;
        int[] supportArray = new int[setA.length + setB.length];
        int[] mergedArray = new int[setA.length + setB.length];
        System.arraycopy(setA, 0, mergedArray, 0, setA.length);
        System.arraycopy(setB, 0, mergedArray, setA.length, setB.length);
        outer:
        for (int j = 0; j < sumOfLengths; j++) {
            if (counter == sumOfLengths) break;
            int checker = mergedArray[k++];
            for (int i = 0; i < counter; i++) {
                if (supportArray[i] == checker) continue outer;
            }
            supportArray[counter++] = checker;
        }
        int[] finalArray = new int[counter];
        System.arraycopy(supportArray, 0, finalArray, 0, counter);
        return finalArray;
    }

    private static int[] subtractionSets(int[] setA, int[] setB) {
        int[] subtractionSet = new int[setA.length];
        int counter = 0, i = 0, j = 0, k = 0;
        for(i = 0; i < setA.length; i++) {
            for (j = 0; j < setB.length; j++) {
                if (setA[i] != setB[j]) {
                    counter++;
                }
            }
            if(counter == setB.length) {
                subtractionSet[k] = setA[i];
                k++;
            }
            counter = 0;
        }
        int[] finalArray = new int[k];
        System.arraycopy(subtractionSet, 0, finalArray, 0, k);
        return finalArray;
    }

    private static boolean affiliationNumberToSet(int number, int[] set) {
        int counter = 0;
        for (int j : set) {
            if (number == j)
                counter++;
        }
        return counter != 0;
    }

}

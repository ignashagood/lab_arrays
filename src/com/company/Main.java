package com.company;

import java.io.File;
import java.io.FileNotFoundException;
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
        int a, b;

        try {
            a = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException exception) {

            System.out.println("Первая строка в файле не содержит число. Программа завершает работу с ошибкой.");
            return;
        }

        String line = scanner.nextLine();
        int[] array1 = convertStringToArray(a, line);

        System.out.print("Первый массив: ");
        for (int j : array1) System.out.print(j + " ");

        try {
            b = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException exception) {
            System.out.println("Третья строка в файле не содержит число. Программа завершает работу с ошибкой.");
            return;
        }
        line = scanner.nextLine();
        int[] array2 = convertStringToArray(b, line);
        System.out.print("Второй массив: ");
        for (int j : array2) System.out.print(j + " ");
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
}

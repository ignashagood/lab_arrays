package com.company;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        String path = "C:\\Users\\ignat\\Desktop\\arrays.txt";
        File file = new File(path);
        Scanner scanner = new Scanner(file);

        int[] array1 = new int[Integer.parseInt(scanner.nextLine())];
        String line = scanner.nextLine();
        String[] numbers = line.split(" ");
        int i, counter = 0;
        for(String number : numbers) {
            array1[counter++] = Integer.parseInt(number);
        }
        counter = 0;
        System.out.print("Первый массив: ");
        for(i = 0; i < array1.length; i++)
            System.out.print(array1[i] + " ");

        int[] array2 = new int[Integer.parseInt(scanner.nextLine())];
        line = scanner.nextLine();
        numbers = line.split(" ");
        for(String number : numbers) {
            array2[counter++] = Integer.parseInt(number);
        }
        System.out.print("Второй массив: ");
        for(i = 0; i < array2.length; i++)
            System.out.print(array2[i] + " ");
    }
}

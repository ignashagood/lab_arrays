package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static final String NEGATIVE_SIZE_ERROR = "Считан отрицательный размер множества. Программа завершает работу с ошибкой.";

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File(args[0]);
        if (!file.exists()) {
            System.out.println("Файл не найден. Программа завершает работу с ошибкой.");
            return;
        }
        Scanner scanner = new Scanner(file);
        int sizeSetA, sizeSetB;

        sizeSetA = extractSize(scanner.nextLine(), "A");
        if(sizeSetA == -1) return;

        String line = scanner.nextLine();
        int[] setA = convertStringToArray(sizeSetA, line);
        if (setA.length == 0) {
            System.out.println("Множество A пусто");
            return;
        }

        sizeSetB = extractSize(scanner.nextLine(), "B");
        if(sizeSetB == -1) return;

        line = scanner.nextLine();
        int[] setB = convertStringToArray(sizeSetB, line);
        if (setB.length == 0) {
            System.out.println("Множество B пусто");
            return;
        }

        System.out.println("Первое множество: " + Arrays.toString(setA));
        System.out.println("Второе множество: " + Arrays.toString(setB));
        System.out.println("Множество пересечений: " + Arrays.toString(intersectionSets(setA, setB)));
        System.out.println("Множество объединений: " + Arrays.toString(unificationSets(setA, setB)));
        System.out.println("Множество разности: " + Arrays.toString(subtractionSets(setA, setB)));

        if (affiliationNumberToSet(Integer.parseInt(args[1]), setA)) {
            System.out.println("Число " + args[1] + " принадлежит множеству");
        } else {
            System.out.println("Число " + args[1] + " не принадлежит множеству");
        }

        if (subset(setA, setB)) {
            System.out.println("Множество является подмножеством другого");
        } else {
            System.out.println("Множество не является подмножеством другого");
        }

        int[] universe = defineUniversalSet(setA, setB);
        System.out.println("Универсум: " + Arrays.toString(universe));
        System.out.println("Отрицание множества: " + Arrays.toString(defineNegationOfSet(setA, universe)));
    }

    /**
     * Извлекает из строки размер множества
     *
     * @param line Исходная строка
     * @return Размер множества, если >= 0, иначе -1
     */
    public static int extractSize(String line, String setName) {
        try {
            int size = Integer.parseInt(line);
            if (size < 0) {
                System.out.println(NEGATIVE_SIZE_ERROR);
                return -1;
            }
            if(size == 0) {
                System.out.println("Множество " + setName + " пусто");
                return -1;
            }
            return size;
        } catch (NumberFormatException exception) {
            System.out.println("Первая строка в файле не содержит число. Программа завершает работу с ошибкой.");
            return -1;
        }
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
        for (int i : setA) {
            for (int j : setB) {
                if (i == j) {
                    counter++;
                    supportArray[k++] = j;
                }
            }
        }
        int[] finalArray = new int[counter];
        System.arraycopy(supportArray, 0, finalArray, 0, counter);
        if (counter == 0) {
            System.out.println("Пересечение множеств A и B пусто.");
        }
        return finalArray;
    }

    private static int[] unificationSets(int[] setA, int[] setB) {
        int k = 0, counter = 0, sumOfLengths = setA.length + setB.length;
        int[] supportArray = new int[sumOfLengths];
        int[] mergedArray = new int[sumOfLengths];
        System.arraycopy(setA, 0, mergedArray, 0, setA.length);
        System.arraycopy(setB, 0, mergedArray, setA.length, setB.length);
        outer:
        for (int j = 0; j < mergedArray.length; j++) {
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
        int counter = 0, k = 0;
        for (int i : setA) {
            for (int j : setB) {
                if (i != j) {
                    counter++;
                }
            }
            if (counter == setB.length) {
                subtractionSet[k++] = i;
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

    private static boolean subset(int[] firstSet, int[] secondSet) {
        int coincidencesCounter = 0;
        for (int i : firstSet) {
            boolean coincidences = false;
            for (int j : secondSet) {
                if (i == j) {
                    coincidences = true;
                    break;
                }
            }
            if (coincidences) coincidencesCounter++;
        }
        return coincidencesCounter == firstSet.length;
    }

    private static int[] defineUniversalSet(int[] firstSet, int[] secondSet) {
        int[] universalSet = new int[firstSet.length + secondSet.length];
        System.arraycopy(firstSet, 0, universalSet, 0, firstSet.length);
        System.arraycopy(secondSet, 0, universalSet, firstSet.length, secondSet.length);
        for (int i = 0; i < universalSet.length; i++) {
            for (int j = 0; j < universalSet.length; j++) {
                if (universalSet[i] < universalSet[j]) {
                    int buffer = universalSet[i];
                    universalSet[i] = universalSet[j];
                    universalSet[j] = buffer;
                }
            }
        }
        return universalSet;
    }

    private static int[] defineNegationOfSet(int[] set, int[] universe) {
        int counter = 0, i = 0, count = 0, p = 0;
        int[] negationSet = new int[universe.length - set.length];
        for (int value : universe) {
            for (int j : set) {
                if (value == j) {
                    counter++;
                }
            }
            if (counter == 0) {
                negationSet[p] = value;
                p++;
            }
            counter = 0;
        }
        for (int value : set) {
            for (int j : universe) {
                if (value == j) {
                    counter++;
                }
            }
            for (i = 0; i < negationSet.length; i++)
                if (value == negationSet[i]) count++;
            if (counter >= 2 && count == 0) {
                negationSet[p] = value;
                p++;
            }
            counter = 0;
        }
        return negationSet;
    }
}

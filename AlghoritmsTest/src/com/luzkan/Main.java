package com.luzkan;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private static String fileName = "resultsSelectionSort";
    private static String sortedBy = "SelectionSort";
    private static int repetition = 1;
    private static int arraySize = 100;
    private static boolean silent = true;

    public static void main(String[] args) throws IOException {

        boolean toFile = false;
        boolean ascending = true;
        int sort = 1;
        // 1 - Selection Sort
        // 2 - Insertion Sort
        // 3 - Heap Sort
        // 4 - Quick Sort
        // 5 - Modified Quick Sort (using Median of Three and [elem<16] -> Insertion

        try {
            switch (args[1]) {
                case "asc":
                    ascending = true;
                    break;
                case "desc":
                    ascending = false;
                    break;
                default:
                    System.out.println("Please use \"asc\" or \"desc\" as a second argument. You typed in: " + args[1] + ".\nProgram will run with default run with ascending = " + ascending);
                    break;
            }
        }catch(IndexOutOfBoundsException e){
            System.out.println("Please type in \"asc\" or \"desc\" as a second argument. You typed nothing.\nProgram will run with default run with ascending = " + ascending);
        }

        try {
            switch (args[0]) {
                case "select":
                    sort = 1;
                    break;
                case "insert":
                    sort = 2;
                    break;
                case "heap":
                    sort = 3;
                    break;
                case "quick":
                    sort = 4;
                    break;
                case "mquick":
                    sort = 5;
                    break;
                default:
                    System.out.println("Please use \"select\", \"insert\", \"heap\" or \"quick\" as the first argument. You typed in: " + args[1] + ".\nProgram will run with default run with sort = " + sort);
                    break;
            }
        }catch(IndexOutOfBoundsException e){
            System.out.println("Please use \"select\", \"insert\", \"heap\" or \"quick\" as the first argument. You typed in nothing.\nProgram will run with default run with sort = " + sort);
        }

        if(args.length == 3){
            fileName = args[2].toLowerCase();
            toFile = true;
        }

        if(args.length == 4){
            try {
                repetition = Integer.parseInt(args[3]);
            }catch(Error e){
                System.out.println("Please type a number as the fourth argument. You typed in " + args[3] + ".\nProgram will run with default repetition = " + repetition);
            }
        }

        prepareToRun(sort, ascending, toFile);
    }


    private static void prepareToRun(int sortType, boolean ascending, boolean toFile) throws IOException {
        for (int k = 0; k < repetition; k++) {
            if(toFile){
                for (arraySize = 100; arraySize <= 10000; arraySize = arraySize + 100) {
                    runTypedSort(sortType, ascending, toFile);
                    //runAllSorts(ascending, toFile);
                }
            }else{
                runTypedSort(sortType, ascending, toFile);
            }
        }
    }

    private static void runAllSorts(boolean ascending, boolean toFile) throws IOException {
        int[] arrayToSort = arrayFrom(toFile);
        int[] copyOfArray = arrayToSort.clone();
        sortedBy = "SelectSort";
        runSelectSort(ascending, toFile, copyOfArray);
        copyOfArray = arrayToSort.clone();

        sortedBy = "InsertSort";
        runInsertSort(ascending, toFile, copyOfArray);
        copyOfArray = arrayToSort.clone();

        sortedBy = "HeapSort";
        runHeapSort(ascending, toFile, copyOfArray);

        sortedBy = "QuickSort";
        runQuickSort(ascending, toFile, arrayToSort);
    }

    private static void runTypedSort(int sortType, boolean ascending, boolean toFile) throws IOException {
        int[] arrayToSort = arrayFrom(toFile);
        switch (sortType) {
            case 1:
                runSelectSort(ascending, toFile, arrayToSort);
                break;
            case 2:
                runInsertSort(ascending, toFile, arrayToSort);
                break;
            case 3:
                runHeapSort(ascending, toFile, arrayToSort);
                break;
            case 4:
                runQuickSort(ascending, toFile, arrayToSort);
                break;
            case 5:
                runMQuickSort(ascending, toFile, arrayToSort);
        }
    }

    private static int[] arrayFrom(boolean toFile){
        int[] arrayToSort;

        if(toFile){
            arrayToSort = getRandomArray();
        }else{
            arrayToSort = scanForArgs();
        }

        return arrayToSort;
    }

    private static int[] getRandomArray() {
        Random r = new Random();

        int[] arrayToSort = new int[arraySize];
        for (int i = 0; i < arrayToSort.length; i++) {
            arrayToSort[i] = r.nextInt();
        }
        if (!silent) System.out.println(Arrays.toString(arrayToSort));

        return arrayToSort;
    }

    private static int[] scanForArgs(){
        Scanner s = new Scanner(System.in);
        System.out.println("Type in amount of arguments");
        int argsAmount = s.nextInt();
        System.out.println("Type in arguments");
        ArrayList<Integer> argsList = new ArrayList<>();

        int iteration = 0;
        while(s.hasNext()) {
            argsList.add(s.nextInt());
            iteration++;
            if (iteration == argsAmount) break;
        }

        return argsList.stream().mapToInt(Integer::intValue).toArray();
    }

    private static void runSelectSort(boolean ascending, boolean toFile, int[] arrayToSort) throws IOException {
        if(ascending) {
            SelectSort ss = new SelectSort();
            ss.sort(arrayToSort);
            if (!silent) ss.printArray(arrayToSort);
            if (toFile) usingBufferedWritter(ss.printInfo());
        }else{
            SelectSortDesc ss = new SelectSortDesc();
            ss.sort(arrayToSort);
            if (!silent) ss.printArray(arrayToSort);
            if (toFile) usingBufferedWritter(ss.printInfo());
        }
    }

    private static void runInsertSort(boolean ascending, boolean toFile, int[] arrayToSort) throws IOException {
        if(ascending) {
            InsertSort is = new InsertSort();
            is.sort(arrayToSort);
            if (!silent) is.printArray(arrayToSort);
            if (toFile) usingBufferedWritter(is.printInfo());
        }else{
            InsertSortDesc is = new InsertSortDesc();
            is.sort(arrayToSort);
            if (!silent) is.printArray(arrayToSort);
            if (toFile) usingBufferedWritter(is.printInfo());
        }
    }

    private static void runHeapSort(boolean ascending, boolean toFile, int[] arrayToSort) throws IOException {
        if(ascending){
            HeapSort hs = new HeapSort();
            hs.sort(arrayToSort);
            if (!silent) hs.printArray(arrayToSort);
            if (toFile) usingBufferedWritter(hs.printInfo());
        }else {
            HeapSortDesc hs = new HeapSortDesc();
            hs.sort(arrayToSort);
            if (!silent) hs.printArray(arrayToSort);
            if (toFile) usingBufferedWritter(hs.printInfo());
        }
    }

    private static void runQuickSort(boolean ascending, boolean toFile, int[] arrayToSort) throws IOException {
        if(ascending) {
            QuickSort qs = new QuickSort();
            qs.start(arrayToSort);
            if (!silent) qs.printArray(arrayToSort);
            if (toFile) usingBufferedWritter(qs.printInfo());
        }else{
            QuickSortDesc qs = new QuickSortDesc();
            qs.start(arrayToSort);
            if (!silent) qs.printArray(arrayToSort);
            if (toFile) usingBufferedWritter(qs.printInfo());
        }
    }

    private static void runMQuickSort(boolean ascending, boolean toFile, int[] arrayToSort) throws IOException {
        if(ascending) {
            MQuickSort mqs = new MQuickSort();
            mqs.sort(arrayToSort, 0, arrayToSort.length - 1);
            if (!silent) mqs.printArray(arrayToSort);
            if (toFile) usingBufferedWritter(mqs.printInfo());
        }else{
            MQuickSortDesc mqs = new MQuickSortDesc();
            mqs.sort(arrayToSort, 0, arrayToSort.length - 1);
            if (!silent) mqs.printArray(arrayToSort);
            if (toFile) usingBufferedWritter(mqs.printInfo());
        }
    }

    private static void usingBufferedWritter(String toWrite) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\Samorozwój\\Studia\\Semestr 4 WPPT Inf\\AiSD\\Lista2\\" + fileName + ".txt", true));
        writer.write(sortedBy + "\t" + arraySize + "\t" + toWrite + "\n");
        writer.close();
    }
}

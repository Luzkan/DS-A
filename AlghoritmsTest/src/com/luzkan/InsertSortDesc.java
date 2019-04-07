package com.luzkan;

class InsertSortDesc {

    private long start = 0, finish = 0;
    private int comparision = 0;
    private int swaps = 0;

    void sort(int[] arr) {
        start = System.nanoTime();
        int n = arr.length;

        comparision++;
        for (int i = 1; i < n; ++i) {
            comparision++;
            int key = arr[i];
            int j = i - 1;

            /* Move elements of arr[0..i-1], that are
               smaller than key, to one position ahead
               of their current position */
            comparision++;
            while (j >= 0 && arr[j] < key) {
                swaps++;
                arr[j + 1] = arr[j];
                j = j - 1;
                comparision++;
            }
            swaps++;
            arr[j + 1] = key;
        }
        finish = System.nanoTime();
    }

    void printArray(int[] arr){
        for (int value : arr) System.out.print(value + " ");
        System.out.println("\n>Comparisions: " + comparision + "\n>Swaps: " + swaps + "\n>Time Elapsed: " + (finish-start)/1000000 + "ms.");
    }

    String printInfo() {
        return (comparision + "\t" + swaps + "\t" + (finish-start));
    }
}

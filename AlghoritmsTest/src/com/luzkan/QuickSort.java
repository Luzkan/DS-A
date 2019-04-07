package com.luzkan;

class QuickSort {

    private long start = 0, finish = 0;
    private int comparision = 0;
    private int swaps = 0;

    /* This function takes last element as pivot,
       places the pivot element at its correct
       position in sorted array, and places all
       smaller (smaller than pivot) to left of
       pivot and all greater elements to right
       of pivot */

    private int partition(int[] arr, int low, int high) {

        int pivot = arr[high];
        int i = (low-1); // index of smaller element
        for (int j=low; j<high; j++) {

            // If current element is smaller than or
            // equal to pivot
            comparision++;
            if (arr[j] <= pivot) {
                i++;

                // swap arr[i] and arr[j]
                swaps++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        // swap arr[i+1] and arr[high] (or pivot)
        swaps++;
        int temp = arr[i+1];
        arr[i+1] = arr[high];
        arr[high] = temp;

        return i+1;
    }


    /* The main function that implements QuickSort()
      arr[] --> Array to be sorted,
      low  --> Starting index,
      high  --> Ending index */
    void sort(int[] arr, int low, int high) {
        if (low < high) {
            comparision++;
            /* pi is partitioning index, arr[pi] is
              now at right place */
            int pi = partition(arr, low, high);

            // Recursively sort elements before
            // partition and after partition
            sort(arr, low, pi-1);
            sort(arr, pi+1, high);
        }
    }

    void start(int [] arr){
        start = System.nanoTime();
        sort(arr, 0, arr.length - 1);
        finish = System.nanoTime();
    }

    /* A utility function to print array of size n */
    void printArray(int[] arr) {
        for (int value : arr) System.out.print(value + " ");
        System.out.println("\n>Comparisions: " + comparision + "\n>Swaps: " + swaps + "\n>Time Elapsed: " + (finish-start)/1000000 + "ms.");
    }

    String printInfo() {
        return (comparision + "\t" + swaps + "\t" + (finish-start));
    }
}
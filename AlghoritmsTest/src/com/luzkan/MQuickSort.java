package com.luzkan;

class MQuickSort {

    private long start = 0, finish = 0;
    private int comparision = 0;
    private int swaps = 0;

    /* This function takes last element as pivot,
       places the pivot element at its correct
       position in sorted array, and places all
       smaller (smaller than pivot) to left of
       pivot and all greater elements to right
       of pivot */

    private int partition(int[] arr, int low, int high, double median) {

        int leftCursor = low-1;
        int rightCursor = high;

        while(leftCursor < rightCursor){
            while(arr[++leftCursor] < median) comparision++;
            while(rightCursor > 0 && arr[--rightCursor] > median) comparision++;
            if(leftCursor >= rightCursor){
                comparision++;
                break;
            }else{
                swaps++;
                int temp = arr[leftCursor];
                arr[leftCursor] = arr[rightCursor];
                arr[rightCursor] = temp;
            }
        }
        swaps++;
        int temp = arr[leftCursor];
        arr[leftCursor] = arr[high];
        arr[high] = temp;

        return leftCursor;
    }

    /* The main function that implements QuickSort()
      arr[] --> Array to be sorted,
      low  --> Starting index,
      high  --> Ending index */
    void sort(int[] arr, int low, int high) {
        if (low < high) {
            comparision++;
            if (arr.length <= 16) {
                int n = arr.length;

                comparision++;
                for (int i = 1; i < n; ++i) {
                    comparision++;
                    int key = arr[i];
                    int j = i - 1;

            /* Move elements of arr[0..i-1], that are
               greater than key, to one position ahead
               of their current position */
                    comparision++;
                    while (j >= 0 && arr[j] > key) {
                        swaps++;
                        arr[j + 1] = arr[j];
                        j = j - 1;
                        comparision++;
                    }
                    swaps++;
                    arr[j + 1] = key;
                }
            } else {
                double median = medianOfThree(arr, low, high);
                /* pi is partitioning index, arr[pi] is
                 now at right place */
                int pi = partition(arr, low, high, median);
                // Recursively sort elements before
                // partition and after partition
                sort(arr, low, pi - 1);
                sort(arr, pi + 1, high);
            }
        }
    }

    private int medianOfThree(int[] arr, int low, int high) {
        int center = (low + high) / 2;

        comparision++;
        if (arr[low] > arr[center]) {
            swaps++;
            int temp = arr[low];
            arr[low] = arr[center];
            arr[center] = temp;
        }

        comparision++;
        if (arr[low] > arr[high]) {
            swaps++;
            int temp = arr[low];
            arr[low] = arr[high];
            arr[high] = temp;
        }

        comparision++;
        if (arr[center] > arr[high]) {
            swaps++;
            int temp = arr[center];
            arr[center] = arr[high];
            arr[high] = temp;
        }

        swaps++;
        int temp = arr[center];
        arr[center] = arr[high];
        arr[high] = temp;

        return arr[high];
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
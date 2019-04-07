package com.luzkan;

class SelectSort {

    private long start = 0, finish = 0;
    private int comparision = 0;
    private int swaps = 0;

    void sort(int[] arr) {
        start = System.nanoTime();
        int n = arr.length;

        comparision++;
        // One by one move boundary of unsorted subarray
        for (int i = 0; i < n-1; i++) {
            // Find the minimum element in unsorted array
            int min_idx = i;

            comparision++;
            for (int j = i+1; j < n; j++) {
                comparision++;
                if (arr[j] < arr[min_idx])
                    min_idx = j;
            }

            // Swap the found minimum element with the first element
            swaps++;
            int temp = arr[min_idx];
            arr[min_idx] = arr[i];
            arr[i] = temp;
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

// Time Complexity: O(n2)       (two nested loops)
// Memory: O(1)                 (never needs more memory than n)

/*
    arr[] = 64 25 12 22 11

    // Find the minimum element in arr[0...4]
    // and place it at beginning
            11 25 12 22 64

    // Find the minimum element in arr[1...4]
    // and place it at beginning of arr[1...4]
            11 12 25 22 64

    // Find the minimum element in arr[2...4]
    // and place it at beginning of arr[2...4]
            11 12 22 25 64

    // Find the minimum element in arr[3...4]
    // and place it at beginning of arr[3...4]
            11 12 22 25 64                      */
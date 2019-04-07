package com.luzkan;

class SelectSortDesc {

    private long start = 0, finish = 0;
    private int comparision = 0;
    private int swaps = 0;

    void sort(int[] arr) {
        start = System.nanoTime();
        int n = arr.length;

        comparision++;
        // One by one move boundary of unsorted subarray
        for (int i = 0; i < n-1; i++) {
            // Find the maximum element in unsorted array
            int max_idx = i;

            comparision++;
            for (int j = i+1; j < n; j++) {
                comparision++;
                if (arr[j] > arr[max_idx])
                    max_idx = j;
            }

            // Swap the found maximum element with the first element
            swaps++;
            int temp = arr[max_idx];
            arr[max_idx] = arr[i];
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

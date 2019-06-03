package com.luzkan;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import static com.luzkan.Util.BITS;
import static com.luzkan.Util.powerOfTwo;

class HyperCube {

    ArrayList<ArrayList<Integer>> cube;

    HyperCube(int k) {
        cube = new ArrayList<>(powerOfTwo(k));
        for(int i = 0; i < powerOfTwo(k); i++){
            cube.add(i, new ArrayList<Integer>(k));
            int weight = hammingWeight(i);
            int l = Math.max(weight+1, k-weight);
            for (int j = 0; j < k; j++){
                // If (((1*2) * j) mod i) is equal to 0
                if (((1 << j) & i) == 0) {
                    cube.get(i).add(j, ThreadLocalRandom.current().nextInt(0, powerOfTwo(l) + 1));
                }else{
                    cube.get(i).add(j, 0);
                }
                //System.out.println("" + i + " " + j + " " + cube.get(i).get(j));
            }
        }
        //System.out.println(cube);
        LinearProgrammingConverter.lpc(k, cube, "HyperCube");
    }

    // Takes an int and returns the number of ’1' bits it has
    // System.out.println("[DEBUG] Answer for HW(23) should be 4 [...10111]: " + hammingWeight(23));
    private int hammingWeight(int i){
        int c = 0;
        for (int n = 0; n < BITS; ++n) {
            if (((i >>> n) & 1) == 1) {
                ++c;
            }
        }
        return c;
    }


}

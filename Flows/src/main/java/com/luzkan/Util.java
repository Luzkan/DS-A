package com.luzkan;

import java.util.ArrayList;

class Util {

    static final double NANO_TO_SEC = 1000000000d;
    static final int BITS = 32;

    static int powerOfTwo(int k){
        return 1 << (k);
        //return (int) Math.pow (2, k);
    }

    // Returns 0 if bits = 0
    static int binlog(int bits) {
        int log = 0;
        if( ( bits & 0xffff0000 ) != 0 ) { bits >>>= 16; log = 16; }
        if( bits >= 256 ) { bits >>>= 8; log += 8; }
        if( bits >= 16  ) { bits >>>= 4; log += 4; }
        if( bits >= 4   ) { bits >>>= 2; log += 2; }
        return log + ( bits >>> 1 );
    }

    static Boolean search(ArrayList<Integer> innerArray, int searched){
        for(int i : innerArray)
            if(i == searched)
                return true;
        return false;
    }

}

package com.comcast.helloworld.util;

public final class FibonacciUtil {


    /**
     *
     * Fibonacci by recursion : Time complexity exponential
     * @param n
     * @return
     */
    public static Integer fibonacci(int n){
        if(n <=1) return n;
        return fibonacci(n - 1) + fibonacci( n - 2);
    }


    /**
     * Fibonacci by dynamic programming. Not computing the repeated work.
     * O(n)
     * @param n
     * @return
     */

    public static Integer fibonacciByDynamicProgramming(int n){
        int fibonacci[] = new int[n+1];
        fibonacci[0] = 0;
        fibonacci[1] = 1;

        for (int i = 2; i <= n; i++) {
            fibonacci[i] = fibonacci[i-1] + fibonacci[i-2];

        }

        return fibonacci[n];

    }


}

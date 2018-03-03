package com.comcast.helloworld;


import com.comcast.helloworld.util.FibonacciUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class FibonacciUtilTest {


    @Test
    public void fibonacciByRecursionTest(){
        assertThat(FibonacciUtil.fibonacci(5)).isEqualTo(5);
        assertThat(FibonacciUtil.fibonacci(9)).isEqualTo(34);
    }

    @Test
    public void fibonacciByDynamicProgrammingTest(){
        assertThat(FibonacciUtil.fibonacciByDynamicProgramming(5)).isEqualTo(5);
        assertThat(FibonacciUtil.fibonacciByDynamicProgramming(9)).isEqualTo(34);
    }



}

package com.comcast.helloworld.controller;


import com.comcast.helloworld.util.FibonacciUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class FibonacciController {


    /**
     * Uses recursion to calculate fibonacci series.
     * @param number
     * @return
     */
    @GetMapping("/fibonacci/r/{number}")
    public ResponseEntity<Integer> getFibonacciByRecursion(@PathVariable  Integer number){
        return ResponseEntity.ok(FibonacciUtil.fibonacci(number));
    }

    /**
     * Uses dynamic programming to calculate fibonacci series.
     * @param number
     * @return
     */
    @GetMapping("/fibonacci/d/{number}")
    public ResponseEntity<Integer> getFibonacciByDynamicProgramming(@PathVariable  Integer number){
        return ResponseEntity.ok(FibonacciUtil.fibonacciByDynamicProgramming(number));
    }



}

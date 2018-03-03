package com.comcast.helloworld.controller;

import com.comcast.helloworld.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DeadlockThreadController {

    Logger log = LoggerFactory.getLogger(this.getClass().getName());


    @GetMapping("/thread/deadlock")
    public ResponseEntity<String> makeTwoThreadDeadlock() throws InterruptedException {

        final String string1 = Constants.JAVA;
        final String string2 = Constants.SCALA;


        Thread javaThread = new Thread(() -> {

            try {
                synchronized (string1) {
                    log.info("Acquired lock on: " + string1);


                    Thread.sleep(4000);


                    synchronized (string2) {
                        log.info("Acquired lock on: " + string2);
                    }
                }
            }catch(InterruptedException e){
                log.info("javaThread was interrupted");

            }

        });

        Thread scalaThread = new Thread(() -> {
            try {
                synchronized (string2){
                    log.info("Acquired lock on " + string2);


                    Thread.sleep(4000);

                    synchronized (string1){
                        log.info("Acquired lock on " + string1);
                    }
                }
            }catch (InterruptedException e) {
                log.info("scala thread was interrupted");
            }
        });

        javaThread.start();
        log.info("java thread starting....");

        scalaThread.start();
        log.info("scala thread starting....");

        while(javaThread.isAlive()){
            log.info("java thread Still waiting...");
            javaThread.join(1000);
            if(javaThread.isAlive()){
                log.info("Java thread Tired of waiting!");
                javaThread.interrupt();
            }
        }
        log.info("java thread done.");

        while(scalaThread.isAlive()){
            log.info("Scala thread Still waiting...");
            scalaThread.join(1000);
            if(scalaThread.isAlive()){
                log.info("Scala thread Tired of waiting!");
                scalaThread.interrupt();
            }
        }
        log.info("scala thread done.");

        return ResponseEntity.ok().body("success");

    }








}

package com.test.task;

public class PrintTask implements Runnable {


    public PrintTask() {
        System.out.println("Task was intialized");
    }

    @Override
    public void run() {
        System.out.println("task done!!!");
    }
}

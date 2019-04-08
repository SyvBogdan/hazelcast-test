package com.test.task;

import java.io.Serializable;

public class PrintTask implements Runnable, Serializable {

    private static final long serialVersionUID = 306867678241899629L;

    public PrintTask() {
        System.out.println("Task was intialized");
    }

    @Override
    public void run() {

        try {
            Thread.sleep(5000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("task done!!!");
    }
}

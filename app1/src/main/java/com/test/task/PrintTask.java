package com.test.task;

import java.io.Serializable;

public class PrintTask implements Runnable, Serializable {

    private static final long serialVersionUID = 306867678241899629L;

    public PrintTask() {
        System.out.println("Task was intialized");
    }

    @Override
    public void run() {
        System.out.println("task done!!!");
    }
}

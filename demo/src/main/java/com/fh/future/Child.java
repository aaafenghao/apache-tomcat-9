package com.fh.future;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class Child implements Callable<Void> {

    private int time;

    public Child(int time){
        this.time = time;
    }
    public Void call() {
        System.out.println("start " + time);
        try {
            TimeUnit.SECONDS.sleep(time);
        }catch (Exception e){
            //ignore exception
        }
        System.out.println("end "+time);
        return null;
    }
}

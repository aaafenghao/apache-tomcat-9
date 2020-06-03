package com.fh.future;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureTest {

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        List<Future<Void>> result = new ArrayList<>();
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        for(int i=1; i<=10; i++){
            result.add(pool.submit(new Child(i)));
        }
        System.out.println("get result");
        for(Future<Void> future:result){
            try {
                future.get();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        System.out.println("end result");
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));


    }
}

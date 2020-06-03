package com.fh.future;

import org.junit.Test;

import java.util.Optional;

public class OptionsTest {

    @Test
    public void optionOf(){
        //会抛出空指针异常
        String message = null;
        Optional<String> message1 = Optional.of(message);
        //不会抛出异常
        Optional<String> message2 = Optional.ofNullable(message);
    }

    @Test
    public void getOrElse(){
        String message = "12";
        //有值的时候Else也会执行
//        Optional.ofNullable(message).orElse(get());
        //有值的时候不会执行
        Optional.ofNullable(message).orElseGet(OptionsTest::get);
    }

    public static String get(){
        System.out.println("11111");
        return "haha";
    }

    
}

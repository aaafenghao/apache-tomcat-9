package com.fh.future;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Test
    public void map(){
        List<Integer> re = new ArrayList();
        re.add(1);
        Optional.ofNullable(re).map(item ->{
           return 1;
        });
    }

    /**
     * 一对多
     */
    @Test
    public void flatMap(){
        List<String> demo1 = Arrays.asList("f","e","n","g");
        List<String> demo2 = Arrays.asList("h", "a", "o");
        List<List<String>> lists = new ArrayList<>();
        lists.add(demo1);
        lists.add(demo2);
        List<String> collect = lists.stream().flatMap(item -> item.stream()).collect(Collectors.toList());
        collect.stream().forEach(System.out::println);

    }

    
}

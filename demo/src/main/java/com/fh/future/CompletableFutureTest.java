package com.fh.future;



import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class CompletableFutureTest {


    @Test
    public void getOrJoin() {
        //主动完成
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(()
                -> {
            int i = 1 / 0;
            return 100;
        });
//        future.get(10, TimeUnit.SECONDS);
          future.join();
//        future.getNow(10);
//        future.get();
    }

    @Test
    public void complete(){
        //设置完成值
        CompletableFuture<Integer> future = new CompletableFuture<>();
        future.complete(100);
        future.completeExceptionally(new Exception(""));
    }

    @Test
    public void createBean(){
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.completedFuture("1");
        //创建CompletableFuture对象
        //有返回值
        CompletableFuture.supplyAsync(() ->{
            return 0;
        });
        //无返回值
        CompletableFuture.runAsync(() ->{

        });
    }

    @Test
    public void compleBack() throws Exception{
        //计算结果完成时的处理
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            return 1;
        }).whenComplete((i, throwable) -> {//使用相同的线程执行
            System.out.println(i);
            System.out.println(throwable);
        }).exceptionally((throwable -> {//异常的处理
            return 0;
        }));
        System.out.println(future.get());


    }

    @Test
    public void handle() throws Exception{
        //线程运行完成后对结果的处理
        CompletableFuture<Integer> handle = CompletableFuture.supplyAsync(
                () -> 1 * 2).handle((t,throwable) -> {
            return t;
        });
        System.out.println(handle.get());
    }

    @Test
    public void apply() throws Exception{
        //可以进行类型转换
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
            return 1;
        }).thenApply(item -> String.valueOf(item));
        System.out.println(stringCompletableFuture.get());
    }

    /**
     * 纯消费
     * @throws Exception
     */
    @Test
    public void accept() throws Exception{
        //消费上一个线程处理的结果
        CompletableFuture.supplyAsync(() ->{
            return 1;
        }).thenAccept(item -> System.out.println(item));

        CompletableFuture.supplyAsync(() ->{
            return 1;
        }).thenAcceptBoth(CompletableFuture.supplyAsync(() ->{
            return 1;
        }),(x,y) -> System.out.println(x+y));

        CompletableFuture.supplyAsync(() ->{
            return 1;
        }).runAfterBoth(CompletableFuture.supplyAsync(() ->{
            return 1;
        }),() ->{
            //不使用上面两个线程的计算结果
        }).thenRun(() ->{
            //不使用计算结果
        });
    }

    /**
     * 组合
     */
    @Test
    public void compose(){
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            return 2;
        });
        //入参是个Function
        future.thenCompose(i ->{
            return CompletableFuture.supplyAsync(() ->{
                return i *2;
            });
        });

        CompletableFuture<Integer> combine1 = CompletableFuture.supplyAsync(() -> {
            return 2;
        });
        CompletableFuture<Integer> combine2 = CompletableFuture.supplyAsync(() -> {
            return 3;
        });
        //入参是个CompletableFuture
        combine1.thenCombine(combine2,(x,y) ->{
            return x+y;
        });
    }

    /**
     * 任意一个计算完成的时候,执行下一个
     */
    @Test
    public void either(){
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            return 0;
        });
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            return 1;
        });
        //不需要返回值
        future.acceptEither(future1,i ->{
            System.out.println(i);
        });
        //需要返回值
        future.applyToEither(future1,i ->{
            System.out.println(i);
            return i;
        });
    }

    /**
     * 批量相关
     */
    @Test
    public void AllAndAny(){
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            return 0;
        });

        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            return 1;
        });
        //全部完成的时候
        CompletableFuture.allOf(future,future1);
        //人任何一个完成的时候
        CompletableFuture.anyOf(future,future1);
    }

    @Test
    public void test() throws Exception{
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {

            }
            return 1;
        });
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (Exception e) {

            }
            return 2;
        });
        List<CompletableFuture<Integer>> data = new ArrayList<>();
        data.add(future);
        data.add(future1);
        CompletableFuture<List<Integer>> all = CompletableFutureTest.all(data);
        List<Integer> list = all.get();
        list.stream().forEach(System.out::println);
    }

    public static <T> CompletableFuture<List<T>> all(List<CompletableFuture<T>> data){
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(data.toArray(new CompletableFuture[data.size()]));
        return voidCompletableFuture.thenApply(v ->data.stream().map(CompletableFuture::join).collect(Collectors.toList()));
    }





}

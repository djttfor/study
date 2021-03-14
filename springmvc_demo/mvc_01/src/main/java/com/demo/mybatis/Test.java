package com.demo.mybatis;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Test
{
    public static Integer count =0;
    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 15, 60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(20));
        Object o = new Object();
        for (int i = 0; i < 30; i++) {
            int k = i;
            executor.execute(()->{
                if(k==6){
                    throw new RuntimeException("我异常了");
                }
               synchronized (o){
                   for (int j = 0; j < 100000; j++) {
                       count ++;
                   }
               }
            });
        }
        executor.shutdown();
        while(executor.getActiveCount()>0){Thread.yield();}
        System.out.println("main:"+count);
    }

}



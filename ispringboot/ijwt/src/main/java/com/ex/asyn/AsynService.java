package com.ex.asyn;

import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AsynService {
    @SneakyThrows
    @Async
    public void async(){
        Thread.sleep(3000);
        System.out.println("今晚打老虎");
    }
}
class B{
    public static void main(String[] args) {
        new Thread(()->{
            System.out.println("今晚打老虎");
        },"T1").start();
    }
}

package com.ex.work.polling;

import static com.ex.routing.Consumer.consum;

public class Work1 {
    public static void main(String[] args) {
        consum("queue1","work1",200L);
    }
}

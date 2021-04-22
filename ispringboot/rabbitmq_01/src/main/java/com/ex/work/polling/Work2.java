package com.ex.work.polling;

import static com.ex.routing.Consumer.consum;

public class Work2 {
    public static void main(String[] args) {
        consum("queue1","work2",100L);
    }
}

package com.ex.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TaskService {
    @Scheduled(cron = "7 * * * * ?")
    public void task(){
        log.info("今晚打老虎");
    }
}

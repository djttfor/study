package com.ex.server.dto;

import lombok.Data;

@Data
public class RabbitProducerBean {
    private String msgId;
    private Integer status;
    private Integer retryCount;
    private Object data;
    public RabbitProducerBean (){}


    public RabbitProducerBean(String msgId, Integer status, Integer retryCount, Object data) {
        this.msgId = msgId;
        this.status = status;
        this.retryCount = retryCount;
        this.data = data;
    }
}

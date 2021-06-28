package com.ex.server.rabbit;

public interface RabbitConstant {
    /**
     * 邮件交换机
     */
    String MAIL_EX = "mailExchange";
    /**
     * 邮件队列
     */
    String MAIL_QUEUE = "mailQueue";
    /**
     * 邮件路由key
     */
    String MAIL_ROUTING_KEY = "mail.*";
    /**
     * 欢迎新员工的路由key
     */
    String MAIL_WELCOME = "mail.welcome";
    /**
     * 邮件死信交换机名
     */
    String MAIL_DEAD_EX = "mailDeadExchange";
    /**
     * 邮件死信队列
     */
    String MAIL_DEAD_QUEUE = "mailDeadQueue";
    /**
     * 邮件死信路由key
     */
    String MAIL_DEAD_ROUTING_KEY = "mailDeadRoutingKey";

    /**
     * 最大重试次数
     */
    Integer MAX_RETRY_COUNT = 3;
    /**
     * 消息初始状态
     */
    Integer MESSAGE_INITIAL = 0;
    /**
     * 消息发送成功
     */
    Integer MESSAGE_SUCCESS = 1;
    /**
     * 消息发送失败
     */
    Integer MESSAGE_FAIL = 2;
    /**
     * 消息发送异常
     */
    Integer MESSAGE_EXCEPTION = 3;
    /**
     * redis消息消费确认key
     */
    String MESSAGE_CONSUMER_CONFIRM = "messageConfirm";
}

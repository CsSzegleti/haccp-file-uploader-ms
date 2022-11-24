package io.c0dr.fileuploader.controller;

import org.springframework.beans.factory.annotation.Value;

public abstract class MessageConverter {

    @Value("${amqp.file-upload.queue.response}")
    protected String responseQueueName;

    @Value("${amqp.file-upload.queue.request}")
    protected String requestQueueName;

}

package io.c0dr.fileuploader.controller;

import org.springframework.beans.factory.annotation.Value;

public abstract class MessageConverter {

    @Value("${amqp.document-factory-manager.queue.response}")
    protected String responseQueueName;

    @Value("${amqp.document-factory-manager.queue.request}")
    protected String requestQueueName;

}

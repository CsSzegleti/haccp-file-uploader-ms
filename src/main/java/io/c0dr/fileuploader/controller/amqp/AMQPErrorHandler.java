package io.c0dr.fileuploader.controller.amqp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.stereotype.Component;


@Component(value = "globalAMQPErrorHandler")
@Slf4j
public class AMQPErrorHandler implements RabbitListenerErrorHandler {

    public AMQPErrorHandler(){
        log.info("Create instance: " + getClass().getName());
    }

    @Override
    public Object handleError(Message amqpMessage, org.springframework.messaging.Message<?> springMessage,
                              ListenerExecutionFailedException exc) {

//        exc.getCause(); the original exception
        log.error(exc.getMessage());
        return null;
    }



}

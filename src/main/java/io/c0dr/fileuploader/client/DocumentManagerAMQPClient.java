package io.c0dr.fileuploader.client;

import io.c0dr.fileuploader.config.RequestMetaDataBean;
import io.c0dr.fileuploader.controller.rest.config.HeaderNames;
import io.c0dr.fileuploader.service.model.DocFileModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DocumentManagerAMQPClient {

    private final RabbitTemplate rabbitTemplate;

    public void sendRabbitMessage(DocFileModel docFileModel, RequestMetaDataBean pCom){
        rabbitTemplate.convertAndSend(pCom.getResponseQueueName(), docFileModel, message -> {
            message.getMessageProperties().setHeader(HeaderNames.CORRELATION_ID, pCom.getCorrelationId());
//            message.getMessageProperties().setHeader(AmqpHeaders.REPLY_TO, );
            message.getMessageProperties().setHeader(HeaderNames.USER_NAME, pCom.getUserName() == null
                    ? HeaderNames.NOT_DEF : pCom.getUserName());
            return message;
                });
        log.info("Rabbit message sent. DocFileModel: " + docFileModel + ", " +
                HeaderNames.CORRELATION_ID + ": " + pCom.getCorrelationId());
    }
}

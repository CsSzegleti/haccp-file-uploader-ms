package io.c0dr.fileuploader.client;

import io.c0dr.fileuploader.config.RequestMetaDataBean;
import io.c0dr.fileuploader.controller.rest.config.HeaderNames;
import io.c0dr.fileuploader.service.model.FileModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileManagerAMQPClient {

    private final RabbitTemplate rabbitTemplate;

    @Value("${amqp.file-upload.queue.request}")
    private String fileManagerRequestQueue;

    public void sendRabbitMessage(FileModel fileModel, RequestMetaDataBean pCom){
        rabbitTemplate.convertSendAndReceive(fileManagerRequestQueue, fileModel, message -> {
            message.getMessageProperties().setHeader(HeaderNames.CORRELATION_ID, pCom.getCorrelationId());
            message.getMessageProperties().setHeader(HeaderNames.USER_NAME, pCom.getUserName() == null
                    ? HeaderNames.NOT_DEF : pCom.getUserName());
            return message;
                });
        log.info("Rabbit message sent. FileModel: " + fileModel + ", " +
                HeaderNames.CORRELATION_ID + ": " + pCom.getCorrelationId());
    }
}

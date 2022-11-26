package io.c0dr.fileuploader.controller.amqp;

import io.c0dr.fileuploader.service.FileManagerFacade;
import io.c0dr.fileuploader.service.model.DFMUploadResponseBD;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AMQPController {

    @Autowired
    private FileManagerFacade fileService;

    public AMQPController() {
        log.info("Create instance: "+this.getClass().getName());
    }

    @RabbitListener(
            queues = "${amqp.file-upload.queue.response}",
            errorHandler = "globalAMQPErrorHandler")
    public void isUploadOk(DFMUploadResponseBD uploadResponse){
        fileService.manageFileUploadResponse(uploadResponse);
    }
}

package io.c0dr.fileuploader.controller.rest.converter;

import io.c0dr.fileuploader.config.RequestMetaDataBean;
import io.c0dr.fileuploader.controller.MessageConverter;
import io.c0dr.fileuploader.service.model.ExtraData;
import io.c0dr.fileuploader.service.model.FileModelBD;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;


@Component
@Slf4j
public class RestMessageConverter extends MessageConverter {

    @Autowired
    RequestMetaDataBean communicationMetaDataBean;

    public FileModelBD convert(byte[] data, String originalFileName,
                               ExtraData extraData, HttpServletRequest request) {
        FileModelBD fileModelBD = FileModelBD.builder()
                .data(data)
                .originalFileName(originalFileName)
                .extraData(extraData)
                .uploadIp(request.getRemoteAddr())
                .uploader(request.getUserPrincipal() == null ? null : request.getUserPrincipal().getName())
                .build();
        fileModelBD.setUserName(communicationMetaDataBean.getUserName());
        fileModelBD.setCorrelationId(communicationMetaDataBean.getCorrelationId());
        fileModelBD.setResponseQueueName(responseQueueName);
        return fileModelBD;
    }
}

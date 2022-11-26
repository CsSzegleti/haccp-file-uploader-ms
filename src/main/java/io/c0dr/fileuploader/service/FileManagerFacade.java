package io.c0dr.fileuploader.service;


import io.c0dr.fileuploader.client.FileManagerAMQPClient;
import io.c0dr.fileuploader.service.exception.SecurityConstraintException;
import io.c0dr.fileuploader.service.exception.WriteException;
import io.c0dr.fileuploader.service.model.DFMUploadResponseBD;
import io.c0dr.fileuploader.service.model.FileModel;
import io.c0dr.fileuploader.service.model.FileModelBD;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

@Component
@Slf4j
@Scope(value="prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
public class FileManagerFacade {

    public FileManagerFacade() {
        log.info("Create instance: " + getClass().getName());
    }

    @Autowired
    private FileManagerAMQPClient messageSender;

    @Autowired
    private OWASPChecker owaspChecker;


    /**
     * 1. Validate filename
     * 2. Save file to tmp location
     * 2. OWASP checks
     * 3. Get additional data of the file
     * 4. Save file to final location
     * 5. Send message to DFM
     * @param fileModelBD file with its additional data
     */
    public void saveFile(FileModelBD fileModelBD)
            throws SecurityConstraintException, WriteException, IOException {

        String fileName = owaspChecker.validateFileName(Objects.requireNonNull(fileModelBD.getOriginalFileName()));
        fileModelBD.setOriginalFileName(fileName);

        File file = owaspChecker.saveToTmpLocation(fileModelBD.getData(), fileModelBD.getOriginalFileName());

        HashMap<String, String> metadata = fileModelBD.getExtraData() == null ? null : fileModelBD.getExtraData().getData();
        FileModel fileModel = owaspChecker.fillDocFileModel(metadata, file);

        fileModel.setFileName(fileModelBD.getOriginalFileName());
        fileModel.setUploader(fileModelBD.getUploader());
        fileModel.setUploadIp(fileModelBD.getUploadIp());

        try {
            owaspChecker.checkFile(new File(file.toURI()));
            owaspChecker.copyToPermanentLocation(file);
        } finally {
            owaspChecker.deleteFile(file);
        }

        messageSender.sendRabbitMessage(fileModel, fileModelBD);
    }

    public void manageFileUploadResponse(DFMUploadResponseBD response) {
        owaspChecker.manageFileUploadResponse(response);
    }

}

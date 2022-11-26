package io.c0dr.fileuploader.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.Map;

@Builder
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class FileModel implements Serializable {
    Integer id;

    String fileName;

    String fileExtension;

    Long fileSize;

    String fileHash;

    Instant uploadedAt;

    String uploader;

    String uploadIp;

    Boolean isVirusCheck;

    String relativePath;

    Map<String, String> metaData;

}

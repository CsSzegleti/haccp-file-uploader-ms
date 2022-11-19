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
public class DocFileModel implements Serializable {
    String fileName;

    String fileExtension;

    Long fileSize;

    String documentHash;

    Instant uploadedAt;

    String uploader;

    String uploadIp;

    Boolean isVirusCheck;

    Instant examinedAt;

    Integer examinedBy;

    Boolean isApproved;

    String rejectReason;

    String relativePath;

    PasswordProtection passwordProtection;

    Map<String, String> metaData;

}

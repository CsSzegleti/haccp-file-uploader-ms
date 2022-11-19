package io.c0dr.fileuploader.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class DFMUploadResponseBD {
    private boolean isOk;
    private String fileName;
    private Integer documentId;
}

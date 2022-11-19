package io.c0dr.fileuploader.controller.rest;


import io.c0dr.fileuploader.controller.rest.config.HeaderNames;
import io.c0dr.fileuploader.controller.rest.config.PathConstants;
import io.c0dr.fileuploader.controller.rest.converter.RestMessageConverter;
import io.c0dr.fileuploader.service.FileManagerFacade;

import io.c0dr.fileuploader.service.exception.ApiError;
import io.c0dr.fileuploader.service.exception.SecurityConstraintException;
import io.c0dr.fileuploader.service.exception.WriteException;
import io.c0dr.fileuploader.service.model.ExtraData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Encoding;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
public class FileUploaderController {

    private final HttpServletRequest request;
    private final FileManagerFacade fileService;
    private final RestMessageConverter converter;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sikeres file feltöltés"),
            @ApiResponse(responseCode = "400", description = "Nem megfelelő kérés paraméterek (validációs hiba)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)) }),
            @ApiResponse(responseCode = "500", description = "Sikertelen file feltöltés (belső rendszer hiba)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)) })
    })
    @Operation(summary = "File feltöltés")
    @PostMapping(value = PathConstants.V1_FILE_UPLOAD, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE} )
    public HttpStatus uploadFile(
            @Parameter(description = "A valós file", style = ParameterStyle.FORM)
            @NotNull(message = "error.field.file.null")  @RequestPart(value = "file")
            MultipartFile file,

            @Parameter(description = "Correlation ID")
            @NotNull(message = "error.field.file.null")
            @NotBlank(message = "error.field.file.blank")
            @NotEmpty(message = "error.field.file.empty")
            @RequestHeader(value = HeaderNames.CORRELATION_ID) String correlationId,
            @RequestHeader(name = HeaderNames.USER_NAME) String user,

            @Parameter(description = "File titkosításhoz szükséges adatok",
                    style = ParameterStyle.FORM,
                    content = @Content(
                            encoding = @Encoding(contentType = MediaType.APPLICATION_JSON_VALUE)),
                    ref = "#/components/schemas/ExtraData")
            @RequestPart(value = "extraData", required = false)
            ExtraData extraData
    ) throws WriteException, SecurityConstraintException, IOException {
        fileService.saveFile(converter.convert(file.getBytes(), file.getOriginalFilename(), extraData, request));

        return HttpStatus.OK;
    }
}

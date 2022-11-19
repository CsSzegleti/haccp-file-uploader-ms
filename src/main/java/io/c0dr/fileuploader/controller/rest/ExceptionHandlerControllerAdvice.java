package io.c0dr.fileuploader.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.c0dr.fileuploader.service.exception.SecurityConstraintException;
import io.c0dr.fileuploader.service.exception.WriteException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice(assignableTypes = {FileUploaderController.class})
@Slf4j
@RequiredArgsConstructor
public class ExceptionHandlerControllerAdvice {

    private final ObjectMapper objectMapper;

    private final HttpServletRequest req;

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> HandleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        log.error(ex.getLocalizedMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
    }

    @ExceptionHandler({SecurityConstraintException.class})
    public ResponseEntity<Object> handleBadRequests(SecurityConstraintException ex, WebRequest request) {
        log.error(ex.getLocalizedMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
    }

    @ExceptionHandler(WriteException.class)
    public ResponseEntity<Object> handleWriteException(WriteException ex, WebRequest request) {
        log.error(ex.getLocalizedMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleOthers(Exception ex, WebRequest request) {
        log.error(ex.getLocalizedMessage(), ex);
        return new ResponseEntity<>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler({Throwable.class})
    public ResponseEntity<Object> handleGenericException(Throwable ex) {
        log.error(ex.getLocalizedMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex);

    }
//
//    // BindException:
//    //  pl.: Rest interface-en custom handlerArgumentResolver es converter nelkuli resolvalaskor dobott Bean validation
//    // MethodArgumentTypeMismatchException:
//    // pl.: Rest interface-en torteno org.springframework.core.convert.converter.Converter#convert exception eseten
//    // MethodArgumentNotValidException:
//    // pl.: Rest interface-en torteno Bean Validation hiba eseten
//    @ExceptionHandler({BindException.class, MethodArgumentTypeMismatchException.class, MethodArgumentNotValidException.class})
//    public ResponseEntity<RestApiError> handleValidationException(BindException ex) {
//        log.error(ex.getLocalizedMessage(), ex);
//        RestApiError restApiError = new RestApiError(RestApiError.ERROR_CODE_INTERFACE_BEAN_VALIDATION_ERROR, Severity.critical.name(), errorDetailsResolver.getErrorDetails(ex));
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restApiError);
//    }
//
//    @ExceptionHandler(BusinessException.class)
//    public ResponseEntity<RestApiError> handleBusinessException(BusinessException ex) {
//        log.error(ex.getLocalizedMessage(), ex);
//        RestApiError restApiError = new RestApiError(restApiErrorCodeConverter.getRestApiErrorCode(ex.getErrorType()), ex.getSeverity().name(), errorDetailsResolver.getErrorDetails(ex));
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restApiError);
//    }
//
//    @ExceptionHandler(MissingEntityException.class)
//    public ResponseEntity<RestApiError> handleMissingEntityException(MissingEntityException ex) {
//        log.error(ex.getLocalizedMessage(), ex);
//        RestApiError restApiError = new RestApiError(restApiErrorCodeConverter.getRestApiErrorCode(ex.getErrorType()), ex.getSeverity().name(), errorDetailsResolver.getErrorDetails(ex));
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(restApiError);
//    }
//
//    @ExceptionHandler(EntityNotFoundException.class)
//    public ResponseEntity<RestApiError> handleEntityNotFoundException(EntityNotFoundException ex) {
//        log.error(ex.getLocalizedMessage(), ex);
//        RestApiError restApiError = new RestApiError(RestApiError.ERROR_CODE_MISSING_ENTITY_ERROR, Severity.critical.name(), errorDetailsResolver.getErrorDetails(ex));
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(restApiError);
//    }
//
//    @ExceptionHandler({MissingServletRequestParameterException.class})
//    public ResponseEntity<RestApiError> handleMissingServletRequestParameterException(
//            MissingServletRequestParameterException ex) {
//        log.error(ex.getLocalizedMessage(), ex);
//        RestApiError restApiError = new RestApiError(RestApiError.ERROR_CODE_INTERFACE_BEAN_VALIDATION_ERROR, Severity.critical.name(), errorDetailsResolver.getErrorDetails(ex));
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restApiError);
//    }
//
//    @ExceptionHandler({MissingRequestHeaderException.class})
//    public ResponseEntity<RestApiError> handleMissingRequestHeaderException(
//            MissingRequestHeaderException ex) {
//        log.error(ex.getLocalizedMessage(), ex);
//        RestApiError restApiError = new RestApiError(RestApiError.ERROR_CODE_INTERFACE_BEAN_VALIDATION_ERROR, Severity.critical.name(), errorDetailsResolver.getErrorDetails(ex));
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restApiError);
//    }
//
//    @ExceptionHandler({JsonProcessingException.class, JsonPatchException.class})
//    public ResponseEntity<RestApiError> handlePatchExceptions(Exception ex) {
//        log.error(ex.getLocalizedMessage(), ex);
//        RestApiError restApiError = new RestApiError(RestApiError.ERROR_CODE_INTERFACE_BEAN_VALIDATION_ERROR, Severity.critical.name(), errorDetailsResolver.getErrorDetails(ex));
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restApiError);
//    }
//
//    private boolean isCause(
//            Class<? extends Throwable> expected,
//            Throwable exc
//    ) {
//        return expected.isInstance(exc) || (
//                exc != null && isCause(expected, exc.getCause())
//        );
//    }
}

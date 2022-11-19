package io.c0dr.fileuploader.service.exception;

public interface IError {

    ErrorCode.ErrorType getErrorType();

    Severity getSeverity();
}

package com.yicj.reactor.component;

import com.yicj.reactor.model.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.OK)
    public ErrorCode handleCustomException(Exception e) {
        log.error(e.getMessage());
        return new ErrorCode("e","error" );
    }
}
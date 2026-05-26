package com.example.botanix.exception;

import com.example.botanix.model.ApiError;
import org.springframework.http.HttpStatus;

import java.util.List;

public class ApiException extends RuntimeException
{
    private final HttpStatus status;
    private final List<ApiError> errors;

    public ApiException(HttpStatus status, String message)
    {
        super(message);
        this.status = status;
        this.errors = List.of();
    }

    public HttpStatus getStatus()
    {
        return status;
    }

    public List<ApiError> getErrors()
    {
        return errors;
    }
}
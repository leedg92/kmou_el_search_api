package com.saltlux.searchstudio.api;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ApiResponse<Object> bindException(BindException e) {
        Map<String, String> validation = new HashMap<>();

        for (FieldError fieldError : e.getFieldErrors()) {
            String fieldName = convertToSnakeCase(fieldError.getField());
            validation.put(fieldName, fieldError.getDefaultMessage());
        }

        return ApiResponse.of(
            HttpStatus.BAD_REQUEST,
            "잘못된 요청입니다.",
            validation);
    }

    private String convertToSnakeCase(String input) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);
            if (Character.isUpperCase(currentChar) && i > 0) {
                result.append("_").append(Character.toLowerCase(currentChar));
            } else {
                result.append(Character.toLowerCase(currentChar));
            }
        }
        return result.toString();
    }

}

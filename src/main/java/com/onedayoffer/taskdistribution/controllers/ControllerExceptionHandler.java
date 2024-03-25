package com.onedayoffer.taskdistribution.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onedayoffer.taskdistribution.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;

@Slf4j
@ControllerAdvice(annotations= RestController.class)
@AllArgsConstructor
public class ControllerExceptionHandler {
    private final ObjectMapper objectMapper;

    @ExceptionHandler(ServiceException.class)
    public void onHandleException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() {
            {
                put("message", "The key or value of the parameter is missing or has an incorrect format, name.");
                put("details", e.getMessage());
            }
        }));
        log.error(e.getMessage());
    }
}

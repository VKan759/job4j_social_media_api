package ru.job4j.socialmedia.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    public void handleException(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException {
        Map<String, String> details = new HashMap<>();
        details.put("message", e.getMessage());
        details.put("type", String.valueOf(e.getClass()));
        details.put("timestamp", LocalDateTime.now().toString());
        details.put("path", request.getRequestURI());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(details));
        e.getLocalizedMessage();
    }
}

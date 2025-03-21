package com.airline.management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public Object handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
        if (isApiRequest(request)) {
            Map<String, String> error = new HashMap<>();
            error.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } else {
            ModelAndView mav = new ModelAndView("error");
            mav.addObject("status", HttpStatus.NOT_FOUND.value());
            mav.addObject("error", "Not Found");
            mav.addObject("message", ex.getMessage());
            return mav;
        }
    }

    @ExceptionHandler(ValidationException.class)
    public Object handleValidationException(ValidationException ex, HttpServletRequest request) {
        if (isApiRequest(request)) {
            Map<String, String> error = new HashMap<>();
            error.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } else {
            ModelAndView mav = new ModelAndView("error");
            mav.addObject("status", HttpStatus.BAD_REQUEST.value());
            mav.addObject("error", "Validation Error");
            mav.addObject("message", ex.getMessage());
            return mav;
        }
    }

    private boolean isApiRequest(HttpServletRequest request) {
        return request.getRequestURI().contains("/api/") ||
                "application/json".equals(request.getContentType()) ||
                request.getHeader("Accept") != null && request.getHeader("Accept").contains("application/json");
    }
}
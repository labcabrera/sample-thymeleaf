package org.labcabrera.sample.front.web;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, HttpServletRequest request, Model model) {
        int status = 500;
        String message = ex.getMessage();
        log.error("Unhandled exception for {} {} -> {}", request.getMethod(), request.getRequestURI(), message, ex);
        model.addAttribute("status", status);
        model.addAttribute("message", message != null ? message : "Unexpected error");
        model.addAttribute("path", request.getRequestURI());
        return "error/custom";
    }
}

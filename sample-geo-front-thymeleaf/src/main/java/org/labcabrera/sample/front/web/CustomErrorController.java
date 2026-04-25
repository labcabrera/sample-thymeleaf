package org.labcabrera.sample.front.web;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.webmvc.error.ErrorAttributes;
import org.springframework.boot.webmvc.error.ErrorController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@Slf4j
public class CustomErrorController implements ErrorController {

    private final ErrorAttributes errorAttributes;

    public CustomErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object message = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        String path = (String) request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);

        ServletWebRequest webRequest = new ServletWebRequest(request);
        Map<String, Object> attrs = this.errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.defaults());

        Integer statusCode = status != null ? Integer.valueOf(status.toString()) : (Integer) attrs.getOrDefault("status", 500);
        String msg = message != null ? message.toString()
            : (String) attrs.getOrDefault("error", (String) attrs.getOrDefault("message", "Unexpected error"));
        String requestUri = path != null ? path : (String) attrs.getOrDefault("path", request.getRequestURI());

        log.error("Error {} on {} -> {}", statusCode, requestUri, msg);
        model.addAttribute("status", statusCode);
        model.addAttribute("message", msg);
        model.addAttribute("path", requestUri);
        return "error/custom";
    }
}

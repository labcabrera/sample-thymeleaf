package org.labcabrera.sample.front.web;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@ControllerAdvice
public class GlobalModelAttributes {

    @ModelAttribute("userName")
    public String userName() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        if (!(ra instanceof ServletRequestAttributes)) {
            return null;
        }
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpSession session = sra.getRequest().getSession(false);
        if (session == null) {
            return null;
        }
        Object o = session.getAttribute("userName");
        return o != null ? o.toString() : null;
    }

    @ModelAttribute("email")
    public String email() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        if (!(ra instanceof ServletRequestAttributes)) {
            return null;
        }
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpSession session = sra.getRequest().getSession(false);
        if (session == null) {
            return null;
        }
        Object o = session.getAttribute("email");
        return o != null ? o.toString() : null;
    }

    @ModelAttribute("emailVerified")
    public Boolean emailVerified() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        if (!(ra instanceof ServletRequestAttributes)) {
            return null;
        }
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpSession session = sra.getRequest().getSession(false);
        if (session == null) {
            return null;
        }
        Object o = session.getAttribute("emailVerified");
        if (o == null)
            return null;
        if (o instanceof Boolean)
            return (Boolean) o;
        return Boolean.valueOf(o.toString());
    }

}

package org.labcabrera.sample.front.web;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class LogoutController {

    @GetMapping("/logout")
    public RedirectView logout(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
        return new RedirectView("/");
    }

}

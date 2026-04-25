package org.labcabrera.sample.front.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/user")
    public String user(Model model) {
        model.addAttribute("title", "User");
        return "user";
    }

}

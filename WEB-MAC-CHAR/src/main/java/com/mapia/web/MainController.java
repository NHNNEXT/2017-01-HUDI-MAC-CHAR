package com.mapia.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mapia.domain.User;
import com.mapia.utils.HttpSessionUtils;

/**
 * Created by Jbee on 2017. 3. 16..
 */
@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping("")
    public String mainPage() {
        return "redirect:/login";
    }

    @GetMapping("login")
    public String loginPage(HttpSession session) {
        User sessionedUser = (User) session.getAttribute(HttpSessionUtils.USER_SESSION_KEY);
        if (sessionedUser == null) {
            return "login_page.html";
        }

        return "redirect:/lobby";
    }

    @GetMapping("signup")
    public String signupPage() {
        return "signup_page.html";
    }
}

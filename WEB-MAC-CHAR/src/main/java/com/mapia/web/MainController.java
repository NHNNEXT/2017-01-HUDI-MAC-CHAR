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
    	User sessionedUser = (User)session.getAttribute(HttpSessionUtils.USER_SESSION_KEY);
		if (sessionedUser == null) {
			return "login_page.html";
		}
    	
		return "redirect:/rooms";
	}

    @GetMapping("signup")
	public String signupPage() {
		return "signup_page.html";
	}
    
    @GetMapping("logout")	// 일단 여기에 놓지만 위치를 다시 생각해 봐야 될 듯.
    public String logout(HttpSession session) {
    	session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
    	return "redirect:/";
    }
}

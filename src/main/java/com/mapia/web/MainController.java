package com.mapia.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Jbee on 2017. 3. 16..
 */
@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping("")
    public String mainPage() {
        return "index.html";
    }
}

package com.taselectfc.controllers;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    private static Logger log = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping("/")
    public String homePage(HttpSession session) {
        log.debug("Loading home page for session [{}]", session.getId());

        return "TASelectFC";
    }
}

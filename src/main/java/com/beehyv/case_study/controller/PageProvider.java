package com.beehyv.case_study.controller;


import com.beehyv.case_study.services.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageProvider {

    @Autowired
    UserManager userManager;

    @GetMapping("/")
    String getIndexPage() {
        return "index.html";
    }

    @GetMapping("/home")
    String getHomePage() {
        return "mainPage.html";
    }

    @GetMapping("/login")
    String getFailedPage() {
        return "loginPage.html";
    }

    @GetMapping("/signUp")
    String getSignUp() {
        return "signUpPage.html";
    }

}

package com.controller;

import com.domain.User;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Locale;

@Controller
public class MainController {

    private static final String VIEW_INDEX = "pages/index";
    private static final String VIEW_403 = "pages/403";
    private static final String VIEW_ADMIN = "pages/admin";
    private static final String VIEW_LOGIN = "pages/authen/login";
    private static final String VIEW_SIGNUP = "pages/authen/signup";
    private static final String MODEL_ATTRIBUTE_USER = "user";
    private static final String MESSAGE_SUCCESS_VARIABLE = "success";
    private static final String MESSAGE_SIGNUP_VARIABLE = "user.signup.success";
    private static final String REDIRECT_HOME = "redirect:/";
    private static final String REDIRECT_LOGIN = "redirect:/login";


    private final UserService userService;
    private final MessageSource messageSource;

    @Autowired
    public MainController(UserService userService, MessageSource messageSource) {
        this.userService = userService;
        this.messageSource = messageSource;
    }

    @GetMapping("/")
    public String index() {
        return VIEW_INDEX;
    }

    @GetMapping("/admin")
    public String admin() {
        return VIEW_ADMIN;
    }

    @GetMapping("/403")
    public String accessDenied() {
        return VIEW_403;
    }

    @GetMapping("/login")
    public String getLogin() {
        return VIEW_LOGIN;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return REDIRECT_HOME;
    }

    @GetMapping("/signup")
    public String getSignup(Model model) {
        model.addAttribute(MODEL_ATTRIBUTE_USER, new User());
        return VIEW_SIGNUP;
    }

    @PostMapping("/signup/save")
    public String saveUser(@Valid User user, BindingResult result, RedirectAttributes redirect, Locale locale) {
        if (result.hasErrors()) {
            return VIEW_SIGNUP;
        }
        userService.save(user);
        redirect.addFlashAttribute(MESSAGE_SUCCESS_VARIABLE, messageSource.getMessage(MESSAGE_SIGNUP_VARIABLE, null, locale));
        return REDIRECT_LOGIN;
    }
}
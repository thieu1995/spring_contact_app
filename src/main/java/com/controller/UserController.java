package com.controller;

import com.domain.User;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Locale;

@Controller
@RequestMapping("user")
public class UserController {

    private static final String VIEW_USER_PROFILE = "pages/user/userProfile";
    private static final String VIEW_USER_EDIT_FORM = "pages/user/userEditForm";
    private static final String REDIRECT_USER_PROFILE = "redirect:/user/profile";
    private static final String MODEL_ATTRIBUTE_USER = "user";
    private static final String MESSAGE_SUCCESS_VARIABLE = "success";
    private static final String MESSAGE_SAVE_VARIABLE = "user.save.success";


    private final UserService userService;
    private final MessageSource messageSource;

    @Autowired
    public UserController(UserService userService, MessageSource messageSource) {
        this.userService = userService;
        this.messageSource = messageSource;
    }

    @RequestMapping(value="/profile")
    public String viewProfile(Model model) {
        model.addAttribute(MODEL_ATTRIBUTE_USER, userService.findOneByEmail(getCurrentUser()));
        return VIEW_USER_PROFILE;
    }

    @RequestMapping(value="/edit")
    public String showUserEditForm(Model model) {
        model.addAttribute(MODEL_ATTRIBUTE_USER, userService.findOneByEmail(getCurrentUser()));
        return VIEW_USER_EDIT_FORM;
    }

    @PostMapping(value="/edit/save")
    public String processEditUser(@Valid User user, BindingResult result, RedirectAttributes redirect, Locale locale) {
        if (result.hasErrors()) {
            return VIEW_USER_EDIT_FORM;
        }
        userService.save(user);
        redirect.addFlashAttribute(MESSAGE_SUCCESS_VARIABLE, messageSource.getMessage(MESSAGE_SAVE_VARIABLE, null, locale));
        return REDIRECT_USER_PROFILE;
    }


    /*
    *   Get current user
    * */
    private String getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        return userDetails.getUsername();
    }
}

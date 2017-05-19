package com.controller;

import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

@Controller
@RequestMapping("admin/user")
public class AdminController {

    private static final String VIEW_USER_LIST = "pages/user/userList";
    private static final String REDIRECT_USER_LIST = "redirect:/admin/user/all";
    private static final String MODEL_ATTRIBUTE_USERS = "users";
    private static final String MESSAGE_SUCCESS_VARIABLE = "success";
    private static final String MESSAGE_DELETE_VARIABLE = "user.delete.success";


    private final UserService userService;
    private final MessageSource messageSource;

    @Autowired
    public AdminController(UserService userService, MessageSource messageSource) {
        this.userService = userService;
        this.messageSource = messageSource;
    }

    @RequestMapping(value="/all")
    public String viewAllUser(Model model) {
        model.addAttribute(MODEL_ATTRIBUTE_USERS, userService.findAll());
        return VIEW_USER_LIST;
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable int id, RedirectAttributes redirect, Locale locale) {
        userService.deleteById(id);
        redirect.addFlashAttribute(MESSAGE_SUCCESS_VARIABLE, messageSource.getMessage(MESSAGE_DELETE_VARIABLE, null, locale));
        return REDIRECT_USER_LIST;
    }

    @GetMapping("/search")
    public String search(@RequestParam("name") String name, Model model) {
        if (name.equals("")) {
            return REDIRECT_USER_LIST;
        }
        model.addAttribute(MODEL_ATTRIBUTE_USERS, userService.searchByFullname(name));
        return VIEW_USER_LIST;
    }
}

package com.controller;

import com.domain.Contact;
import com.domain.User;
import com.service.ContactService;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /*
    *   Handle admin user controller
    * */
    @RequestMapping(value="/admin/user/all")
    public String viewAllUser(Model model) {
        model.addAttribute("users", userService.findAll());
        return "userList";
    }

    @GetMapping("/admin/user/{id}/delete")
    public String delete(@PathVariable int id, RedirectAttributes redirect) {
        userService.deleteById(id);
        redirect.addFlashAttribute("success", "Deleted user successfully!");
        return "redirect:/admin/user/all";
    }

    @GetMapping("/admin/user/search")
    public String search(@RequestParam("name") String name, Model model) {
        if (name.equals("")) {
            return "redirect:/admin/user/all";
        }
        model.addAttribute("users", userService.searchByFullname(name));
        return "userList";
    }



    /*
    * Handle user controller
    * */
    @RequestMapping(value="/user/profile")
    public String viewProfile(Model model) {
        model.addAttribute("user", userService.findOneByEmail(getCurrentUser()));
        return "userProfile";
    }

    @RequestMapping(value="/user/edit")
    public String showUserEditForm(Model model) {
        model.addAttribute("user", userService.findOneByEmail(getCurrentUser()));
        return "userEditForm";
    }

    @PostMapping(value="/user/edit/save")
    public String processEditUser(@Valid User user, BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return "userEditForm";
        }
        userService.save(user);
        redirect.addFlashAttribute("success", "Updated user successfully!");
        return "redirect:/user/profile";
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

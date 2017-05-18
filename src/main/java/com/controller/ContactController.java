package com.controller;

import com.domain.Contact;
import com.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class ContactController {

    @Autowired
    private ContactService contactService;

    @RequestMapping(value="/contact/all")
    public String viewAllContact(Model model) {
        model.addAttribute("contacts", contactService.findAll());
        return "contactList";
    }

    @RequestMapping(value="/contact/create")
    public String displayFormCreateNewContact(Model model) {
        model.addAttribute("contact", new Contact());
        return "contactForm";
    }

    @PostMapping("/contact/save")
    public String save(@Valid Contact contact, BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return "contactForm";
        }
        contactService.save(contact);
        redirect.addFlashAttribute("success", "Saved contact successfully!");
        return "redirect:/contact/all";
    }

    @GetMapping("/contact/{id}/edit")
    public String edit(@PathVariable int id, Model model) {
        model.addAttribute("contact", contactService.findOne(id));
        return "contactForm";
    }

    @GetMapping("/contact/{id}/delete")
    public String delete(@PathVariable int id, RedirectAttributes redirect) {
        contactService.delete(id);
        redirect.addFlashAttribute("success", "Deleted contact successfully!");
        return "redirect:/contact/all";
    }

    @GetMapping("/contact/search")
    public String search(@RequestParam("q") String q, Model model) {
        if (q.equals("")) {
            return "redirect:/contact/all";
        }
        model.addAttribute("contacts", contactService.search(q));
        return "contactList";
    }
}

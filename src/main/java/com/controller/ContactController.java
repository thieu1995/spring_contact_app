package com.controller;

import com.domain.Contact;
import com.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Locale;

@Controller
@RequestMapping("contact")
public class ContactController {

    private static final String VIEW_CONTACT_LIST = "pages/contact/contactList";
    private static final String VIEW_CONTACT_FORM = "pages/contact/contactForm";
    private static final String REDIRECT_CONTACT_LIST = "redirect:/contact/all";

    private static final String MODEL_ATTRIBUTE_CONTACTS = "contacts";
    private static final String MODEL_ATTRIBUTE_CONTACT = "contact";

    private static final String MESSAGE_SUCCESS_VARIABLE = "success";
    private static final String MESSAGE_SAVE_VARIABLE = "contact.save.success";
    private static final String MESSAGE_DELETE_VARIABLE = "contact.delete.success";


    private final ContactService contactService;
    private final MessageSource messageSource;

    @Autowired
    public ContactController(ContactService contactService, MessageSource messageSource) {
        this.contactService = contactService;
        this.messageSource = messageSource;
    }

    @RequestMapping(value="/all")
    public String viewAllContact(Model model) {
        model.addAttribute(MODEL_ATTRIBUTE_CONTACTS, contactService.findAll());
        return VIEW_CONTACT_LIST;
    }

    @RequestMapping(value="/create")
    public String displayFormCreateNewContact(Model model) {
        model.addAttribute(MODEL_ATTRIBUTE_CONTACT, new Contact());
        return VIEW_CONTACT_FORM;
    }

    @PostMapping("/save")
    public String save(@Valid Contact contact, BindingResult result, RedirectAttributes redirect, Locale locale) {
        if (result.hasErrors()) {
            return VIEW_CONTACT_FORM;
        }
        contactService.save(contact);
        redirect.addFlashAttribute(MESSAGE_SUCCESS_VARIABLE, messageSource.getMessage(MESSAGE_SAVE_VARIABLE, null, locale));
        return REDIRECT_CONTACT_LIST;
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable int id, Model model) {
        model.addAttribute(MODEL_ATTRIBUTE_CONTACT, contactService.findOne(id));
        return VIEW_CONTACT_FORM;
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable int id, RedirectAttributes redirect, Locale locale) {
        contactService.delete(id);
        redirect.addFlashAttribute(MESSAGE_SUCCESS_VARIABLE, messageSource.getMessage(MESSAGE_DELETE_VARIABLE, null, locale));
        return REDIRECT_CONTACT_LIST;
    }

    @GetMapping("/search")
    public String search(@RequestParam("q") String q, Model model) {
        if (q.equals("")) {
            return REDIRECT_CONTACT_LIST;
        }
        model.addAttribute(MODEL_ATTRIBUTE_CONTACTS, contactService.search(q));
        return VIEW_CONTACT_LIST;
    }
}

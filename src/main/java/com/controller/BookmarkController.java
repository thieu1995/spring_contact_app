package com.controller;

import com.domain.Bookmark;
import com.repository.BookmarkRepository;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Locale;

@Controller
public class BookmarkController {

    private final BookmarkRepository bookmarkRepository;
    private final UserService userService;
    private final MessageSource messageSource;

    @Autowired
    public BookmarkController(BookmarkRepository bookmarkRepository, UserService userService, MessageSource messageSource) {
        this.bookmarkRepository = bookmarkRepository;
        this.userService = userService;
        this.messageSource = messageSource;
    }

    /*
    *   Get current user
    * */
    private String getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return auth.getName();
        }
        return null;
    }

    private int getCurrentUserId() {
        return userService.findOneByEmail(getCurrentUser()).getId();
    }


    @RequestMapping(value="/bookmark/all")
    public String viewListBookmarks(Model model) {
        model.addAttribute("bookmarks", bookmarkRepository.findBookmarkByUserId(getCurrentUserId()));
        return "bookmarkList";
    }

    @RequestMapping(value="/bookmark/create")
    public String displayFormCreateNewBookmark(Model model) {
        Bookmark b = new Bookmark();
        b.setUser(userService.findOneByEmail(getCurrentUser()));
        System.out.println(userService.findOneByEmail(getCurrentUser()));
        System.out.println(b.getUser().getId());
        model.addAttribute("bookmark", b);
        return "bookmarkForm";
    }

    @PostMapping("/bookmark/save")
    public String save(@Valid Bookmark bookmark, BindingResult result, RedirectAttributes redirect, Locale locale) {
        if (result.hasErrors()) {
            return "bookmarkForm";
        }
        bookmarkRepository.save(bookmark);
        redirect.addFlashAttribute("success", messageSource.getMessage("bookmark.save.success", null, locale));
        return "redirect:/bookmark/all";
    }

    @GetMapping("/bookmark/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("bookmark", bookmarkRepository.findOne(id));
        return "bookmarkForm";
    }

    @GetMapping("/bookmark/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirect, Locale locale) {
        bookmarkRepository.delete(id);
        redirect.addFlashAttribute("success", messageSource.getMessage("bookmark.delete.success", null, locale));
        return "redirect:/bookmark/all";
    }

    @GetMapping("/bookmark/search")
    public String search(@RequestParam("uri") String uri, Model model) {
        if (uri.equals("")) {
            return "redirect:/bookmark/all";
        }
        model.addAttribute("bookmarks", bookmarkRepository.findAllByUriContaining(uri));
        return "bookmarkList";
    }
}

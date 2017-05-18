package com.controller;

import com.domain.Bookmark;
import com.repository.BookmarkRepository;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class BookmarkController {

    @Autowired
    private BookmarkRepository bookmarkRepository;
    @Autowired
    private UserService userService;

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
    public String save(@Valid Bookmark bookmark, BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return "bookmarkForm";
        }
        bookmarkRepository.save(bookmark);
        redirect.addFlashAttribute("success", "Create new bookmark successfully!");
        return "redirect:/bookmark/all";
    }

    @GetMapping("/bookmark/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("bookmark", bookmarkRepository.findOne(id));
        return "bookmarkForm";
    }

    @GetMapping("/bookmark/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirect) {
        bookmarkRepository.delete(id);
        redirect.addFlashAttribute("success", "Deleted bookmark successfully!");
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

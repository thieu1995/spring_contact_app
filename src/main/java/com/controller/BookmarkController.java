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
@RequestMapping("bookmark")
public class BookmarkController {

    private static final String VIEW_BOOKMARK_LIST = "pages/bookmark/bookmarkList";
    private static final String VIEW_BOOKMARK_FORM = "pages/bookmark/bookmarkForm";
    private static final String REDIRECT_BOOKMARK_LIST = "redirect:/bookmark/all";

    private static final String MODEL_ATTRIBUTE_BOOKMARKS = "bookmarks";
    private static final String MODEL_ATTRIBUTE_BOOKMARK = "bookmark";

    private static final String MESSAGE_SUCCESS_VARIABLE = "success";
    private static final String MESSAGE_SAVE_VARIABLE = "bookmark.save.success";
    private static final String MESSAGE_DELETE_VARIABLE = "bookmark.delete.success";


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


    @RequestMapping(value="/all")
    public String viewListBookmarks(Model model) {
        model.addAttribute(MODEL_ATTRIBUTE_BOOKMARKS, bookmarkRepository.findBookmarkByUserId(getCurrentUserId()));
        return VIEW_BOOKMARK_LIST;
    }

    @RequestMapping(value="/create")
    public String displayFormCreateNewBookmark(Model model) {
        Bookmark b = new Bookmark();
        b.setUser(userService.findOneByEmail(getCurrentUser()));
        model.addAttribute(MODEL_ATTRIBUTE_BOOKMARK, b);
        return VIEW_BOOKMARK_FORM;
    }

    @PostMapping("/save")
    public String save(@Valid Bookmark bookmark, BindingResult result, RedirectAttributes redirect, Locale locale) {
        if (result.hasErrors()) {
            return VIEW_BOOKMARK_FORM;
        }
        bookmarkRepository.save(bookmark);
        redirect.addFlashAttribute(MESSAGE_SUCCESS_VARIABLE, messageSource.getMessage(MESSAGE_SAVE_VARIABLE, null, locale));
        return REDIRECT_BOOKMARK_LIST;
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute(MODEL_ATTRIBUTE_BOOKMARK, bookmarkRepository.findOne(id));
        return VIEW_BOOKMARK_FORM;
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirect, Locale locale) {
        bookmarkRepository.delete(id);
        redirect.addFlashAttribute(MESSAGE_SUCCESS_VARIABLE, messageSource.getMessage(MESSAGE_DELETE_VARIABLE, null, locale));
        return REDIRECT_BOOKMARK_LIST;
    }

    @GetMapping("/search")
    public String search(@RequestParam("uri") String uri, Model model) {
        if (uri.equals("")) {
            return REDIRECT_BOOKMARK_LIST;
        }
        model.addAttribute(MODEL_ATTRIBUTE_BOOKMARKS, bookmarkRepository.findAllByUriContaining(uri));
        return VIEW_BOOKMARK_LIST;
    }
}

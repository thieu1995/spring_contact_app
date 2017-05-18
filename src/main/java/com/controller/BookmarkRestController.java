package com.controller;

import com.domain.Bookmark;
import com.repository.BookmarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contactapp/api/bookmarks")
class BookmarkRestController {

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<Bookmark> getAllBookmarksByEmail() {
        return this.bookmarkRepository.findBookmarkByUserEmail(getCurrentUser());
    }

    @RequestMapping(method = RequestMethod.POST)
    public List<Bookmark> add(@RequestBody Bookmark input) {
        this.bookmarkRepository.save(new Bookmark(input.getUri(), input.getDescription(), input.getUser()));
        return this.bookmarkRepository.findBookmarkByUserEmail(getCurrentUser());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{bookmarkId}")
    public Bookmark getBookmarkById(@PathVariable Long bookmarkId) {
        return this.bookmarkRepository.findOne(bookmarkId);
    }


    /*
    *   Get current user
    * */
    private String getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
}

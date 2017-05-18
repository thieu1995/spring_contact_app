/* https://spring.io/guides/tutorials/bookmarks/  */

package com.repository;

import com.domain.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    List<Bookmark> findBookmarkByUserId(int id);
    List<Bookmark> findBookmarkByUserEmail(String email);

    List<Bookmark> findAllByUriContaining(String uri);
}
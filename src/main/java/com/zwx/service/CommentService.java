package com.zwx.service;


import com.zwx.pojo.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {

    List<Comment> listCommentByBlogId(Long id);

    Comment saveComment(Comment comment);

    Page<Comment> listComment(Pageable pageable);

    void delteComment(Long id);

    Long countComment();
}

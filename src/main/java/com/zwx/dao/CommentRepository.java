package com.zwx.dao;

import com.zwx.pojo.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findByBlogIdAndParentCommentNull(Long blogId,Sort sort);

    @Transactional
    @Modifying
    @Query("update Comment c set c.parentComment=null where c.id=?1")
    int updateComment(Long id);
}

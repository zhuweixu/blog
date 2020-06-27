package com.zwx.dao;

import com.zwx.pojo.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LikeRepository extends JpaRepository<Like,Long> {

    Like findByBlogIdAndUserId(Long blogId,Long userId);

    void deleteByBlogIdAndUserId(Long blogId,Long userId);

}

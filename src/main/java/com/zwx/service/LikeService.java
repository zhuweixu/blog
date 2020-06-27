package com.zwx.service;

import com.zwx.pojo.Like;

public interface LikeService {

    Like saveLike(Like like);

    Like findByBlogIdAndUserId(Long blogId,Long UserId);

    void deleteByBlogIdAndUserId(Long blogId,Long userId);


    Long countLike();

}

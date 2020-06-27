package com.zwx.service;

import com.zwx.dao.LikeRepository;
import com.zwx.pojo.Like;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Override
    public Long countLike() {
        return likeRepository.count();
    }

    @Transactional
    @Override
    public Like saveLike(Like like) {
        return likeRepository.save(like);
    }

    @Override
    public Like findByBlogIdAndUserId(Long blogId,Long userId) {
        return likeRepository.findByBlogIdAndUserId(blogId,userId);
    }

    @Transactional
    @Override
    public void deleteByBlogIdAndUserId(Long blogId, Long userId) {
        likeRepository.deleteByBlogIdAndUserId(blogId,userId);
    }
}

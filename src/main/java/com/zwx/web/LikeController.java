package com.zwx.web;

import com.zwx.pojo.Like;
import com.zwx.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping("/likes")
    public String post(Like like){
        Long blogId=like.getBlog().getId();
        Long userId=like.getUser().getId();
        Like like1=likeService.findByBlogIdAndUserId(blogId,userId);
        if(like1==null){
            likeService.saveLike(like);
            return "blog :: like";
        }else{
            likeService.deleteByBlogIdAndUserId(blogId,userId);
            return "blog :: like";
        }

    }

}

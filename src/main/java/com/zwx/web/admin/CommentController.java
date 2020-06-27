package com.zwx.web.admin;


import com.zwx.pojo.Comment;
import com.zwx.service.BlogService;
import com.zwx.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private BlogService blogService;

    @GetMapping("/comments/{blogId}")
    public String comment(@PathVariable Long blogId, Model model){
        model.addAttribute("comments",commentService.listCommentByBlogId(blogId));
        return "blog :: commentList";
    }

    @PostMapping("/comments")
    public String post(Comment comment){
        Long blogid=comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogid));
        commentService.saveComment(comment);
        return "redirect:/comments/"+ blogid;
    }

    @PostMapping("/admin/comments/reply")
    public String reply(Comment comment,@PageableDefault(sort = {"createTime"},direction = Sort.Direction.DESC)
            Pageable pageable, Model model){
        commentService.saveComment(comment);
        model.addAttribute("page",commentService.listComment(pageable));
        return "/admin/html/comments :: commentList";
    }

    @GetMapping("/admin/comments")
    public String comments(@PageableDefault(sort = {"createTime"},direction = Sort.Direction.DESC)
                                       Pageable pageable, Model model){
        model.addAttribute("page",commentService.listComment(pageable));
        return "admin/html/comments";
    }

    @PostMapping("/admin/comments/delete")
    public String delete(Long id,@PageableDefault(sort = {"createTime"},direction = Sort.Direction.DESC)
            Pageable pageable, Model model){
        commentService.delteComment(id);
        model.addAttribute("page",commentService.listComment(pageable));
        return "/admin/html/comments :: commentList";
    }
}


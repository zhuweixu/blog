package com.zwx.service;

import com.zwx.dao.AdminRepository;
import com.zwx.dao.CommentRepository;
import com.zwx.dao.UserRepository;
import com.zwx.pojo.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        Sort sort = new Sort("createTime");
        List<Comment> comments = commentRepository.findByBlogIdAndParentCommentNull(blogId,sort);
        return eachComment(comments);
    }

    @Transactional
    @Override
    public void delteComment(Long id) {
        Comment comment=commentRepository.findOne(id);
        List<Comment> comments=comment.getReplyComments();
        for(Comment comment1:comments){
            Long comment1Id=comment1.getId();
            commentRepository.updateComment(comment1Id);
        }
        commentRepository.delete(id);
    }

    @Override
    public Long countComment() {
        return commentRepository.count();
    }

    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        Long parentUserId = comment.getParentUser().getId();
        Long parentAdminId = comment.getParentAdmin().getId();
        if (parentCommentId != -1) {
            comment.setParentComment(commentRepository.findOne(parentCommentId));
        } else {
            comment.setParentComment(null);
        }
        if (parentUserId == -1) {
            comment.setParentUser(null);
        }
        if (parentAdminId == -1) {
            comment.setParentAdmin(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }

    @Transactional
    @Override
    public Page<Comment> listComment(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }


    /**
     * 循环每个顶级的评论节点
     * @param comments
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);
            commentsView.add(c);
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }

    /**
     *
     * @param comments root根节点，blog不为空的对象集合
     * @return
     */
    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();
    private void combineChildren(List<Comment> comments) {

        for (Comment comment : comments) {
            List<Comment> replys1 = comment.getReplyComments();
            for(Comment reply1 : replys1) {
                //循环迭代，找出子代，存放在tempReplys中
                tempReplys.add(reply1);//顶节点添加到临时存放集合
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }
}

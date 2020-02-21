package com.swb.springcloud.comment_vote.service;

import com.swb.springcloud.comment_vote.pojo.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {

    Comment saveComment(Comment comment);

    Page<Comment> getCommentByFlowerId(Long id, Pageable pageable);

    Comment getCommentById(Long id);
}

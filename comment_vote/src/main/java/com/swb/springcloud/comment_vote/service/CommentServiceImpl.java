package com.swb.springcloud.comment_vote.service;

import com.swb.springcloud.comment_vote.feign.Client.ESFeignClient;
import com.swb.springcloud.comment_vote.pojo.Comment;
import com.swb.springcloud.comment_vote.repository.CommentRepository;
import com.swb.springcloud.comment_vote.thread.ESTongXi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ESFeignClient esFeignClient;

    @Override
    @Transactional
    public Comment saveComment(Comment comment) {
        commentRepository.addFlowerCommentSize(comment.getFlowerId());
        ESTongXi esTongXi=new ESTongXi(esFeignClient,comment.getFlowerId(),"add","comment");
        Thread thread=new Thread(esTongXi);
        thread.start();
        return commentRepository.save(comment);
    }

    @Override
    public Page<Comment> getCommentByFlowerId(Long id, Pageable pageable) {
        return commentRepository.findCommentsByFlowerIdOrderByCreateTimeAsc(id,pageable);
    }

    @Override
    public Comment getCommentById(Long id) {
        return commentRepository.findById(id).orElse(null);
    }
}

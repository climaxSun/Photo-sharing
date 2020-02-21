package com.swb.springcloud.comment_vote.repository;

import com.swb.springcloud.comment_vote.pojo.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    @Modifying
    @Query(value = "update flower set comment_size=comment_size+1 where id=?1",nativeQuery = true)
    int addFlowerCommentSize(Long flowerId);

    @Modifying
    @Query(value = "update flower set comment_size=comment_size-1 where id=?1",nativeQuery = true)
    int subFlowerCommentSize(Long flowerId);

    Page<Comment> findCommentsByFlowerIdOrderByCreateTimeAsc(Long flowerId,Pageable pageable);
}

package com.swb.springcloud.comment_vote.repository;

import com.swb.springcloud.comment_vote.pojo.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VoteRepository extends JpaRepository<Vote,Long> {

    int deleteVoteByFlowerIdAndUserId(Long flowerId,Long userId);

    Vote findVotesByFlowerIdAndUserId(Long flowerId,Long userId);

    @Modifying
    @Query(value = "update flower set vote_size=vote_size+1 where id=?1",nativeQuery = true)
    int addVoteSize(Long id);

    @Modifying
    @Query(value = "update flower set vote_size=vote_size-1 where id=?1",nativeQuery = true)
    int subVoteSize(Long id);

    Vote findByFlowerIdAndUserId(Long flowerId,Long userId);
}

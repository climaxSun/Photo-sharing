package com.swb.springcloud.comment_vote.service;

import com.swb.springcloud.comment_vote.pojo.Vote;

public interface VoteService {

    Vote setVote(Long flowerId, Long userId);

    int deleteVote(Long flowerId,Long userId);

    Vote getVote(Long flowerId,Long userId);
}

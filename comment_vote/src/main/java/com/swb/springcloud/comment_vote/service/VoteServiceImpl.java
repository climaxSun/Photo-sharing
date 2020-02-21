package com.swb.springcloud.comment_vote.service;

import com.swb.springcloud.comment_vote.feign.Client.ESFeignClient;
import com.swb.springcloud.comment_vote.pojo.Vote;
import com.swb.springcloud.comment_vote.repository.VoteRepository;
import com.swb.springcloud.comment_vote.thread.ESTongXi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private ESFeignClient esFeignClient;

    @Override
    @Transactional
    public Vote setVote(Long flowerId, Long userId) {
        Vote vote=voteRepository.findVotesByFlowerIdAndUserId(flowerId,userId);
        Vote returnVote;
        if(vote==null){
            vote=new Vote(flowerId,userId);
            returnVote=voteRepository.save(vote);
            voteRepository.addVoteSize(flowerId);
            ESTongXi esTongXi=new ESTongXi(esFeignClient,flowerId,"add","vote");
            Thread thread=new Thread(esTongXi);
            thread.start();
        }
        else {
            returnVote=vote;
        }
        return returnVote;
    }

    @Override
    @Transactional
    public int deleteVote(Long flowerId, Long userId) {
        int i=voteRepository.deleteVoteByFlowerIdAndUserId(flowerId,userId);
        if(i==0) return 0;
        voteRepository.subVoteSize(flowerId);
        ESTongXi esTongXi=new ESTongXi(esFeignClient,flowerId,"sub","vote");
        Thread thread=new Thread(esTongXi);
        thread.start();
        return 1;
    }

    @Override
    public Vote getVote(Long flowerId, Long userId) {
        return voteRepository.findByFlowerIdAndUserId(flowerId, userId);
    }
}

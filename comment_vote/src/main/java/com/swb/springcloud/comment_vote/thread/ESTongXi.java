package com.swb.springcloud.comment_vote.thread;

import com.swb.springcloud.comment_vote.feign.Client.ESFeignClient;
import com.swb.springcloud.comment_vote.repository.ReportRepository;
import org.springframework.transaction.annotation.Transactional;

public class ESTongXi implements Runnable {

    private ESFeignClient esFeignClient;

    private Long id;

    private String type;

    private String who;

    public ESTongXi(ESFeignClient esFeignClient, Long id,String type,String who) {
        this.esFeignClient = esFeignClient;
        this.id=id;
        this.type=type;
        this.who=who;
    }

    @Override
    public void run() {
        if(who.equals("comment")){
            esFeignClient.editFlowerCommentSize(type,id);
        }
        else{
            esFeignClient.editFlowerVoteSize(type,id);
        }

    }

}

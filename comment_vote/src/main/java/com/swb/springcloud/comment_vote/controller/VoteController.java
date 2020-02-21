package com.swb.springcloud.comment_vote.controller;

import com.swb.springcloud.comment_vote.comment.RestResponse;
import com.swb.springcloud.comment_vote.pojo.UserReturn;
import com.swb.springcloud.comment_vote.pojo.Vote;
import com.swb.springcloud.comment_vote.service.UserServiceImpl;
import com.swb.springcloud.comment_vote.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vote")
public class VoteController {

    @Autowired
    private VoteService voteService;

    @Autowired
    private UserServiceImpl userService;

    @GetMapping()
    public RestResponse<Vote> setVote(@CookieValue(value = "token",required = false) String token , @RequestParam Long flowerId){
        UserReturn userReturn=userService.getUserByToen(token);
        voteService.setVote(flowerId,userReturn.getId());
        return RestResponse.success();
    }

    @DeleteMapping()
    public RestResponse<Vote> deleteVote(@CookieValue(value = "token",required = false) String token , @RequestParam Long flowerId){
        UserReturn userReturn=userService.getUserByToen(token);
        voteService.deleteVote(flowerId,userReturn.getId());
        return RestResponse.success();
    }

    @GetMapping("/isVote")
    public RestResponse<Boolean> getVote(@RequestParam Long flowerId, @RequestParam Long userId){
        Vote vote=voteService.getVote(flowerId,userId);
        if(vote==null){
            return RestResponse.success(false);
        }
        return RestResponse.success(true);
    }
}

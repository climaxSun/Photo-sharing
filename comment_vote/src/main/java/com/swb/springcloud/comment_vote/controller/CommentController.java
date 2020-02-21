package com.swb.springcloud.comment_vote.controller;

import com.swb.springcloud.comment_vote.Utils.ToolKit;
import com.swb.springcloud.comment_vote.comment.RestResponse;
import com.swb.springcloud.comment_vote.exception.CommentException;
import com.swb.springcloud.comment_vote.pojo.Comment;
import com.swb.springcloud.comment_vote.pojo.UserReturn;
import com.swb.springcloud.comment_vote.service.CommentService;
import com.swb.springcloud.comment_vote.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserServiceImpl userService;

    @PostMapping
    public RestResponse<Comment> Comment(@CookieValue(value = "token",required = false)String token,
                           @RequestParam("flowerId") Long flowerId,@RequestParam("content") String content){
        UserReturn userReturn=userService.getUserByToen(token);
        Comment comment=new Comment(userReturn.getId(), ToolKit.toHtml(content),flowerId);
        comment= commentService.saveComment(comment);
        return RestResponse.success(comment);
    }

    @GetMapping
    public RestResponse<Map<String,Object>> GetComments(@RequestParam("flowerId") Long flowerId, @RequestParam("pageIndex")int pageIndex){
        Pageable pageable= PageRequest.of(pageIndex,5);
        Page<Comment> commentPage=commentService.getCommentByFlowerId(flowerId,pageable);
        List<Comment> commentList=commentPage.getContent();
        List<Long> userIdList=new ArrayList<Long>();
        for(int i=0;i<commentList.size();i++){
            Comment comment=commentList.get(i);
            Long id=comment.getUserId();
            if(userIdList.contains(id)) {
                continue;
            }
            userIdList.add(id);
        }
        List<UserReturn> userReturnList= userService.getUsersByUserIds(userIdList);
        int commentSize=commentList.size();
        for(int i=0;i<commentSize;i++){
            Comment comment=commentList.get(i);
            for(UserReturn userReturn: userReturnList){
                if(comment.getUserId().equals(userReturn.getId())){
                    comment.setUsername(userReturn.getName());
                    comment.setUserAvatar(userReturn.getAvatar());
                    break;
                }
            }
        }
        Map<String,Object> map=new HashMap<>();
        map.put("comments",commentList);
        map.put("totalElements",commentPage.getTotalElements());
        map.put("totalPages",commentPage.getTotalPages());
        map.put("number",commentPage.getNumber());
        map.put("isFirst",commentPage.isFirst());
        map.put("isLast",commentPage.isLast());
        return RestResponse.success(map);
    }

    @GetMapping("/{id}")
    public RestResponse<Comment> getCommentById(@PathVariable(value = "id")Long id){
        Comment comment=commentService.getCommentById(id);
        if(comment==null)
            throw new CommentException(CommentException.Type.Comment_NOT_FOUND,"评论不存在");
        return  RestResponse.success(comment);
    }
}

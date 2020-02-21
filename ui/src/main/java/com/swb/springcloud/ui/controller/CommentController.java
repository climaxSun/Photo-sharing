package com.swb.springcloud.ui.controller;

import com.swb.springcloud.ui.common.RestResponse;
import com.swb.springcloud.ui.exception.IllegalParamsException;
import com.swb.springcloud.ui.feign.Client.CVFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CVFeignClient cvFeignClient;

    @GetMapping
    public String comment(@RequestParam(value = "flowerId")Long flowerId, Model model,
                          @RequestParam(value = "pageIndex",defaultValue = "0",required = false)int pageIndex){
        RestResponse<Object>commentMap=  cvFeignClient.getComment(flowerId,pageIndex);
        if(commentMap==null ||commentMap.getCode()!=0) {
            model.addAttribute("error", "true");
            return "userspance/comment";
        }
        model.addAttribute("error","false");
        Map<String,Object> map= (Map<String, Object>) commentMap.getResult();
        model.addAttribute("comments",map.get("comments"));
        model.addAttribute("totalElements",map.get("totalElements"));
        model.addAttribute("totalPages",map.get("totalPages"));
        model.addAttribute("number",map.get("number"));
        model.addAttribute("first",map.get("isFirst"));
        model.addAttribute("last",map.get("isLast"));
        return "userspance/comment";
    }
}

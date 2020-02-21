package com.swb.springcloud.ui.controller;

import com.swb.springcloud.ui.common.RestResponse;
import com.swb.springcloud.ui.exception.IllegalParamsException;
import com.swb.springcloud.ui.feign.Client.CVFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private CVFeignClient cvFeignClient;

    @GetMapping("/{type}")
    public String getReportUsers(@RequestParam(value = "pageIndex",defaultValue = "0",required = false)int pageIndex, Model model,
                                 @PathVariable(value = "type")String type){
        RestResponse<Map<String,Object>> restResponse= cvFeignClient.getReport(type,pageIndex);
        if(restResponse ==null || restResponse.getCode()!=0){
            throw new IllegalParamsException(IllegalParamsException.Type.XITONG_FANMANG,"系统繁忙");
        }
        Map<String,Object> map=restResponse.getResult();
        model.addAttribute("reports",map.get("reports"));
        model.addAttribute("totalElements",map.get("totalElements"));
        model.addAttribute("number",map.get("number"));
        model.addAttribute("first",map.get("isFirst"));
        model.addAttribute("totalPages",map.get("totalPages"));
        model.addAttribute("last",map.get("isLast"));
        String view;
        if(type.equals("user")){
            view="admin/report/user";
        }else if(type.equals("comment")){
            view="admin/report/comment";
        }else if(type.equals("flower")){
            view="admin/report/flower";
        }else{
            view="admin/report/article";
        }
        return view;
    }

}

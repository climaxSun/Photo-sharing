package com.swb.springcloud.comment_vote.controller;

import com.swb.springcloud.comment_vote.comment.RestResponse;
import com.swb.springcloud.comment_vote.exception.UserException;
import com.swb.springcloud.comment_vote.feign.Client.UserServiceFeignClient;
import com.swb.springcloud.comment_vote.pojo.Report;
import com.swb.springcloud.comment_vote.pojo.UserReturn;
import com.swb.springcloud.comment_vote.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private UserServiceFeignClient userServiceFeignClient;

    @PostMapping
    public RestResponse<Report> addReport(@RequestParam(value = "reportedId")Long reportedId,
                                          @RequestParam(value = "type")String type,
                                          @RequestParam(value = "reason")String reason){
        Report report=new Report(type,reportedId,reason);
        reportService.save(report);
        return RestResponse.success();
    }

    @PutMapping
    public RestResponse<Report> editReport(@RequestParam(value = "reportId")Long reportId,
                                           @RequestParam(value = "result",defaultValue = "false") boolean result,
                                           @CookieValue(value = "adminToken",required = false)String adminToken){
        RestResponse<UserReturn> restResponse= userServiceFeignClient.getAuth(adminToken);
        if(restResponse.getCode()!=0){
            throw new UserException(UserException.Type.USER_NOT_LOGIN,"token已失效");
        }
        UserReturn userReturn=restResponse.getResult();
        if(userReturn.getLv()!=0 && userReturn.getLv()!=3){
            throw new UserException(UserException.Type.USER_NOT_LOGIN,"权限不足");
        }
        reportService.edit(reportId,result);
        return RestResponse.success();
    }

    @GetMapping
    public RestResponse<Map<String,Object>> getReportAll(@RequestParam(value = "type")String type,
                                                         @RequestParam(value = "pageIndex")int pageIndex){
        Page<Report> reportPage=reportService.findReportByType(type,pageIndex);
        Map<String,Object> map=new HashMap<>();
        map.put("reports",reportPage.getContent());
        map.put("totalElements",reportPage.getTotalElements());
        map.put("number",reportPage.getNumber());
        map.put("totalPages",reportPage.getTotalPages());
        map.put("isFirst",reportPage.isFirst());
        map.put("isLast",reportPage.isLast());
        return RestResponse.success(map);
    }

    @DeleteMapping("/{reportId}")
    public RestResponse<Report> deleteReport(@PathVariable(value = "reportId")Long reportId){
        reportService.deleteReport(reportId);
        return RestResponse.success();
    }

}

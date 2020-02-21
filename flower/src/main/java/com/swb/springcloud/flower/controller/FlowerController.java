package com.swb.springcloud.flower.controller;

import com.swb.springcloud.flower.Util.ToolKit;
import com.swb.springcloud.flower.common.RestCode;
import com.swb.springcloud.flower.common.RestResponse;
import com.swb.springcloud.flower.exception.UserException;
import com.swb.springcloud.flower.feign.Client.UserServiceFeignClient;
import com.swb.springcloud.flower.pojo.Flower;
import com.swb.springcloud.flower.pojo.UserReturn;
import com.swb.springcloud.flower.service.FlowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class FlowerController {

    @Autowired
    private UserServiceFeignClient userServiceFeignClient;

    @Autowired
    private FlowerService flowerService;

    @PostMapping("/flower")
    public RestResponse<Flower> saveFlower(@CookieValue(value = "token",required = false)String token, @RequestParam(value = "photoUrl[]") String[] photoUrl,
                                           @RequestParam(value = "content") String content,@RequestParam(value = "tag") String tag){

        RestResponse restResponse=userServiceFeignClient.getAuth(token);
        if(restResponse.getCode()== RestCode.TOKEN_INVALID.code){
            throw new UserException(UserException.Type.USER_NOT_LOGIN,"用户已过期，请重新登录");
        }
        UserReturn user= (UserReturn) restResponse.getResult();
        content=ToolKit.toHtml(content);
        Flower flower=new Flower(content,photoUrl);
        flower.setTags(tag);
        flower.setUserId(user.getId());
        flower.setUserName(user.getName());
        flower.setUserAvatar(user.getAvatar());
        Flower flowerReturn =flowerService.saveFlower(flower);
        return RestResponse.success(flowerReturn);
    }

    @GetMapping("/getFlower")
    public RestResponse<Map<String,Object>> getFlowers(Long id, Long userId, int pageIndex, int pageSize,
                                       @RequestParam(value = "keyword",required = false,defaultValue = "") String keyword){
        if(keyword.length()!=0){
            keyword=keyword.replaceAll("花","");
        }
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<Flower> flowers = flowerService.getFlowersByUser_id(id,userId,pageable,keyword);
        Map<String,Object> map=new HashMap<>();
        map.put("flower",flowers.getContent());
        map.put("totalElements",flowers.getTotalElements());
        map.put("totalPages",flowers.getTotalPages());
        map.put("number",flowers.getNumber());
        map.put("pageSize",flowers.getSize());
        map.put("isFirst",flowers.isFirst());
        map.put("isLast",flowers.isLast());
        return  RestResponse.success(map);
    }

    @GetMapping("/getFlowerOne")
    public RestResponse<Flower> getFlower(@RequestParam(value = "flowerId") Long flowerId){
        Flower flower=flowerService.getFlowerById(flowerId);
        return  RestResponse.success(flower);
    }

    @DeleteMapping("/{flowerId}")
    public RestResponse<Flower> deleteFlower(@PathVariable(value = "flowerId")Long flowerId,
                                             @CookieValue(value = "adminToken",required = false)String adminToken){
        RestResponse<UserReturn> restResponse = userServiceFeignClient.getAuth(adminToken);
        if(restResponse.getCode()!=0){
            throw new UserException(UserException.Type.USER_NOT_LOGIN,"TOKEN失效");
        }
        UserReturn userReturn=restResponse.getResult();
        if(!userReturn.getLv().equals(0) && !userReturn.getLv().equals(3)){
            throw new UserException(UserException.Type.USER_Insufficient_permissions,"权限不足");
        }
        flowerService.removeFlower(flowerId);
        return  RestResponse.success();
    }


    @GetMapping("/aaa")
    public RestResponse<Boolean> aa(){
        boolean x= flowerService.saveEsFlower();
        return RestResponse.success(x);
    }
}

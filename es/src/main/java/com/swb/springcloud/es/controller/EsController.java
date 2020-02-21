package com.swb.springcloud.es.controller;

import com.swb.springcloud.es.pojo.EsFlower;
import com.swb.springcloud.es.pojo.Flower;
import com.swb.springcloud.es.pojo.TagVO;
import com.swb.springcloud.es.service.EsFlowerService;
import com.swb.springcloud.es.thread.UpdateEsFlower;
import com.swb.springcloud.es.vo.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class EsController {

    @Autowired
    private EsFlowerService esFlowerService;

    @GetMapping("/comment")
    public RestResponse<Object> editFlowerCommentSize(@RequestParam(value = "type")String type,
                                                  @RequestParam(value = "flowerId")Long flowerId){
        esFlowerService.editFlowerComment(type,flowerId);
        return RestResponse.success();
    }

    @GetMapping("/vote")
    public RestResponse<Object> editFlowerVoteSize(@RequestParam(value = "type")String type,
                                                  @RequestParam(value = "flowerId")Long flowerId){
        esFlowerService.editFlowerVote(type,flowerId);
        return RestResponse.success();
    }

    @PostMapping("/flower")
    public RestResponse<EsFlower> saveEsFlower(@RequestBody Flower flower){
        System.out.println(flower);
        EsFlower esFlower=esFlowerService.updateEsFlower(flower);
        System.out.println(esFlower);
        return  RestResponse.success(esFlower);
    }

    @PostMapping("/flowers")
    public RestResponse<EsFlower> saveEsFlowers(@RequestBody List<Flower> flowers){
        System.out.println(flowers);
        UpdateEsFlower updateEsFlower=new UpdateEsFlower(esFlowerService,flowers);
        Thread thread= new Thread(updateEsFlower);
        thread.start();
        return  RestResponse.success();
    }

    @DeleteMapping("/flower/{flowerId}")
    public RestResponse<Object> deleteEsFlower(@PathVariable Long flowerId){
        esFlowerService.removeEsFlower(flowerId);
        return  RestResponse.success();
    }

    @PostMapping("/deleteFlower")
    public RestResponse<Object> deleteEsFlowerByIds(@RequestBody List<Long> flowerId){
        esFlowerService.removeEsFlowerByFlowerIds(flowerId);
        return  RestResponse.success();
    }

    @DeleteMapping("/flowerByUser")
    public RestResponse<Object> deleteEsFlowerByUserId(@RequestParam(value = "userId") Long userId){
        esFlowerService.removeEsFlowerByUserId(userId);
        return  RestResponse.success();
    }

    @GetMapping("/hotUser")
    public RestResponse<List<Long>> getHotUser(){
        List <Long> userIds=esFlowerService.listTop12Users();
        return  RestResponse.success(userIds);
    }

    @GetMapping("/hotTag")
    public RestResponse<List<TagVO>> getHotTag(){
        List<TagVO> tagVOS=esFlowerService.listTop30Tags();
        return  RestResponse.success(tagVOS);
    }

    @GetMapping("/all")
    public RestResponse<Iterable<EsFlower>> getAll(){
        Iterable<EsFlower> esFlowers=  esFlowerService.findAll();
        return  RestResponse.success(esFlowers);
    }

    @GetMapping("/getFlowers")
    public RestResponse<Map<String,Object>> findFlowerByKeyword(@RequestParam(value="order",required=false,defaultValue="new") String order,
                                                                @RequestParam(value="keyword",required=false,defaultValue="" ) String keyword,
                                                                @RequestParam(value="pageIndex",required=false,defaultValue="0") int pageIndex,
                                                                @RequestParam(value="pageSize",required=false,defaultValue="10") int pageSize){
        Pageable pageable= PageRequest.of(pageIndex,pageSize);
        Page<EsFlower> page=null;
        if(order.equals("new")){
            page=esFlowerService.listNewestEsFlowers(keyword,pageable);
        }else{
            page=esFlowerService.listHotestEsFlowers(keyword,pageable);
        }
        Map<String,Object> map=new HashMap<>();
        map.put("flower",page.getContent());
        map.put("totalElements",page.getTotalElements());
        map.put("totalPages",page.getTotalPages());
        map.put("number",page.getNumber());
        map.put("pageSize",page.getSize());
        map.put("isFirst",page.isFirst());
        map.put("isLast",page.isLast());
        return  RestResponse.success(map);
    }
}

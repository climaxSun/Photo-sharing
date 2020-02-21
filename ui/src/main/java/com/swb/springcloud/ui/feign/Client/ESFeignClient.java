package com.swb.springcloud.ui.feign.Client;

import com.swb.springcloud.ui.common.RestResponse;
import com.swb.springcloud.ui.feign.Hystrix.ESFeignHystrix;
import com.swb.springcloud.ui.pojo.Flower;
import com.swb.springcloud.ui.pojo.TagVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "es",fallback = ESFeignHystrix.class)
public interface ESFeignClient {

    @PostMapping("/flower")
    RestResponse<Object> saveEsFlower(@RequestBody Flower flower);

    @PostMapping("/deleteFlower")
    RestResponse<Object> deleteEsFlowerByIds(@RequestBody List<Long> flowerIds);

    @DeleteMapping("/flower/{flowerId}")
    RestResponse<Object> deleteEsFlowerById(@PathVariable(value = "flowerId") Long flowerId);

    @DeleteMapping("/flowerByUser")
    RestResponse<Object> deleteEsFlowerByUserId(@RequestParam(value = "userId") Long userId);

    @GetMapping("/comment")
    RestResponse<Object> editFlowerCommentSize(@RequestParam(value = "type") String type, @RequestParam(value = "flowerId") Long flowerId);

    @GetMapping("/vote")
    RestResponse<Object> editFlowerVoteSize(@RequestParam(value = "type") String type, @RequestParam(value = "flowerId") Long flowerId);

    @PostMapping("/flowers")
    RestResponse<Object> saveEsFlowers(@RequestBody List<Flower> flowers);

    @GetMapping("/hotUser")
    RestResponse<List<Long>> getHotUser();

    @GetMapping("/hotTag")
    RestResponse<List<TagVO>> getHotTag();

    @GetMapping("/getFlowers")
    RestResponse<Map<String, Object>> getFlowers(@RequestParam(value="order") String order, @RequestParam(value="keyword") String keyword,
                                                  @RequestParam(value="pageIndex") int pageIndex, @RequestParam(value="pageSize") int pageSize);
}

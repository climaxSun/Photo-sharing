package com.swb.springcloud.ui.feign.Client;

import com.swb.springcloud.ui.common.RestResponse;
import com.swb.springcloud.ui.feign.Hystrix.CVFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


@FeignClient(name = "comment-vote",fallback = CVFeignHystrix.class)
public interface CVFeignClient {

    @GetMapping("/comment")
    RestResponse<Object> getComment(@RequestParam("flowerId") Long flowerId, @RequestParam("pageIndex") int pageIndex);

    @GetMapping("/report")
    RestResponse<Map<String,Object>> getReport(@RequestParam("type") String type, @RequestParam("pageIndex") int pageIndex);
}

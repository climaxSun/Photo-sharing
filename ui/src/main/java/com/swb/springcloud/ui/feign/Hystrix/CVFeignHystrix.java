package com.swb.springcloud.ui.feign.Hystrix;

import com.swb.springcloud.ui.common.RestResponse;
import com.swb.springcloud.ui.feign.Client.CVFeignClient;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CVFeignHystrix implements CVFeignClient {

    @Override
    public RestResponse<Object> getComment(Long flowerId, int pageIndex) {
        System.out.println("CVFeignClient.getFlower()");
        return null;
    }

    @Override
    public RestResponse<Map<String,Object>> getReport(String type, int pageIndex) {
        return null;
    }

}

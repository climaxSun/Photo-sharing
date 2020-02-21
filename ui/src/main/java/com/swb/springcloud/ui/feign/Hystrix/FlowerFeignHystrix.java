package com.swb.springcloud.ui.feign.Hystrix;

import com.swb.springcloud.ui.common.RestCode;
import com.swb.springcloud.ui.common.RestResponse;
import com.swb.springcloud.ui.exception.IllegalParamsException;
import com.swb.springcloud.ui.feign.Client.FlowerFeignClient;
import com.swb.springcloud.ui.pojo.Flower;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FlowerFeignHystrix implements FlowerFeignClient {

    @Override
    public RestResponse<Object> getFlowers(Long id, Long userId, int pageIndex, int pageSize, String keyword) {
        System.out.println("FlowerFeignHystrix.getFlowers(id,userId)");
        return null;
    }

    @Override
    public RestResponse<Flower> getFlower(Long id) {
        System.out.println("FlowerFeignHystrix.getFlower(id)");
        throw new IllegalParamsException(IllegalParamsException.Type.XITONG_FANMANG,"系统繁忙");
    }

    @Override
    public RestResponse<Object> getArticleById(Long articleId) {
        return RestResponse.error(RestCode.XITONG_FANMANG);
    }

    @Override
    public RestResponse<Map<String, Object>> getArticleAll(int pageIndex,int pageSize, String keyword) {
        return RestResponse.error(RestCode.XITONG_FANMANG);
    }
}

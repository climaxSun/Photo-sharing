package com.swb.springcloud.flower.feign.Hystrix;

import com.swb.springcloud.flower.common.RestResponse;
import com.swb.springcloud.flower.exception.IllegalParamsException;
import com.swb.springcloud.flower.feign.Client.ESFeignClient;
import com.swb.springcloud.flower.pojo.Flower;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ESFeignHystrix implements ESFeignClient {

    @Override
    public RestResponse<Object> saveEsFlower(Flower flower) {
        throw new IllegalParamsException(IllegalParamsException.Type.ES_SERVICE_ERROR,"es服务异常");
    }

    @Override
    public RestResponse<Object> deleteEsFlowerByIds(List<Long> flowerIds) {
        throw new IllegalParamsException(IllegalParamsException.Type.ES_SERVICE_ERROR,"es服务异常");
    }

    @Override
    public RestResponse<Object> deleteEsFlowerById(Long flowerId) {
        throw new IllegalParamsException(IllegalParamsException.Type.ES_SERVICE_ERROR,"es服务异常");
    }

    @Override
    public RestResponse<Object> deleteEsFlowerByUserId(Long userId) {
        throw new IllegalParamsException(IllegalParamsException.Type.ES_SERVICE_ERROR,"es服务异常");
    }

    @Override
    public RestResponse<Object> editFlowerCommentSize(String type, Long flowerId) {
        throw new IllegalParamsException(IllegalParamsException.Type.ES_SERVICE_ERROR,"es服务异常");
    }

    @Override
    public RestResponse<Object> editFlowerVoteSize(String type, Long flowerId) {
        throw new IllegalParamsException(IllegalParamsException.Type.ES_SERVICE_ERROR,"es服务异常");
    }

    @Override
    public RestResponse<Object> saveEsFlowers(List<Flower> flowers) {
        throw new IllegalParamsException(IllegalParamsException.Type.ES_SERVICE_ERROR,"es服务异常");
    }
}

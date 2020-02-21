package com.swb.springcloud.ui.feign.Hystrix;

import com.swb.springcloud.ui.common.RestResponse;
import com.swb.springcloud.ui.exception.IllegalParamsException;
import com.swb.springcloud.ui.feign.Client.ESFeignClient;
import com.swb.springcloud.ui.pojo.Flower;
import com.swb.springcloud.ui.pojo.TagVO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

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

    @Override
    public RestResponse<List<Long>> getHotUser() {
        throw new IllegalParamsException(IllegalParamsException.Type.ES_SERVICE_ERROR, "es服务异常");
    }

    @Override
    public RestResponse<List<TagVO>> getHotTag() {
        throw new IllegalParamsException(IllegalParamsException.Type.ES_SERVICE_ERROR,"es服务异常");
    }

    @Override
    public RestResponse<Map<String, Object>> getFlowers(String order, String keyword, int pageIndex, int pageSize) {
        throw new IllegalParamsException(IllegalParamsException.Type.ES_SERVICE_ERROR,"es服务异常");
    }
}

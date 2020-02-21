package com.swb.springcloud.comment_vote.feign.Hystrix;

import com.swb.springcloud.comment_vote.comment.RestResponse;
import com.swb.springcloud.comment_vote.exception.IllegalParamsException;
import com.swb.springcloud.comment_vote.feign.Client.ESFeignClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ESFeignHystrix implements ESFeignClient {

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
}

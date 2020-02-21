package com.swb.springcloud.es.service;

import com.swb.springcloud.es.pojo.EsFlower;
import com.swb.springcloud.es.pojo.Flower;
import com.swb.springcloud.es.pojo.TagVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EsFlowerService {

    /**
     * 删除Blog
     * @param id
     * @return
     */
    void removeEsFlower(String id);

    void removeEsFlower(Long flowerId);

    /**
     * 更新 EsFlower
     * @param EsFlower
     * @return
     */
    EsFlower updateEsFlower(Flower flower);

    /**
     * 根据id获取Flower
     * @param id
     * @return
     */
    EsFlower getEsFlowersByFlowerId(Long flowerId);

    /**
     * 最新博客列表，分页
     * @param keyword
     * @param pageable
     * @return
     */
    Page<EsFlower> listNewestEsFlowers(String keyword, Pageable pageable);

    /**
     * 最热博客列表，分页
     * @param keyword
     * @param pageable
     * @return
     */
    Page<EsFlower> listHotestEsFlowers(String keyword, Pageable pageable);

    /**
     * 博客列表，分页
     * @param pageable
     * @return
     */
    Page<EsFlower> listEsFlowers(Pageable pageable);
    /**
     * 最新前5
     * @param keyword
     * @return
     */
    List<EsFlower> listTop5NewestEsFlowers();

    /**
     * 最热前5
     * @param keyword
     * @return
     */
    List<EsFlower> listTop5HotestEsFlowers();

    /**
     * 最热前 30 标签
     * @return
     */
    List<TagVO> listTop30Tags();

    /**
     * 最热前12用户
     * @return
     */
    List<Long> listTop12Users();

    Iterable<EsFlower> findAll();

    EsFlower editFlowerComment(String type,Long flowerId);

    EsFlower editFlowerVote(String type,Long flowerId);

    int removeEsFlowerByFlowerIds(List<Long> flowerId);

    int removeEsFlowerByUserId(Long userId);
}

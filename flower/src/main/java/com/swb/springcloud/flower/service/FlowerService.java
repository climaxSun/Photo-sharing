package com.swb.springcloud.flower.service;


import com.swb.springcloud.flower.pojo.Flower;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FlowerService {
    /**
     * Flower
     * @param Flower
     * @return
     */
    Flower saveFlower(Flower flower);

    /**
     * Flower
     * @param id
     * @return
     */
    void removeFlower(Long id);

    /**
     * 根据id获取Flower
     * @param id
     * @return
     */
    Flower getFlowerById(Long id);

    /**
     * 根据多个id获取Flower
     * @param id
     * @return
     */
    List<Flower> getFlowerByAllId(List<Long> id);

    Page<Flower> getFlowersByUser_id(Long id, Long userId, Pageable pageable, String keyword);

    boolean saveEsFlower();
}

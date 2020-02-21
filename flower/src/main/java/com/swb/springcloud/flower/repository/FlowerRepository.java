package com.swb.springcloud.flower.repository;

import com.swb.springcloud.flower.pojo.Flower;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface FlowerRepository extends JpaRepository<Flower, Long> {

    Page<Flower> findAllByIdInOrderByCreateTimeDesc(Collection<Long> id, Pageable pageable);

    Page<Flower> findFlowersByUserIdAndTagsLikeOrderByCreateTimeDesc(Long userId,Pageable pageable,String tags);


}

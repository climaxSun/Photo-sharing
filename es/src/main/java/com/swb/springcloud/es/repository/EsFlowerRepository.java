package com.swb.springcloud.es.repository;

import com.swb.springcloud.es.pojo.EsFlower;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Collection;
import java.util.List;

public interface EsFlowerRepository extends ElasticsearchRepository<EsFlower, String> {

    Page<EsFlower> findDistinctByContentContainingOrTagsContaining(String content, String tags, Pageable pageable);

    EsFlower findByFlowerId(Long flowerId);

    EsFlower deleteEsFlowerByFlowerId(Long flowerId);

    int deleteEsFlowersByFlowerIdIn(Collection<Long> flowerId);

    int deleteEsFlowersByUserIdIs(Long userId);
}

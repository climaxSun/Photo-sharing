package com.swb.springcloud.flower.repository;

import com.swb.springcloud.flower.pojo.Article;
import com.swb.springcloud.flower.pojo.Flower;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article,Long> {

    List<Article> findAllByIdInOrderByCreateTimeDesc(Collection<Long> id);

    Page<Article> findArticlesByTitleLikeOrTagsLikeOrderByCreateTimeDesc(String title,String tags,Pageable Pageable);
}

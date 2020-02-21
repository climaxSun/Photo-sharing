package com.swb.springcloud.flower.service;

import com.swb.springcloud.flower.pojo.Article;
import com.swb.springcloud.flower.pojo.Flower;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ArticleService {

    Article saveArticle(Article article);

    Page<Article> getArticlesByKeyWord(String keyword,int pageIndex,int pageSize);

    Article getArticleById(Long id);

    void removeArticle(Long id);

    List<Article> getArticleByAllId(List<Long> id);
}

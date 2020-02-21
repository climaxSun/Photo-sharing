package com.swb.springcloud.flower.service;

import com.swb.springcloud.flower.exception.IllegalParamsException;
import com.swb.springcloud.flower.pojo.Article;
import com.swb.springcloud.flower.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService{

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public Article saveArticle(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public Page<Article> getArticlesByKeyWord(String keyword, int pageIndex,int pageSize) {
        Pageable pageable= PageRequest.of(pageIndex,pageSize);
        keyword="%"+keyword+"%";
        return articleRepository.findArticlesByTitleLikeOrTagsLikeOrderByCreateTimeDesc(keyword,keyword,pageable);
    }

    @Override
    public Article getArticleById(Long id) {
        Article article=articleRepository.findById(id).orElse(null);
        if(article==null){
            throw new IllegalParamsException(IllegalParamsException.Type.ARICLE_NOT_FIND,"未找到文章");
        }
        return article;
    }

    @Override
    public void removeArticle(Long id) {
        articleRepository.deleteById(id);
    }

    @Override
    public List<Article> getArticleByAllId(List<Long> id) {
        return null;
    }
}

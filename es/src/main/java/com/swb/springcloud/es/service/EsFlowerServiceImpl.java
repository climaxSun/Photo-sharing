package com.swb.springcloud.es.service;

import com.swb.springcloud.es.pojo.EsFlower;
import com.swb.springcloud.es.pojo.Flower;
import com.swb.springcloud.es.pojo.TagVO;
import com.swb.springcloud.es.repository.EsFlowerRepository;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.search.aggregations.AggregationBuilders.terms;

@Service
public class EsFlowerServiceImpl implements EsFlowerService{

    @Autowired
    private EsFlowerRepository esFlowerRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    private static final Pageable TOP_5_PAGEABLE = PageRequest.of(0, 5);
    private static final String EMPTY_KEYWORD = "";

    @Override
    public void removeEsFlower(String id) {
        esFlowerRepository.deleteById(id);
    }

    @Override
    public void removeEsFlower(Long flowerId) {
        esFlowerRepository.deleteEsFlowerByFlowerId(flowerId);
    }

    @Override
    public EsFlower updateEsFlower(Flower flower) {
        EsFlower esFlower= esFlowerRepository.findByFlowerId(flower.getId());
        if(esFlower!=null){
            esFlower.update(flower);
        }else{
            esFlower=new EsFlower(flower);
        }
        return esFlowerRepository.save(esFlower);
    }

    @Override
    public EsFlower getEsFlowersByFlowerId(Long flowerId) {
        return esFlowerRepository.findByFlowerId(flowerId);
    }

    @Override
    public Page<EsFlower> listNewestEsFlowers(String keyword, Pageable pageable) {
        Sort sort=new Sort(Sort.Direction.DESC,"createTime");
        pageable =PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        return esFlowerRepository.findDistinctByContentContainingOrTagsContaining(keyword,keyword,pageable);
    }

    @Override
    public Page<EsFlower> listHotestEsFlowers(String keyword, Pageable pageable) {
        Sort sort = new Sort(Sort.Direction.DESC,"commentSize","voteSize","createTime");
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        return esFlowerRepository.findDistinctByContentContainingOrTagsContaining(keyword,keyword,pageable);
    }

    @Override
    public Page<EsFlower> listEsFlowers(Pageable pageable) {
        return esFlowerRepository.findAll(pageable);
    }

    @Override
    public List<EsFlower> listTop5NewestEsFlowers() {
        Page<EsFlower> page=this.listNewestEsFlowers(EMPTY_KEYWORD,TOP_5_PAGEABLE);
        return page.getContent();
    }

    @Override
    public List<EsFlower> listTop5HotestEsFlowers() {
        Page<EsFlower> page=this.listHotestEsFlowers(EMPTY_KEYWORD,TOP_5_PAGEABLE);
        return page.getContent();
    }

    @Override
    public List<TagVO> listTop30Tags() {
        List<TagVO> list = new ArrayList<>();
        // given
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchAllQuery())
                .withSearchType(SearchType.QUERY_THEN_FETCH)
                .withIndices("flower").withTypes("flower")
                .addAggregation(terms("tags").field("tags").order(BucketOrder.count(false)).size(30))
                .build();
        // when
        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, new ResultsExtractor<Aggregations>() {
            @Override
            public Aggregations extract(SearchResponse response) {
                return response.getAggregations();
            }
        });
        StringTerms modelTerms =  (StringTerms)aggregations.asMap().get("tags");
        Iterator<StringTerms.Bucket> modelBucketIt = modelTerms.getBuckets().iterator();
        while (modelBucketIt.hasNext()) {
            Bucket actiontypeBucket = modelBucketIt.next();

            list.add(new TagVO(actiontypeBucket.getKey().toString(),
                    actiontypeBucket.getDocCount()));
        }
        return list;
    }

    @Override
    public List<Long> listTop12Users() {
        List<Long> userIds = new ArrayList<>();
        // given
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchAllQuery())
                .withSearchType(SearchType.QUERY_THEN_FETCH)
                .withIndices("flower").withTypes("flower")
                .addAggregation(terms("users").field("userId").order(BucketOrder.count(false)).size(12))
                .build();
        // when
        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, new ResultsExtractor<Aggregations>() {
            @Override
            public Aggregations extract(SearchResponse response) {
                return response.getAggregations();
            }
        });

        LongTerms modelTerms =  (LongTerms)aggregations.asMap().get("users");

        Iterator<LongTerms.Bucket> modelBucketIt = modelTerms.getBuckets().iterator();
        while (modelBucketIt.hasNext()) {
            Bucket actiontypeBucket = modelBucketIt.next();
            Long userId = (Long) actiontypeBucket.getKey();
            userIds.add(userId);
        }
        return userIds;
    }

    @Override
    public Iterable<EsFlower> findAll() {
        return esFlowerRepository.findAll();
    }

    @Override
    @Transactional
    public EsFlower editFlowerComment(String type, Long flowerId) {
        EsFlower esFlower=esFlowerRepository.findByFlowerId(flowerId);
        if(type.equals("add")){
            esFlower.addCommentSize();
        }else {
            esFlower.subCommentSize();
        }
        return esFlowerRepository.save(esFlower);
    }

    @Override
    @Transactional
    public EsFlower editFlowerVote(String type, Long flowerId) {
        EsFlower esFlower=esFlowerRepository.findByFlowerId(flowerId);
        System.out.println(esFlower.getId());
        if(type.equals("add")){
            esFlower.addVoteSize();
        }else {
            esFlower.subVoteSize();
        }
        return esFlowerRepository.save(esFlower);
    }

    @Override
    public int removeEsFlowerByFlowerIds(List<Long> flowerId) {
        return esFlowerRepository.deleteEsFlowersByFlowerIdIn(flowerId);
    }

    @Override
    public int removeEsFlowerByUserId(Long userId) {
        return esFlowerRepository.deleteEsFlowersByUserIdIs(userId);
    }
}

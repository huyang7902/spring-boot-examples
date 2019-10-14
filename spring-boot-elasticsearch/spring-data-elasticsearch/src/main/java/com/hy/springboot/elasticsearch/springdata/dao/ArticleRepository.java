package com.hy.springboot.elasticsearch.springdata.dao;

import com.hy.springboot.elasticsearch.springdata.bean.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author yang.hu
 * @Date 2019/10/6 11:44
 */
@Repository
public interface ArticleRepository extends ElasticsearchRepository<Article, Long> {

}
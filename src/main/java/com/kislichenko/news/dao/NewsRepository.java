package com.kislichenko.news.dao;

import com.kislichenko.news.entity.AdBlockTypes;
import com.kislichenko.news.entity.News;
import com.kislichenko.news.entity.Price;
import org.springframework.data.repository.CrudRepository;

public interface NewsRepository extends CrudRepository<News, Long> {
    News findNewsByArticle(String article);

}

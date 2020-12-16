package com.kislichenko.news.dao;

import com.kislichenko.news.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
    News findNewsByArticle(String article);

}

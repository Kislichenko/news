package com.kislichenko.news.services;

import com.kislichenko.news.dao.NewsRepository;
import com.kislichenko.news.dto.NewsDto;
import com.kislichenko.news.dto.ReqDataDto;
import com.kislichenko.news.entity.AppUser;
import com.kislichenko.news.entity.News;
import com.kislichenko.news.entity.Price;
import com.kislichenko.news.entity.ReqData;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewsService {
    NewsRepository newsRepository;
    ModelMapper modelMapper = new ModelMapper();

    public NewsService(NewsRepository newsRepository){
        this.newsRepository = newsRepository;
    }

    public List<NewsDto> getAllNews() {
        List<News> news = newsRepository.findAll();
        List<NewsDto> newsDtos = new ArrayList<>();
        news.forEach(tmpNews -> newsDtos.add(modelMapper.map(tmpNews, NewsDto.class)));
        return newsDtos;
    }

    public NewsDto getNewsById(Long id) {
        if (newsRepository.findById(id).isPresent()) {
            News news = newsRepository.findById(id).get();
            NewsDto newsDto= modelMapper.map(news, NewsDto.class);
            return newsDto;
        } else {
            return null;
        }
    }

    public void addNewNews(NewsDto newsDto) {
        News news = modelMapper.map(newsDto, News.class);
        try {
            newsRepository.save(news);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteNewsById(Long id) {
        newsRepository.deleteById(id);
    }
}

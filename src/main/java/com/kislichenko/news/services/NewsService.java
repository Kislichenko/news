package com.kislichenko.news.services;

import com.kislichenko.news.dao.AppUserRepository;
import com.kislichenko.news.dao.NewsRepository;
import com.kislichenko.news.dto.NewsDTO;
import com.kislichenko.news.entity.News;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewsService {
    NewsRepository newsRepository;
    AppUserRepository appUserRepository;
    ModelMapper modelMapper = new ModelMapper();

    public NewsService(NewsRepository newsRepository,
                       AppUserRepository appUserRepository){
        this.newsRepository = newsRepository;
        this.appUserRepository = appUserRepository;
    }

    public List<NewsDTO> getAllNews() {
        List<News> news = newsRepository.findAll();
        List<NewsDTO> newsDtos = new ArrayList<>();
        news.forEach(tmpNews -> newsDtos.add(modelMapper.map(tmpNews, NewsDTO.class)));
        return newsDtos;
    }

    public NewsDTO getNewsDtoById(Long id) {
        if (newsRepository.findById(id).isPresent()) {
            News news = newsRepository.findById(id).get();
            NewsDTO newsDto= modelMapper.map(news, NewsDTO.class);
            return newsDto;
        } else {
            return null;
        }
    }

    public News getNewsById(Long id) {
        if (newsRepository.findById(id).isPresent()) {
            News news = newsRepository.findById(id).get();
            return news;
        } else {
            return null;
        }
    }

    public void addNewNews(NewsDTO newsDto) {
        News news = modelMapper.map(newsDto, News.class);
        try {
            if(appUserRepository.findByUsername(newsDto.getInfoManager()) !=null){
                news.setInfoManager(appUserRepository.findByUsername(newsDto.getInfoManager()));
            }
            newsRepository.save(news);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteNewsById(Long id) {
        newsRepository.deleteById(id);
    }
}

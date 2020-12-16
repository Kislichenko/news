package com.kislichenko.news.controller;

import com.kislichenko.news.dto.NewsDTO;
import com.kislichenko.news.entity.News;
import com.kislichenko.news.services.NewsService;
import com.kislichenko.news.services.ReportageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
public class NewsController {

    Logger logger = LoggerFactory.getLogger(AdminController.class);
    NewsService newsService;
    ReportageService reportageService;

    public NewsController(NewsService newsService,
                          ReportageService reportageService) {
        this.newsService = newsService;
        this.reportageService = reportageService;
    }

    @Secured("ROLE_INFO_MANAGER")
    @GetMapping("news")
    public ResponseEntity<Object> getNews() {
        logger.debug("Getting all news");
        return new ResponseEntity<>(newsService.getAllNews(), HttpStatus.OK);
    }

    @Secured("ROLE_INFO_MANAGER")
    @RequestMapping(value = "/news/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<String> patchNewsById(@PathVariable Long id, @RequestBody NewsDTO newsDto) {
        logger.debug("Patching the news by info_manager");
        newsService.addNewNews(newsDto);
        News news = newsService.getNewsById(newsDto.getId());
        if (newsDto.isRealization() && news != null) {
            reportageService.createReportageByNews(news);
        }
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @Secured("ROLE_INFO_MANAGER")
    @GetMapping("news/{id}")
    public ResponseEntity<NewsDTO> getNewsById(@PathVariable Long id) {
        logger.debug("Getting news by id = " + id);
        NewsDTO newsDto = newsService.getNewsDtoById(id);
        return new ResponseEntity<>(newsDto, HttpStatus.OK);
    }

    @Secured("ROLE_INFO_MANAGER")
    @DeleteMapping("news/{id}")
    public ResponseEntity<Object> deleteNews(@PathVariable Long id) {
        logger.debug("Deleting news by id = " + id);
        newsService.deleteNewsById(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}

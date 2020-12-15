package com.kislichenko.news.services;

import com.kislichenko.news.dao.NewsRepository;
import com.kislichenko.news.entity.News;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

@Service
public class RssService {

    NewsRepository newsRepository;

    public RssService(NewsRepository newsRepository){
        this.newsRepository = newsRepository;

        URL url = null;
        ArrayList<News> news = new ArrayList<>();
        try {
            url = new URL("https://www.mchs.gov.ru/deyatelnost/press-centr/novosti/rss");
            news = getFeedNews(url);
        }catch (Exception e){
            e.printStackTrace();
        }

        news.forEach(tmpNews -> {
            if (newsRepository.findNewsByArticle(tmpNews.getArticle()) == null)
                newsRepository.save(tmpNews);
        });
    }

    private ArrayList<News> getFeedNews(URL url) throws IOException, FeedException {
        URLConnection urlConnection = url.openConnection();
        urlConnection.setConnectTimeout(4000);

        XmlReader reader = new XmlReader(urlConnection);
        SyndFeed feed = new SyndFeedInput().build(reader);
        ArrayList<News> news = new ArrayList<>();

        for (SyndEntry entry : feed.getEntries()) {

            News item = new News();

            //получаем заголовок новости
            item.setArticle(entry.getTitle());

            //получаем статью из области контента

            Document doc = null;
            if (entry.getForeignMarkup().get(0).toString().contains("yandex")) {
                doc = Jsoup.parse(entry.getForeignMarkup().get(0).getContent().get(0).getValue());
                //System.out.println(doc);

            } else {
                doc = Jsoup.parse(entry.getContents().get(0).getValue());
            }


            //берем тело статьи
            Element article = doc.body();

            item.setBody(article.html());

            news.add(item);

        }

        return news;
    }
}

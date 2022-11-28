package com.ssafy.hh.model.service;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.ssafy.hh.model.NewsDto;

@Service
public class NewsServiceImpl implements NewsService {

    private static final String url = "https://news.naver.com/main/list.naver?mode=LS2D&mid=shm&sid1=101&sid2=260";

    @Override
    public List<NewsDto> getNews() throws Exception  {
        Document document = Jsoup.connect(url).get();
        //Jsoup 커넥션 생성

//        List<String> list = getDataList(document);
        List<NewsDto>list = new ArrayList<>();
        Elements selects = document.select(".type06_headline li");

        int idx = 0;
        for (Element select : selects) {
            if(idx==6){
                break;
            }
            NewsDto news = new NewsDto();
            Elements dts = select.select("dt");
            if(dts.size()==1) { //이미지없는 녀석
                news.setLink(dts.get(0).select("a").attr("href"));
                news.setTitle(dts.get(0).select("a").text());
            }else {
                news.setLink(dts.get(1).select("a").attr("href"));
                news.setTitle(dts.get(1).select("a").text());
            }

            news.setOrigin(select.select(".writing").text());
            news.setTime(select.select(".date").text());
            list.add(news);
            idx++;

        }
        return list;
    } 

}

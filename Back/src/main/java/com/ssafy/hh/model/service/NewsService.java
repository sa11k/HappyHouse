package com.ssafy.hh.model.service;

import java.util.List;

import com.ssafy.hh.model.NewsDto;

public interface NewsService {
	List<NewsDto> getNews() throws Exception; 
}

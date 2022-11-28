package com.ssafy.hh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.hh.model.NewsDto;
import com.ssafy.hh.model.service.NewsService;

@RestController
@RequestMapping("/news")
@CrossOrigin("*")
public class NewsRestController {
	@Autowired
	@Qualifier("newsServiceImpl")
	private NewsService svc;
	
	@GetMapping
	public ResponseEntity<List<NewsDto>> selectAll() throws Exception{
		return new ResponseEntity<List<NewsDto>>(svc.getNews(), HttpStatus.OK);
	}
}

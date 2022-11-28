package com.ssafy.hh.model.service;

import java.util.List;

import com.ssafy.hh.model.NoticeDto;

public interface NoticeService {
	NoticeDto select(int no);
	List<NoticeDto> selectpage(int page);
	List<NoticeDto> selectAll();
	List<NoticeDto> sortHit(List<NoticeDto> array, int start, int end);
	int getcnt();
	int update(NoticeDto notice);
	int delete(int no);
	int insert(NoticeDto notice);
}

package com.ssafy.hh.model.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssafy.hh.model.NoticeDto;

@Repository
public interface NoticeMapper {
	NoticeDto select(int no);
	List<NoticeDto> selectpage(int page);
	int getcnt();
	void hit(NoticeDto notice);
	int update(NoticeDto notice);
	int delete(int no);
	int insert(NoticeDto notice);
	List<NoticeDto> selectAll();
}

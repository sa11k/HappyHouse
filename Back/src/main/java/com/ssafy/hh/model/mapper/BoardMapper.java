package com.ssafy.hh.model.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssafy.hh.model.BoardDto;

@Repository
public interface BoardMapper {
	BoardDto select(int no);
	List<BoardDto> selectpage(int page);
	int getcnt();
	void hit(BoardDto notice);
	int update(BoardDto notice);
	int delete(int no);
	int insert(BoardDto notice);
	List<BoardDto> selectAll();
}

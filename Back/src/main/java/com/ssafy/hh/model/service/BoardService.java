package com.ssafy.hh.model.service;

import java.util.List;

import com.ssafy.hh.model.BoardDto;

public interface BoardService {
	BoardDto select(int no);
	List<BoardDto> selectpage(int page);
	List<BoardDto> selectAll();
	List<BoardDto> sortHit(List<BoardDto> array, int start, int end);
	int getcnt();
	int update(BoardDto notice);
	int delete(int no);
	int insert(BoardDto notice);
}

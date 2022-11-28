package com.ssafy.hh.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.hh.model.BoardDto;
import com.ssafy.hh.model.mapper.BoardMapper;

@Service("boardServiceImpl")
public class BoardServiceImpl implements BoardService {
	@Autowired
	private BoardMapper boardMapper;
	
	@Override
	public BoardDto select(int no){
		boardMapper.hit(boardMapper.select(no));
		return boardMapper.select(no);
	}

	@Override
	public List<BoardDto> selectpage(int page){
		page = (10*(page-1));
		return boardMapper.selectpage(page);
	}

	@Override
	public int update(BoardDto board){
		return boardMapper.update(board);
	}

	@Override
	public int delete(int no){
		return boardMapper.delete(no);
	}
	@Override
	public int getcnt() {
		return boardMapper.getcnt();
	}
	
	@Override
	public int insert(BoardDto board){
		return boardMapper.insert(board);
	}

	@Override
	public List<BoardDto> sortHit(List<BoardDto> array, int start, int end) {
		int p = partition(array, start, end);
		if(start<p-1)
			sortHit(array, start, p-1);
		if(p<end)
			sortHit(array, p, end);	
		return array;
	}
	
	static int partition(List<BoardDto> array, int start, int end) {
		int pivot = array.get((start+end)/2).getHit();
		while(start<=end) {
			while(array.get(start).getHit()>pivot) start++;
			while(array.get(end).getHit()<pivot) end--;
			if(start<=end) {
				BoardDto tmp = array.get(start);
				array.remove(start);
				array.add(start, array.get(end-1));
				array.remove(end);
				array.add(end, tmp);
				start++;
				end--;
			}
		}
		return start;
	}

	@Override
	public List<BoardDto> selectAll() {
		return boardMapper.selectAll();
	}
}

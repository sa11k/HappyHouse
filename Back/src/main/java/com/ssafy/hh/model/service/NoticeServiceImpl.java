package com.ssafy.hh.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.hh.model.NoticeDto;
import com.ssafy.hh.model.mapper.NoticeMapper;

@Service("noticeServiceImpl")
public class NoticeServiceImpl implements NoticeService {
	@Autowired
	private NoticeMapper noticeMapper;
	
	@Override
	public NoticeDto select(int no){
		noticeMapper.hit(noticeMapper.select(no));
		return noticeMapper.select(no);
	}

	@Override
	public List<NoticeDto> selectpage(int page){
		page = (10*(page-1));
		return noticeMapper.selectpage(page);
	}

	@Override
	public int update(NoticeDto notice){
		return noticeMapper.update(notice);
	}

	@Override
	public int delete(int no){
		return noticeMapper.delete(no);
	}
	@Override
	public int getcnt() {
		return noticeMapper.getcnt();
	}
	
	@Override
	public int insert(NoticeDto notice){
		return noticeMapper.insert(notice);
	}

	@Override
	public List<NoticeDto> sortHit(List<NoticeDto> array, int start, int end) {
		int p = partition(array, start, end);
		if(start<p-1)
			sortHit(array, start, p-1);
		if(p<end)
			sortHit(array, p, end);	
		return array;
	}
	
	static int partition(List<NoticeDto> array, int start, int end) {
		int pivot = array.get((start+end)/2).getHit();
		while(start<=end) {
			while(array.get(start).getHit()>pivot) start++;
			while(array.get(end).getHit()<pivot) end--;
			if(start<=end) {
				NoticeDto tmp = array.get(start);
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
	public List<NoticeDto> selectAll() {
		return noticeMapper.selectAll();
	}
}

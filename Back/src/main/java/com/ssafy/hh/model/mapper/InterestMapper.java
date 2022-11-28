package com.ssafy.hh.model.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssafy.hh.model.InterestDto;

@Repository
public interface InterestMapper {
	int insert(InterestDto dto);
	List<InterestDto> selectall(String user_id);
	int select_no(InterestDto dto);
	int delete(int no);
}

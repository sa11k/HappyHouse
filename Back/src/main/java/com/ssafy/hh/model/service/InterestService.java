package com.ssafy.hh.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.hh.model.InterestDto;

public interface InterestService {
	int insert(InterestDto dto) throws SQLException;
	List<InterestDto> selectall(String user_id);
	int delete(int no);
}

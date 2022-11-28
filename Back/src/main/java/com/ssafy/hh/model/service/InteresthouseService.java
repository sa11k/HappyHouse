package com.ssafy.hh.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.hh.model.InteresthouseDto;

public interface InteresthouseService {
	int insert(InteresthouseDto dto) throws SQLException;
	List<InteresthouseDto> selectall(String user_id);
	int delete(int no);
}

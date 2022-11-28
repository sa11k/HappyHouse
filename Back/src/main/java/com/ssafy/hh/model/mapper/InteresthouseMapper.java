package com.ssafy.hh.model.mapper;

import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssafy.hh.model.InteresthouseDto;

@Repository
public interface InteresthouseMapper {
	int insert(InteresthouseDto dto);
	List<InteresthouseDto> selectall(String user_id);
	BigInteger select_no(InteresthouseDto dto);
	String select_deal(InteresthouseDto dto);
	int delete(int no);
	String select_name(InteresthouseDto dto);
	String select_dongCode(InteresthouseDto dto);
	String select_sidoName(String dongCode);
	String select_gugunName(String dongCode);
	String select_dongName(String dongCode);
	int deletedetail(BigInteger no);
}

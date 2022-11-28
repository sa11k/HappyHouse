package com.ssafy.hh.model.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.hh.model.InteresthouseDto;
import com.ssafy.hh.model.mapper.HouseMapMapper;
import com.ssafy.hh.model.mapper.InteresthouseMapper;

@Service("interesthouseServiceImpl")
public class InteresthouseServiceImpl implements InteresthouseService {
	@Autowired
	private InteresthouseMapper interesthouseMapper;
	
	@Override
	public int insert(InteresthouseDto dto) throws SQLException{
		dto.setAptCode(interesthouseMapper.select_no(dto));
		dto.setDealAmount(interesthouseMapper.select_deal(dto));
		dto.setApartmentName(interesthouseMapper.select_name(dto));
		dto.setDongCode(interesthouseMapper.select_dongCode(dto));
		dto.setSidoName(interesthouseMapper.select_sidoName(interesthouseMapper.select_dongCode(dto)));
		dto.setGugunName(interesthouseMapper.select_gugunName(interesthouseMapper.select_dongCode(dto)));
		dto.setDongName(interesthouseMapper.select_dongName(interesthouseMapper.select_dongCode(dto)));
		return interesthouseMapper.insert(dto);
	}

	@Override
	public List<InteresthouseDto> selectall(String user_id){
		return interesthouseMapper.selectall(user_id);
	}

	@Override
	public int delete(int no) {
		return interesthouseMapper.delete(no);
	}

}

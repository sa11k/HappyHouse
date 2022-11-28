package com.ssafy.hh.model.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.hh.model.InterestDto;
import com.ssafy.hh.model.mapper.InterestMapper;

@Service("interestServiceImpl")
public class InterestServiceImpl implements InterestService {
	@Autowired
	private InterestMapper interestMapper;
	
	@Override
	public int insert(InterestDto dto) throws SQLException{
		int no=interestMapper.select_no(dto);
		if(no == 0) throw new SQLException("해당하는 주소가 없음");
		dto.setBaseaddress_no(no);
		return interestMapper.insert(dto);
	}

	@Override
	public List<InterestDto> selectall(String user_id){
		return interestMapper.selectall(user_id);
	}

	@Override
	public int delete(int no) {
		return interestMapper.delete(no);
	}

}

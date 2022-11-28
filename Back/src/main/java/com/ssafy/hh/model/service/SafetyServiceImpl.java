package com.ssafy.hh.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.hh.model.SafetyDto;
import com.ssafy.hh.model.mapper.SafetyMapper;

@Service("safetyServiceImpl")
public class SafetyServiceImpl implements SafetyService {
	
	@Autowired
	private SafetyMapper safetyMapper;
	
	@Override
	public List<SafetyDto> getSafety() throws Exception {
		return safetyMapper.getSafety();
	}

}

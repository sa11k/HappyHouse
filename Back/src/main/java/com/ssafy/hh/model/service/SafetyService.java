package com.ssafy.hh.model.service;

import java.util.List;

import com.ssafy.hh.model.SafetyDto;

public interface SafetyService {
	List<SafetyDto> getSafety() throws Exception;
}

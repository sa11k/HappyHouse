package com.ssafy.hh.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.hh.model.HouseInfoDto;
import com.ssafy.hh.model.SidoGugunCodeDto;
import com.ssafy.hh.model.HouseDetailDto;

public interface HouseMapService {

	List<SidoGugunCodeDto> getSido() throws Exception;
	List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception;
	List<HouseInfoDto> getDongInGugun(String gugun) throws Exception;
	List<HouseInfoDto> getAptInDong(String dong) throws Exception;
	String getAddress(String dongcode) throws Exception;
	List<HouseDetailDto> getDetail(String aptCode) throws Exception;
	int updateHit(String aptCode) throws Exception;
	void sms(List<String> userlist);
} 

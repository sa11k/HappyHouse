package com.ssafy.hh.model.mapper;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssafy.hh.model.AddressDto;
import com.ssafy.hh.model.HouseDetailDto;
import com.ssafy.hh.model.HouseInfoDto;
import com.ssafy.hh.model.SidoGugunCodeDto;

@Mapper
public interface HouseMapMapper {

	List<SidoGugunCodeDto> getSido() throws SQLException;
	List<SidoGugunCodeDto> getGugunInSido(String sido) throws SQLException;
	List<HouseInfoDto> getDongInGugun(String gugun) throws SQLException;
	List<HouseInfoDto> getAptInDong(String dong) throws SQLException;
	AddressDto getAddress(String dongcode) throws SQLException;
	List<HouseDetailDto> getDetail(String aptCode) throws SQLException;
	int updateHit(String aptCode) throws SQLException;
	int isPos(String aptCode);
	int registDeal(String dealAmount, String area,  String floor, BigInteger aptCode);
	int registDetail(String description, BigInteger aptCode);
	int registInfo(BigInteger aptCode, int buildYear, String dong, String sigunguCode, String eubmyundongCode, String dongCode, String apartmentName, String lng, String lat, String jibun);
	String searchdongCode(String sido, String gugun, String dong);
	BigInteger aptCode();
	int getBaseAddressNo(String dongCode);
	String getUserIdInterest(int baseaddressNo);
	List<String> isInterestHouseUser(BigInteger no);
}

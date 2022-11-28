package com.ssafy.hh.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.hh.model.StoreDto;

@Mapper
public interface StoreMapper {
	List<StoreDto> getCafe(String dong) throws SQLException;
	List<StoreDto> getRestaurant(String dong) throws SQLException;
	List<StoreDto> getAlcohol(String dong) throws SQLException;
	List<StoreDto> getGrocery(String dong) throws SQLException;
	List<StoreDto> getEducation(String dong) throws SQLException;
}

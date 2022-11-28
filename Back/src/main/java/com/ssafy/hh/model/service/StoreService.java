package com.ssafy.hh.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.hh.model.StoreDto;

public interface StoreService {
	List<StoreDto> getCafe(String dong) throws Exception;
	List<StoreDto> getRestaurant(String dong) throws Exception;
	List<StoreDto> getAlcohol(String dong) throws Exception;
	List<StoreDto> getGrocery(String dong) throws Exception;
	List<StoreDto> getEducation(String dong) throws Exception;
}

package com.ssafy.hh.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.hh.model.StoreDto;
import com.ssafy.hh.model.mapper.StoreMapper;

@Service("storeServiceImpl")
public class StoreServiceImpl implements StoreService {
	
	@Autowired
	private StoreMapper storeMapper;
	
	@Override
	public List<StoreDto> getCafe(String dong) throws Exception {
		return storeMapper.getCafe(dong);
	}

	@Override
	public List<StoreDto> getRestaurant(String dong) throws Exception {
		return storeMapper.getRestaurant(dong);
	}
	
	@Override
	public List<StoreDto> getAlcohol(String dong) throws Exception {
		return null;
	}

	@Override
	public List<StoreDto> getGrocery(String dong) throws Exception {
		return null;
	}

	@Override
	public List<StoreDto> getEducation(String dong) throws Exception {
		return null;
	}

}

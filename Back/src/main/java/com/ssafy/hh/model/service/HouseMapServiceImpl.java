package com.ssafy.hh.model.service;

import java.util.List;
import java.util.HashMap;
import org.json.simple.JSONObject;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.hh.model.AddressDto;
import com.ssafy.hh.model.HouseInfoDto;
import com.ssafy.hh.model.SidoGugunCodeDto;
import com.ssafy.hh.model.HouseDetailDto;
import com.ssafy.hh.model.mapper.HouseMapMapper;

@Service("houseMapServiceImpl")
public class HouseMapServiceImpl implements HouseMapService {
	
	String api_key = "";
    String api_secret = "";
    Message coolsms = new Message(api_key, api_secret);
	
	@Autowired
	private HouseMapMapper houseMapMapper;

	@Override
	public List<SidoGugunCodeDto> getSido() throws Exception {
		return houseMapMapper.getSido();
	}

	@Override
	public List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception {
		return houseMapMapper.getGugunInSido(sido);
	}

	@Override
	public List<HouseInfoDto> getDongInGugun(String gugun) throws Exception {
		return houseMapMapper.getDongInGugun(gugun);
	}

	@Override
	public List<HouseInfoDto> getAptInDong(String dong) throws Exception {
		return houseMapMapper.getAptInDong(dong);
	}

	@Override
	public String getAddress(String dongcode) throws Exception {
		AddressDto addressDto = houseMapMapper.getAddress(dongcode);
		StringBuilder address = new StringBuilder();
		address.append(addressDto.getSidoName()).append(" ").append(addressDto.getGugunName()).append(" ").append(addressDto.getDongName());
		return address.toString();
	}

	@Override
	public List<HouseDetailDto> getDetail(String aptCode) throws Exception {
		// TODO Auto-generated method stub
		return houseMapMapper.getDetail(aptCode);
	}

	@Override
	public int updateHit(String aptCode) throws Exception {
		// TODO Auto-generated method stub
		return houseMapMapper.updateHit(aptCode);
	}
	
	@Override
	public void sms(List<String> userlist) {
		
		for(int i = 0; i<userlist.size(); i++) {
			HashMap<String, String> params = new HashMap<String, String>();
		    params.put("to", userlist.get(i));
		    params.put("from", "01012345678");
		    params.put("type", "SMS");
		    params.put("text", "안녕하세요. HAPPYHOUSE 입니다. 관심 지역에 새로운 매물이 등록되었습니다. 행복한 하루 되세요!");
		    
		    try {
		        JSONObject obj = (JSONObject) coolsms.send(params);
		        System.out.println(obj.toString());
		      } catch (CoolsmsException e) {
		        System.out.println(e.getMessage());
		        System.out.println(e.getCode());
		      }
			
		}
		
		
		
	}
	

}

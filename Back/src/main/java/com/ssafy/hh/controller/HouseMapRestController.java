package com.ssafy.hh.controller;


import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.hh.model.BoardDto;
import com.ssafy.hh.model.HouseDetailDto;
import com.ssafy.hh.model.HouseInfoDto;
import com.ssafy.hh.model.SidoGugunCodeDto;
import com.ssafy.hh.model.mapper.HouseMapMapper;
import com.ssafy.hh.model.mapper.UserMapper;
import com.ssafy.hh.model.service.HouseMapService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/map")
@CrossOrigin("*")
//@CrossOrigin(origins = { "*" }, maxAge = 6000)
public class HouseMapRestController {
	
	private final Logger logger = LoggerFactory.getLogger(HouseMapRestController.class);

	@Autowired
	@Qualifier("houseMapServiceImpl")
	private HouseMapService haHouseMapService;
	
	@Autowired
	private UserMapper uMapper;
	
	@Autowired
	private HouseMapMapper mapper;
	
	@GetMapping("/sido")
	public ResponseEntity<List<SidoGugunCodeDto>> sido() throws Exception {
		logger.debug("sido : {}", haHouseMapService.getSido());
		return new ResponseEntity<List<SidoGugunCodeDto>>(haHouseMapService.getSido(), HttpStatus.OK);
	}
	
	@GetMapping("/gugun")
	public ResponseEntity<List<SidoGugunCodeDto>> gugun(@RequestParam("sido") String sido) throws Exception {
		return new ResponseEntity<List<SidoGugunCodeDto>>(haHouseMapService.getGugunInSido(sido), HttpStatus.OK);
	}
	
	@GetMapping("/dong")
	public ResponseEntity<List<HouseInfoDto>> dong(@RequestParam("gugun") String gugun) throws Exception {
		return new ResponseEntity<List<HouseInfoDto>>(haHouseMapService.getDongInGugun(gugun), HttpStatus.OK);
	}
	
	
	@GetMapping("/apt")
	public ResponseEntity<List<HouseInfoDto>> apt(@RequestParam("dong") String dong) throws Exception {
		System.out.println(haHouseMapService.getAptInDong(dong));
		return new ResponseEntity<List<HouseInfoDto>>(haHouseMapService.getAptInDong(dong), HttpStatus.OK);
	}
	
	@GetMapping("/address")
	public ResponseEntity<String> address(@RequestParam("dong") String dongcode) throws Exception {
		return new ResponseEntity<String>(haHouseMapService.getAddress(dongcode), HttpStatus.OK);
	}
	
	@GetMapping("/interest/{id}/{no}")
	public ResponseEntity<String> getuserInterest(@PathVariable String id, @PathVariable BigInteger no) throws Exception {
		List<String> user = mapper.isInterestHouseUser(no);
		for(int i = 0; i<user.size(); i++) {
			if(id.equals(user.get(i))) {
				return new ResponseEntity<String>("success", HttpStatus.OK);
			}
			
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/detail")
	public ResponseEntity<List<HouseDetailDto>> getDetail(@RequestParam("aptCode") String aptCode) throws Exception {
		return new ResponseEntity<List<HouseDetailDto>>(haHouseMapService.getDetail(aptCode), HttpStatus.OK);
	}
	
	@PutMapping("/updatehit")
	//public ResponseEntity<String> update(@RequestBody Map<String, String> map) throws Exception {
	public ResponseEntity<String> update(@RequestParam("aptCode") String aptCode) throws Exception {
		//System.out.println(map);
		//if (haHouseMapService.updateHit(map.get("aptCode"))>=1) {
		if (haHouseMapService.updateHit(aptCode)>=1) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}
	
	@PostMapping("/registDeal")
	public ResponseEntity<String> registDeal(@RequestParam("dealAmount") String dealAmount, @RequestParam("area") String area, @RequestParam("floor") String floor) throws Exception {
		
		BigInteger aptCode = mapper.aptCode().add(BigInteger.ONE);
		System.out.println(aptCode);
		if (mapper.registDeal(dealAmount, area, floor, aptCode)>=1) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}
	
	@PostMapping("/registDetail")
	public ResponseEntity<String> registDetail(@RequestParam("description") String description) throws Exception {
		BigInteger aptCode = mapper.aptCode().add(BigInteger.ONE);
		if (mapper.registDetail(description, aptCode)>=1) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}
	
	@PostMapping("/registInfo")
	public ResponseEntity<String> registInfo(@RequestParam("address") String address,@RequestParam("buildYear") String buildYear, @RequestParam("dong") String dong, @RequestParam("apartmentName") String apartmentName, @RequestParam("lng") String lng,  @RequestParam("lat") String lat) throws Exception {
				
		BigInteger aptCode = mapper.aptCode().add(BigInteger.ONE);
		
		StringTokenizer st = new StringTokenizer(address);
		
		String sidoName = st.nextToken();
		String gugunName = st.nextToken();
		String dongName = st.nextToken();
		String jibun = st.nextToken();
		
		
		String dongCode = mapper.searchdongCode(sidoName, gugunName, dongName);
		int baseaddressNo = mapper.getBaseAddressNo(dongCode);
		String user_id = mapper.getUserIdInterest(baseaddressNo);
		
		
		System.out.println(uMapper.smsUser(user_id));

		haHouseMapService.sms(uMapper.smsUser(user_id));
	
		
		if (mapper.registInfo(aptCode, Integer.parseInt(buildYear), dongName, dongCode.substring(0, 5), dongCode.substring(5,10), dongCode, apartmentName, lng, lat, jibun)>=1) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}
	
	
	
	@GetMapping("/result")
	public String result() {
		return "searchResult";
	}
	
}

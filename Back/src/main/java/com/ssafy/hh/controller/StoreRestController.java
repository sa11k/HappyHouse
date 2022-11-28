package com.ssafy.hh.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.hh.model.StoreDto;
import com.ssafy.hh.model.service.StoreService;

@RestController
@RequestMapping("/store")
@CrossOrigin("*")
public class StoreRestController {
	private final Logger logger = LoggerFactory.getLogger(HouseMapRestController.class);
	
	@Autowired
	@Qualifier("storeServiceImpl")
	private StoreService StoreService;
	
	@ResponseBody
	@GetMapping("/cafe")
	public ResponseEntity<List<StoreDto>> cafe(@RequestParam("dong") String dong) throws Exception{
		return new ResponseEntity<List<StoreDto>>(StoreService.getCafe(dong), HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping("/restaurant")
	public ResponseEntity<List<StoreDto>> restaurant(@RequestParam("dong") String dong) throws Exception{
		return new ResponseEntity<List<StoreDto>>(StoreService.getRestaurant(dong), HttpStatus.OK);
	}
}













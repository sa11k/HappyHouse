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
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.hh.model.SafetyDto;
import com.ssafy.hh.model.service.SafetyService;

@RestController
@RequestMapping("/safety")
@CrossOrigin("*")
public class SafetyController {
	private final Logger logger = LoggerFactory.getLogger(HouseMapRestController.class);
	
	@Autowired
	@Qualifier("safetyServiceImpl")
	private SafetyService safetyService;
	
	@GetMapping("/all")
	public ResponseEntity<List<SafetyDto>> getAll() throws Exception {
		logger.debug("safety all : {}", safetyService.getSafety());
		return new ResponseEntity<List<SafetyDto>>(safetyService.getSafety(), HttpStatus.OK);
	}
}

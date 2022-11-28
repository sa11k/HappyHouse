package com.ssafy.hh.controller;

import java.math.BigInteger;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.hh.model.InterestDto;
import com.ssafy.hh.model.InteresthouseDto;
import com.ssafy.hh.model.NoticeDto;
import com.ssafy.hh.model.UserDto;
import com.ssafy.hh.model.mapper.InteresthouseMapper;
import com.ssafy.hh.model.service.InterestService;
import com.ssafy.hh.model.service.InteresthouseService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/interesthouse")
public class InteresthouseRestController{
	@Autowired
	@Qualifier("interesthouseServiceImpl")
	private InteresthouseService svc;
	
	@Autowired
	private InteresthouseMapper mapper;
	
	@GetMapping("{id}")
	public ResponseEntity<Model> interestform(Model m, @PathVariable String id) {
		m.addAttribute("interesthouselist", svc.selectall(id));
		return new ResponseEntity<Model>(m, HttpStatus.OK);
	}
	
	@GetMapping("/regist/{userId}/{housedeal_no}")
	public ResponseEntity<String> regist(@PathVariable String userId, @PathVariable BigInteger housedeal_no) throws SQLException {
		InteresthouseDto i = new InteresthouseDto(housedeal_no, userId);	
		if (svc.insert(i)>=1) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("{no}")
	public ResponseEntity<String> delete(@PathVariable int no) {
		if (svc.delete(no)>=1) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("/detail/{no}")
	public ResponseEntity<String> deletedetail(@PathVariable BigInteger no) {
	System.out.println("i");
		if (mapper.deletedetail(no)>=1) {
			System.out.println("삭제 성공");
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}
}


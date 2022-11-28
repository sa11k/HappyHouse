package com.ssafy.hh.controller;

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
import com.ssafy.hh.model.NoticeDto;
import com.ssafy.hh.model.UserDto;
import com.ssafy.hh.model.service.InterestService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/interest")
public class InterestRestController{
	@Autowired
	@Qualifier("interestServiceImpl")
	private InterestService svc;
	
	@GetMapping("/interestform/{id}")
	public ResponseEntity<Model> interestform(Model m, @PathVariable String id) {
		m.addAttribute("interestlist", svc.selectall(id));
		return new ResponseEntity<Model>(m, HttpStatus.OK);
	}
	
	@PostMapping("/regist/{userId}/{dong}")
	public ResponseEntity<String> regist(@PathVariable String userId, @PathVariable String dong) throws SQLException {
		InterestDto i = new InterestDto(dong, userId);
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
}


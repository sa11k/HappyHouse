package com.ssafy.hh.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.hh.model.NoticeDto;
import com.ssafy.hh.model.service.NoticeService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/notice")
public class NoticeRestController{
	@Autowired
	@Qualifier("noticeServiceImpl")
	private NoticeService svc;
	
	@GetMapping("/index")
	public ResponseEntity<Model> index(Model m) {
		m.addAttribute("notices", svc.selectpage(1));
		m.addAttribute("nowpage", 1);
		m.addAttribute("cnt", svc.getcnt());
		return new ResponseEntity<Model>(m, HttpStatus.OK);
	}
	
	@GetMapping("/detail/{no}")
	public ResponseEntity<NoticeDto> detailnotice(@PathVariable String no) {
		int num = Integer.parseInt(no);
		return new ResponseEntity<NoticeDto>(svc.select(num), HttpStatus.OK);
	}
	
	@GetMapping("/movepage/{page}")
	public ResponseEntity<Model> movepage(Model m,@PathVariable int page) {
		m.addAttribute("notices", svc.selectpage(page));
		m.addAttribute("nowpage", page);
		m.addAttribute("cnt", svc.getcnt());
		return new ResponseEntity<Model>(m, HttpStatus.OK);
	}
	
	@GetMapping("/registform")
	public String registform() {
		return "notice/regist";
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> regist(@RequestBody NoticeDto notice) {
		NoticeDto n = new NoticeDto(notice.getTitle(), notice.getContent(), notice.getUser_id(), "운영자");
		if (svc.insert(n)>=1) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/updateform/{no}")
	public ResponseEntity<NoticeDto> updateform(@PathVariable String no) {
		int num = Integer.parseInt(no);
		return new ResponseEntity<NoticeDto>(svc.select(num), HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<String> update(@RequestBody NoticeDto notice) {
		if (svc.update(notice)>=1) {
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


package com.ssafy.hh.controller;

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

import com.ssafy.hh.model.BoardDto;
import com.ssafy.hh.model.service.BoardService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/board")
public class BoardRestController{
	@Autowired
	@Qualifier("boardServiceImpl")
	private BoardService svc;
	
	@GetMapping("/index")
	public ResponseEntity<Model> index(Model m) {
		m.addAttribute("boards", svc.selectpage(1));
		m.addAttribute("nowpage", 1);
		m.addAttribute("cnt", svc.getcnt());
		return new ResponseEntity<Model>(m, HttpStatus.OK);
	}
	
	@GetMapping("/detail/{no}")
	public ResponseEntity<BoardDto> detailboard(@PathVariable String no) {
		int num = Integer.parseInt(no);
		return new ResponseEntity<BoardDto>(svc.select(num), HttpStatus.OK);
	}
	
	@GetMapping("/movepage/{page}")
	public ResponseEntity<Model> movepage(Model m,@PathVariable int page) {
		m.addAttribute("boards", svc.selectpage(page));
		m.addAttribute("nowpage", page);
		m.addAttribute("cnt", svc.getcnt());
		return new ResponseEntity<Model>(m, HttpStatus.OK);
	}
	
	@GetMapping("/registform")
	public String registform() {
		return "board/regist";
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> regist(@RequestBody BoardDto board) {
		BoardDto n = new BoardDto(board.getTitle(), board.getContent(), board.getUser_id(), board.getUser_name());
		if (svc.insert(n)>=1) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/updateform/{no}")
	public ResponseEntity<BoardDto> updateform(@PathVariable String no) {
		int num = Integer.parseInt(no);
		return new ResponseEntity<BoardDto>(svc.select(num), HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<String> update(@RequestBody BoardDto board) {
		if (svc.update(board)>=1) {
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


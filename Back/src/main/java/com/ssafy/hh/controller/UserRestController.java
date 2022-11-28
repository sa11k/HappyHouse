package com.ssafy.hh.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.jni.Time;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.hh.model.NoticeDto;
import com.ssafy.hh.model.UserDto;
import com.ssafy.hh.model.service.InterestService;
import com.ssafy.hh.model.service.JwtServiceImpl;
import com.ssafy.hh.model.service.UserService;

import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/user")
public class UserRestController {
	
	public static final Logger logger = LoggerFactory.getLogger(UserRestController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService usvc;
	
	@Autowired
	@Qualifier("interestServiceImpl")
	private InterestService isvc;
	
	@Autowired
	private JwtServiceImpl jwtService;
	
	@GetMapping("/idcheck/{id}")
	public ResponseEntity<String> idcheck(@PathVariable String id) throws Exception {
		System.out.println(id);
		if (usvc.idCheck(id)!=1) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}
	

	@PostMapping("/register")
	public ResponseEntity<String> regist(@RequestBody UserDto user) throws Exception {
		UserDto n = new UserDto(user.getId(), user.getPw(), user.getName(), user.getEmail(), user.getAddress(), user.getIsAgent(), user.getSms(), user.getPhone());
		if (usvc.registerUser(n)>=1) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}

	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody @ApiParam(value = "로그인 시 필요한 회원정보(아이디, 비밀번호).", required = true) UserDto memberDto) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			UserDto loginUser = usvc.login(memberDto);
			System.out.println(memberDto);
			if (loginUser != null) {
				String token = jwtService.create("userid", loginUser.getId(), "access-token");// key, data, subject
				logger.debug("로그인 토큰정보 : {}", token);
				resultMap.put("access-token", token);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} else {
				resultMap.put("message", FAIL);
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			logger.error("로그인 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	
	@GetMapping("/list")
	public ResponseEntity<Model> list(Model m) throws Exception {
		m.addAttribute("users", usvc.listUser());
		return new ResponseEntity<Model>(m, HttpStatus.OK);
	}
	
	@GetMapping("/info/{id}")
	public ResponseEntity<Map<String, Object>> getInfo(
			@PathVariable("id") @ApiParam(value = "인증할 회원의 아이디.", required = true) String userid,
			HttpServletRequest request) {
//		logger.debug("userid : {} ", userid);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		if (jwtService.isUsable(request.getHeader("access-token"))) {
			logger.info("사용 가능한 토큰!!!");
			try {
//				로그인 사용자 정보.
				UserDto memberDto = usvc.getUser(userid);
				resultMap.put("userInfo", memberDto);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				logger.error("정보조회 실패 : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			logger.error("사용 불가능 토큰!!!");
			resultMap.put("message", FAIL);
			status = HttpStatus.ACCEPTED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@GetMapping("/updateform")
	private String updateform(HttpSession session, Model m) {
		UserDto user=(UserDto)session.getAttribute("logininfo");
		m.addAttribute("userinfo", user);
		return "/user/updateinfo";
	}
	
	@PutMapping("/update")
	public ResponseEntity<String> update(@RequestBody UserDto user) throws Exception {
		if (usvc.updateUser(user)>=1) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<String> delete(@PathVariable String id) throws Exception {
		if (usvc.deleteUser(id)>=1) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}
	
}

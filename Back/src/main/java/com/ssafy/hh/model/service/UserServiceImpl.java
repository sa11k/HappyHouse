package com.ssafy.hh.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.hh.model.UserDto;
import com.ssafy.hh.model.mapper.UserMapper;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public int idCheck(String checkId) throws Exception {
		return userMapper.idCheck(checkId); // 0 or 1
	}

	@Override
	public int registerUser(UserDto userDto) throws Exception {
		return userMapper.registerUser(userDto);
	}

	@Override
	public UserDto login(UserDto user) throws Exception {
		if(user.getId()==null||user.getPw()==null)
			return null;
		return userMapper.login(user);
	}
	
	@Override
	public List<UserDto> listUser() throws Exception {
		return userMapper.listUser();
	}

	@Override
	public UserDto getUser(String userId) throws Exception {
		return userMapper.getUser(userId);
	}

	@Override
	public int updateUser(UserDto userDto) throws Exception {
		return userMapper.updateUser(userDto);
	}

	@Override
	public int deleteUser(String userId) throws Exception {
		return userMapper.deleteUser(userId);
	}

}

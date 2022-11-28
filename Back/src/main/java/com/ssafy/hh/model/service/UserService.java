package com.ssafy.hh.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.hh.model.UserDto;

public interface UserService {
	UserDto login(UserDto user) throws Exception;

	int idCheck(String checkId) throws Exception;

	int registerUser(UserDto userDto) throws Exception;

	List<UserDto> listUser() throws Exception;

	UserDto getUser(String id) throws Exception;

	int updateUser(UserDto userDto) throws Exception;

	int deleteUser(String id) throws Exception;
}

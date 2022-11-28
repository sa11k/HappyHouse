package com.ssafy.hh.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.hh.model.UserDto;

@Mapper
public interface UserMapper {
	UserDto login(UserDto user) throws Exception;

	int idCheck(String checkId) throws Exception;

	int registerUser(UserDto userDto) throws Exception;

	List<UserDto> listUser() throws Exception;

	UserDto getUser(String userId) throws Exception;

	int updateUser(UserDto userDto) throws Exception;

	int deleteUser(String userId) throws Exception;
	
	List<String> smsUser(String user_id) throws Exception;
}

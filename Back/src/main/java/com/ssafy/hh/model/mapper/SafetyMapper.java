package com.ssafy.hh.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.hh.model.SafetyDto;

@Mapper
public interface SafetyMapper {
	List<SafetyDto> getSafety();
}

package com.ssafy.hh.model;

import java.math.BigInteger;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HouseInfoDto {

	private BigInteger aptCode;
	private String apartmentName;
	private String dongCode;
	private String dong;
	private String sidoName;
	private String gugunName;
	private int buildYear;
	private String jibun;
	private String lat;
	private String lng;
	private String recentPrice;
	private String floor;
	private String area;
	private BigInteger housedeal_no;
	private int baseaddress_no;
}


//List<HouseInfoDto>
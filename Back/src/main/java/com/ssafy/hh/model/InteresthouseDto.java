package com.ssafy.hh.model;

import java.math.BigInteger;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InteresthouseDto {
	private int no;
	private BigInteger housedeal_no;
	private String user_id;
	private BigInteger aptCode;
	private String apartmentName;
	private String dealAmount;
	private String dongCode;
	private String sidoName;
	private String gugunName;
	private String dongName;
	
	public InteresthouseDto(BigInteger housedeal_no, String user_id) {
		this.housedeal_no = housedeal_no;
		this.user_id = user_id;
	}	
}

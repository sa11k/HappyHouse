package com.ssafy.hh.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InterestDto {
	private int no;
	private int baseaddress_no;
	private String user_id;
	private String sidoName;
	private String gugunName;
	private String dongName;
	private String dongCode;
	private String lat;
	private String lng;
	
	public InterestDto(String dongCode, String user_id) {
		this.dongCode = dongCode;
		this.user_id = user_id;
	}
	
	public InterestDto(String user_id, String sidoName, String gugunName, String dongName) {
		this.user_id = user_id;
		this.sidoName = sidoName;
		this.gugunName = gugunName;
		this.dongName = dongName;
	}
	
}

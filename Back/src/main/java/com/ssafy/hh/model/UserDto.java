package com.ssafy.hh.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto {
	private String id;
	private String pw;
	private String name;
	private String email;
	private String address;
	private String joinDate;
	private String isAgent;
	private int sms;
	private String phone;
	
	public UserDto(String id, String pw, String name, String email, String address, String isAgent, int sms, String phone) {
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.email = email;
		this.address = address;
		this.isAgent = isAgent;
		this.sms = sms;
		this.phone = phone;
	}
}

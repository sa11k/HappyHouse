package com.ssafy.hh.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StoreDto {
	private String storeName;
	private String jibunAddress;
	private String roadAddress;
	private String lng;
	private String lat;
}

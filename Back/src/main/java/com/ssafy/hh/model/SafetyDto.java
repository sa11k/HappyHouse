package com.ssafy.hh.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SafetyDto {
	String sidoCode;
	int bas_yy;
	String regi;
	int trffac;
	int fire;
	int crim;
	int natdsast;
	int comu_safe;
	int suicid;
	int infect;
}

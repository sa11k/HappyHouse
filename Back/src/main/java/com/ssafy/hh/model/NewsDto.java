package com.ssafy.hh.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NewsDto {
	private String title;
	private String link;
	private String origin;
	private String time;
}

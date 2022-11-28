package com.ssafy.hh.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BoardDto {
	private int no;
	private String title;
	private String content;
	private String user_id;
	private String user_name;
	private int hit;
	private String date;
	
	public BoardDto(String title, String content, String user_id, String user_name) {
		this.title = title;
		this.content = content;
		this.user_id = user_id;
		this.user_name = user_name;
	}

	public BoardDto(int no, String title, String content) {
		this.no = no;
		this.title = title;
		this.content = content;
	}
	
}

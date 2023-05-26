package com.asia.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardSearchDto {
	
	
	private String searchDateType;//현재시간과 등록일을 비교해서 데이터 조회
	private String searchBy; // 조회할때 어떤 유형으로 조회할지 선택 (name:글제목, createdBy:등록자 아이디)
	private String searchQuery=""; // 조회할 검색어 저장할 변수
	
	


}

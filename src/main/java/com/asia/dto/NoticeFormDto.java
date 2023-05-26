package com.asia.dto;

import javax.validation.constraints.NotBlank;

import org.modelmapper.ModelMapper;

import com.asia.entity.Notice;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeFormDto {
	
	
	private Long num;
	
	@NotBlank(message = "글제목은 필수 입력 값입니다.")
	private String name; // 글제목

	private String content; // 글내용
	
	private static ModelMapper modelMapper = new ModelMapper();
	//modelMappper 이용해서 엔티티 객체와 dto객체 간의 데이터를 복사해 복사한 객체를 반환해주는 메서드
	
	public Notice createNotice() {
		return modelMapper.map(this, Notice.class);
		//dto -> entity
	}
	
	public static NoticeFormDto of(Notice notice) {
		return modelMapper.map(notice, NoticeFormDto.class);
		//entity -> dto
		
	}

	
//	private LocalDateTime date; // 작성일
//	private String cnt; // 조회수

}

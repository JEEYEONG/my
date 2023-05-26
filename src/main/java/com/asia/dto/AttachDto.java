package com.asia.dto;

import org.modelmapper.ModelMapper;

import com.asia.entity.Attach;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AttachDto {

	private Long num; // 첨부파일번호

	private String name; // 파일이름

	private String oriname; // 파일원본이름

	private String url; // 파일url

	private String thumb; // 파일썸네일

	//private String repimgYn;// 대표이미지여부

	private static ModelMapper modelMapper = new ModelMapper();
	// modelMappper 이용해서 엔티티 객체와 dto객체 간의 데이터를 복사해 복사한 객체를 반환해주는 메서드

	public static AttachDto of(Attach attach) {
		return modelMapper.map(attach, AttachDto.class);
		// of(Attach attach) 엔티티를 DTO로 변환하는 작업을 위한 메서드
	}

	@QueryProjection
	public AttachDto(Long num, String name) {
		this.num = num;
		this.name = name;
	}

}

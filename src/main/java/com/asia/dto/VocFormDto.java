package com.asia.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;

import com.asia.entity.Voc;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class VocFormDto {

	private Long num;

	@NotBlank(message = "글제목은 필수 입력 값입니다.")
	private String name; // 글제목

	private String content; // 글내용

	@NotNull
	private int cnt; // 조회수

	private String createdBy;

	private LocalDateTime regTime;
	
	private Long originNo;

	private Long groupOrd;

	private Long groupLayer;

	private List<AttachDto> attachDtoList = new ArrayList<AttachDto>(); // 글 등록 후 수정할 때 상품 이미지 정보를 저장하는 리스트

	private List<Long> attachIds = new ArrayList<Long>(); // 글 이미지 아이디를 저장하는 리스트. 글 등록시에는 아직 글 이미지를 저장하지 않았기 때문에 아무 값도
															// 들어가 있지 않고 수정시에 이미지 아이디 담아둘 용도

	private static ModelMapper modelMapper = new ModelMapper();
	// modelMappper 이용해서 엔티티 객체와 dto객체 간의 데이터를 복사해 복사한 객체를 반환해주는 메서드

	public Voc createVoc() {
		return modelMapper.map(this, Voc.class);
		// dto -> entity
	} // VocFormDto 객체를 생성해서 메서드를 호출하여 매핑하고

	public static VocFormDto of(Voc voc) {
		return modelMapper.map(voc, VocFormDto.class);
		// entity -> dto
	} // static에 저장하는 메서드로 객체생성 없이 클래스, 메서드 호출해 사용

	// 작성일

}

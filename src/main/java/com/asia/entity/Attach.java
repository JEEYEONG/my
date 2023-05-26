package com.asia.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="attach")
@Getter
@Setter
@ToString
@SequenceGenerator(name = "USER_SEQ_GEN4", // 시퀀스 제너레이터 이름
sequenceName = "USER_SEQ4", // 시퀀스 이름
initialValue = 1, // 시작값
allocationSize = 1 // 메모리를 통해 할당할 범위 사이즈
)
public class Attach{
	
	@Id
	@Column(name="num")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USER_SEQ_GEN4")
	private Long num; //첨부파일번호
	
	@Column
	private String name; //파일이름
	
	@Column
	private String oriname; //파일원본이름
	
	@Column
	private String url; //파일url
	
	@Column
	private String thumb; //파일썸네일//여기로 옮겨
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="voc_num") //이름이겹치면안돼
	private Voc voc;
	
	//원본 이미지 파일명, 업데이트할 이미지 파일명, 이미지 경로를 파라미터로 입력받아 이미지정보를 업데이트하는 메서드
	public void updateImg(String oriname, String name, String url) {
		this.oriname = oriname;
		this.name = name;
		this.url = url;
	}
	

	

	
	

}
	
	
	
	
	
	

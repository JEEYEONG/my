package com.asia.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.asia.dto.VocFormDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "voc")
@Getter
@Setter
@ToString
@SequenceGenerator(name = "USER_SEQ_GEN3", // 시퀀스 제너레이터 이름
		sequenceName = "USER_SEQ3", // 시퀀스 이름
		initialValue = 1, // 시작값
		allocationSize = 1 // 메모리를 통해 할당할 범위 사이즈
)
public class Voc extends BaseEntity {

	@Id
	@Column(name = "voc_num")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ_GEN3")
	private Long num; // 글번호

	@Column(nullable = false)
	private String name; // 글제목

	@Column
	private String content; // 글내용

	@Column(columnDefinition = "number default 0", nullable = false)
	private int cnt;
	
	@Column
	private Long originNo;
	
	@Column
	private Long groupOrd;
	
	@Column
	private Long groupLayer;
	
	

	// 주인은 mappedBy속성 사용불가, 이걸 사용해 주인이 아닌걸 jpa에 알려
	@OneToMany(mappedBy = "voc", cascade = CascadeType.ALL)
	@ToString.Exclude
	private List<Attach> attach;
	


	public void updateVoc(VocFormDto vocFormDto) {
		this.name = vocFormDto.getName();
		this.content = vocFormDto.getContent();
		
	}

}

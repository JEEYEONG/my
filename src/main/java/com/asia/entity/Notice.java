package com.asia.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.asia.dto.NoticeFormDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="notice")
@Getter
@Setter
@ToString
@SequenceGenerator(name = "USER_SEQ_GEN2", // 시퀀스 제너레이터 이름
sequenceName = "USER_SEQ2", // 시퀀스 이름
initialValue = 1, // 시작값
allocationSize = 1 // 메모리를 통해 할당할 범위 사이즈
)
public class Notice extends BaseEntity {
	
	@Id
	@Column(name="ntc_num")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USER_SEQ_GEN2")
	private Long num; //글번호
	
	@Column(nullable = false)
	private String name; //글제목
	
	@Lob
	@Column
	private String content; //글내용
	
	public void updateNotice(NoticeFormDto noticeFormDto){
		//글 수정
		this.name = noticeFormDto.getName();
		this.content = noticeFormDto.getContent();
	}
	
//	@Column(nullable = false)
//	private LocalDateTime date; //작성일
//	
//	@Column(nullable = false)
//	private String cnt; //조회수
	
	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "mem_id")
//	private String num;//관리자번호 PK
	
	
	
	
	
	
	
//	//회원가입
//	public static Notice createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
//		Notice member = new Notice();
//		member.setId(memberFormDto.getId());
//		member.setName(memberFormDto.getName());
//		member.setEmail(memberFormDto.getEmail());
//		member.setTel(memberFormDto.getTel());
//		member.setBirth(memberFormDto.getBirth());
//		member.setAddr(memberFormDto.getAddr());
//		member.setAge(memberFormDto.getAge());
//		member.setAgree(memberFormDto.getAgree());
//		String password = passwordEncoder.encode(memberFormDto.getPassword());
//		member.setPassword(password);
//		member.setRole(Role.USER);
//		member.setStat(Stat.회원);
//		return member;
//	}
}

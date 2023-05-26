package com.asia.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.asia.constant.Role;
import com.asia.constant.Stat;
import com.asia.dto.MemberFormDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="member")
@Getter
@Setter
@ToString
@SequenceGenerator(name = "USER_SEQ_GEN1", // 시퀀스 제너레이터 이름
sequenceName = "USER_SEQ1", // 시퀀스 이름
initialValue = 1, // 시작값
allocationSize = 1 // 메모리를 통해 할당할 범위 사이즈
)
public class Member extends BaseEntity {
	
	@Id
	@Column(name="member_id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USER_SEQ_GEN1")
	private Long num;
	
	@Column(unique = true, nullable = false)
	private String id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(unique = true, nullable = false)
	private String email;
	
	@Column(unique = true, nullable = false)
	private String tel;
	
	private String birth;
	
	private String addr;
	
	@Column(nullable = false)
	private String password;
	
	@Enumerated(EnumType.STRING)
	private Stat stat;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	private Long age;
	
	private String agree;
	
	
	//회원가입
	public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
		Member member = new Member();
		member.setId(memberFormDto.getId());
		member.setName(memberFormDto.getName());
		member.setEmail(memberFormDto.getEmail());
		member.setTel(memberFormDto.getTel());
		member.setBirth(memberFormDto.getBirth());
		member.setAddr(memberFormDto.getAddr());
		member.setAge(memberFormDto.getAge());
		member.setAgree(memberFormDto.getAgree());
		String password = passwordEncoder.encode(memberFormDto.getPassword());
		member.setPassword(password);
		member.setRole(Role.USER);
		member.setStat(Stat.회원);
		return member;
	}
}

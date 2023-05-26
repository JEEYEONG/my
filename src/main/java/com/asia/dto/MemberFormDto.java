package com.asia.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberFormDto {

	@NotBlank(message = "이름은 필수 입력 값입니다.")
	private String name;
	
	@NotBlank(message = "아이디은 필수 입력 값입니다.")
	private String id;
	
	@NotEmpty(message = "이메일은 필수 입력 값입니다.")
	@Email(message = "이메일 형식으로 입력해주세요.")
	private String email;
	
	@NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
	@Length(min = 8, max = 16, message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요")
	private String password;
	
	@NotEmpty(message = "주소는 필수 입력 값입니다.")
	private String addr;
	
	@NotEmpty(message = "전화번호는 필수 입력 값입니다.")
	private String tel;
	
	@NotEmpty(message = "생년월일는 필수 입력 값입니다.")
	private String birth;
	
	private Long age;
	
	private String agree;
}

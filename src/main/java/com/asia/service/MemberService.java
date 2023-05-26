package com.asia.service;

import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.asia.entity.Member;
import com.asia.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

	private final MemberRepository memberRepository;

	// 회원가입
	public Member saveMember(Member member) {
		validateDuplicateMember(member);
		return memberRepository.save(member);
	}

	// 회원 중복검사
	private void validateDuplicateMember(Member member) {
		Member findMember = memberRepository.findById(member.getId());
		if (findMember != null) {
			throw new IllegalStateException("이미 가입된 아이디입니다."); // 이미 가입된 회원의 경우 예외를 발생시킨다.
		}
		findMember = null;
		findMember = memberRepository.findByEmail(member.getEmail());
		if (findMember != null) {
			throw new IllegalStateException("이미 사용된 이메일입니다.");
		}
		findMember = null;
		findMember = memberRepository.findByTel(member.getTel());
		if (findMember != null) {
			throw new IllegalStateException("이미 사용된 전화번호입니다.");
		}
	}

	// 로그인
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		System.out.println("check"+id);
		Member member = memberRepository.findById(id);
		if (member == null) {
			throw new UsernameNotFoundException(id);
		}

		return User.builder().username(member.getId())
				.password(member.getPassword())
				.roles(member.getRole().toString())
				.build();
	}

	public boolean checkIdDuplicate(String id) {
		return memberRepository.existsById(id);
	}
}

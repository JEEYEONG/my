package com.asia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asia.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	
	Member findById(String id);
	Member findByTel(String tel);
	Member findByEmail(String email);
	
	boolean existsById(String id);
	
}

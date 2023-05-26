package com.asia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asia.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long>{
	
	
}

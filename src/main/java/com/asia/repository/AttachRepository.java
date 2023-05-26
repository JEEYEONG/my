package com.asia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.asia.dto.AttachDto;
import com.asia.entity.Attach;

public interface AttachRepository extends JpaRepository<Attach, Long>{
	
	List<Attach> findByVocNumOrderByNumAsc(Long num);
	
	@Query("select new com.asia.dto.AttachDto(num, name) from Attach where voc_num = :num")
	List<AttachDto> getLists(Long num);

	Attach findByNum(Long num);
}

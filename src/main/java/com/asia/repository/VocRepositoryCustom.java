package com.asia.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.asia.dto.BoardSearchDto;
import com.asia.entity.Voc;

//사용자정의 인터페이스 작성
public interface VocRepositoryCustom {
	
	Page<Voc> getBoardPage(BoardSearchDto boardSearchDto, Pageable pageable);
	//조회 조건을 담고 있는 boardSearchDto 객체와 페이징 정보를 담고있는 pageble 객체를 파라미터로 받는 getAdminPage메서드 정의

	Page<Voc> getVocLists(Pageable pageable);


	
	
	
	
}

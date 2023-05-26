package com.asia.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asia.dto.NoticeFormDto;
import com.asia.entity.Notice;
import com.asia.repository.NoticeRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeService {
	
	private final NoticeRepository noticeRepository;
	
	
	//새글등록
	public Notice saveNotice(NoticeFormDto noticeFormDto) throws Exception{
		 Notice notice = noticeFormDto.createNotice();
		
		return noticeRepository.save(notice);
	}
	
	//새글등록2
//		public Long saveNotice(NoticeFormDto noticeFormDto) throws Exception{
//			 Notice notice = noticeFormDto.createNotice();
//			 noticeRepository.save(notice);
//			return null;
//		}
	
	//리스트 조회
	public List<Notice> noticeList(){
		return noticeRepository.findAll();
	}
	
	//게시글 불러오기
	public Notice noticeView(Long num) { //Notice entity 가져와야
		return noticeRepository.findById(num).get();
	}
	
	//삭제
	public void noticeDelete(Long num) {
		noticeRepository.deleteById(num);
	}
	
	
	//수정-등록된 상품 불러오는 메서드
	@Transactional(readOnly = true) //읽어오는 트랜잭션을 읽기전용으로 설정, 이럴 경우 JPA가 변경감지(더티체킹)를 수행하지 않아서 성능향상
	public NoticeFormDto getntcDtl(Long num) {
		Notice notice = noticeRepository.findById(num)
				.orElseThrow(EntityNotFoundException::new);
		NoticeFormDto noticeFormDto=NoticeFormDto.of(notice);
		return noticeFormDto;
	}
	
	public Long updateNotice(NoticeFormDto noticeFormDto) throws Exception{
		//글 수정
		Notice notice = noticeRepository.findById(noticeFormDto.getNum())
				.orElseThrow(EntityNotFoundException::new);
		notice.updateNotice(noticeFormDto); //수정화면으로 전달 받은 noticeFormDto를 통해 Notice엔티티 업데이트
		
		return notice.getNum();
	}
	
	
	
	
	
	
	
	
	
	

}

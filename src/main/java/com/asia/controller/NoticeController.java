package com.asia.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asia.dto.NoticeFormDto;
import com.asia.service.NoticeService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {
	
	private final NoticeService noticeService;
	
	@GetMapping("/new")
	public String noticeForm(Model model) {
		model.addAttribute("noticeFormDto", new NoticeFormDto());
		
		return "notice/noticeForm";
	}
	
	@PostMapping("/new")
	public String newNotice(@Valid NoticeFormDto noticeFormDto, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			return "notice/noticeForm";
		}
		try {
			noticeService.saveNotice(noticeFormDto);
		} catch (Exception e) {
			model.addAttribute("errorMessage","등록 중 에러발생");
			return "notice/noticeForm";
			
		}
		return "redirect:/";
	}
	
	//리스트불러오기
	@GetMapping("/list")
	public String listNotice(Model model) {
		model.addAttribute("list", noticeService.noticeList());
		return "notice/noticeList";
	}
	
	//상세보기
	@GetMapping("/view")
	public String viewNotice(Model model, Long num) {
		model.addAttribute("notice", noticeService.noticeView(num)); //내테이블 이름 "notice"
		return "notice/noticeView";
	}
	
	//삭제
	@GetMapping("/delete")
	public String deleteNotice(Long num) {
		noticeService.noticeDelete(num);
		return "redirect:/";
	}
	
	
	
//	해당 컨트롤러는 @PathVariable을 사용해 id라는 값을 매개변수로 넘겨 쿼리 스트링 형식이 아닌 특정 숫자 그자체로 조회가 가능하게 했다.
//	예를 들어 @PathVariable를 사용한 경우라면 localhost:8080/board/modify/1 이런식으로 uri가 나온다면
//	쿼리 스트링의 경우엔 localhost:8080/board/modify?id=1의 형식으로 나오게 된다.
	
	//수정
	@GetMapping("/update/{num}")
	public String modNotice(@PathVariable("num") Long num,Model model) {
		
		try {
			NoticeFormDto noticeFormDto = noticeService.getntcDtl(num); //조회한 데이터를 모델에 담아 뷰로 전달
			model.addAttribute("noticeFormDto", noticeFormDto);
		} catch (Exception e) { //에러시 글쓰기로 이동
			model.addAttribute("errorMessage", "존재하지 않는 글 입니다.");
			return "notice/noticeForm";
		}
		return "notice/noticeForm";
		
	}
	
	
//	쿼리 스트링 vs @PathVariable - 파라미터를 넘기는 두가지 방법
//	쿼리 스트링 = 특정 값을 사용한 필터링
//	@PathVariable = 특정 인덱스를 조회
	//수정시 작동
	@PostMapping("/update/{num}")
	public String updateNotice(@Valid NoticeFormDto noticeFormDto, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			return "notice/noticeForm";
		}
		try {
			noticeService.updateNotice(noticeFormDto);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "글 수정 중 에러 발생");
			return "notice/noticeForm";
		}
		
		return "redirect:/";
	}

	

	
	
	
	

}

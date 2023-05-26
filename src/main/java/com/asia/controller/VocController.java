package com.asia.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.asia.dto.BoardSearchDto;
import com.asia.dto.VocFormDto;
import com.asia.entity.Voc;
import com.asia.repository.VocRepository;
import com.asia.service.AttachService;
import com.asia.service.VocService;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Controller
@RequiredArgsConstructor
@RequestMapping("/voc")
@ToString
public class VocController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(VocController.class);
	private final VocService vocService;
	private final AttachService attachService;
	private final VocRepository vocRepository;

	
	// 새글등록
	@GetMapping("/new")
	public String vocForm(Model model) {
		model.addAttribute("vocFormDto", new VocFormDto());
		
		return "board/voc/vocForm";
	}

	//@valid @validated은 항상 값을 검증하려는 오브젝트 옆에 붙어야 해. 그리고 에러를 담아내는 bindingresult도 있어야 돼.
	// 새글
	@PostMapping("/new")
	public String newVoc(@Valid VocFormDto vocFormDto, BindingResult bindingResult, Model model, @RequestParam("attachFile") List<MultipartFile> attachFileList) {
		//LOGGER.info("newVoc 메서드가 호출되었습니다.");
		//LOGGER.info("vocFormDto의 내용은 : {}", attachFileList);
		if (bindingResult.hasErrors()) {
			return "board/voc/vocForm";
		}
		
		if(attachFileList.get(0).isEmpty() && vocFormDto.getNum() == null) {
			model.addAttribute("errorMessage", "첫번째 이미지는 필수 입력값 입니다.");
			return "board/voc/vocForm";
		}
		try {
			System.out.println(vocFormDto);
			vocService.saveVoc(vocFormDto, attachFileList);
			
			VocFormDto vocFormDto1 = vocService.getvocCtD(vocFormDto.getContent());
			//사진정보가 담겼어 vocFormDto1, 근데 같은 내용을 쓰면 오류나
			vocService.updateCnt(vocFormDto1.getNum());
			model.addAttribute("voc", vocFormDto1);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "등록 중 에러발생");
			return "board/voc/vocForm";
		}
		return "board/voc/vocDetail";
	}
	
	// 리스트불러오기 페이징넣어서
//		@GetMapping(value = {"/list", "/list/{page}"})
		@GetMapping("/list")
		public String listVoc(@PageableDefault(page = 0, size = 8, sort = "num", direction = Sort.Direction.DESC) Pageable pageable, Model model) {
			
			Page<Voc> list = vocService.getVocLists(pageable);
			model.addAttribute("list", list);
			//model.addAttribute("list", vocService.vocList()); 위에 page에서 이미 전부 담아와서 다시 조회해서 넣어줄필요없음 
			System.out.println(list);
			//페이징
			
			int nowPage = list.getPageable().getPageNumber() + 1;
			int startPage = Math.max(nowPage - 4, 1);
			int endPage = Math.min(nowPage + 9, list.getTotalPages());
			model.addAttribute("nowPage",nowPage);
			model.addAttribute("startPage",startPage);
			model.addAttribute("endPage",endPage);
			
			return "board/voc/vocList";
		}
	
	

	// 상세보기
	@GetMapping("/detail/{num}")
	public String detailVoc(Model model, @PathVariable("num") Long num) {
		System.out.println(num);
		VocFormDto vocFormDto = vocService.getvocDtl(num);
		vocService.updateCnt(num);
		model.addAttribute("voc",vocFormDto);
		
		//model.addAttribute("voc", vocService.vocDetail(num)); // 내테이블 이름 "voc"
		return "board/voc/vocDetail";
	}

	// 삭제
	@GetMapping("/delete/{num}")
	public String deleteVoc(@PathVariable Long num) throws Exception {
		System.out.println(num);
		attachService.attachDelete(num);
		vocService.vocDelete(num);
		return "redirect:/voc/list";
	}
	

	// 수정페이지 진입
	@GetMapping(value = "/update/{num}") //url 경로변수는 {}로 표현
		public String vocDtl(@PathVariable("num") Long num, Model model) {
		try {
			VocFormDto vocFormDto = vocService.getvocDtl(num); //조회한 데이터를 모델에 담아 뷰로 전달
			//System.out.println(vocFormDto);
			//LOGGER.info("vocFormDto의 내용은 : {}", vocFormDto);
			model.addAttribute("vocFormDto", vocFormDto);
		} catch (Exception e) { //엔티티가 존재하지 않을 경우 에러메세지를 담아 등록페이지로 이동
			model.addAttribute("errorMessage", "존재하지 않는 글 입니다.");
			model.addAttribute("vocFormDto", new VocFormDto());
			return "board/voc/vocForm";
		}
		return "board/voc/vocForm";	
	}
	
	//수정시 작동
	@PostMapping(value = "/update/{num}")
	public String vocUpdate(@Valid VocFormDto vocFormDto, BindingResult bindingResult, @RequestParam("attachFile") List<MultipartFile>attachFileList, Model model) {
		if(bindingResult.hasErrors()) {
			return "board/voc/vocForm";
		}
		if(attachFileList.get(0).isEmpty() && vocFormDto.getNum() == null) {
			model.addAttribute("errorMessage","첫번째 상품 이미지는 필수 입력 값");
			return "board/voc/vocForm";
		}
		try {
			System.out.println(vocFormDto);
			vocService.updateVoc(vocFormDto, attachFileList);
			
		} catch (Exception e) {
			model.addAttribute("errorMessage","상품 수정 중 에러 발생");
			return "board/voc/vocForm";
		}
		return "redirect:/voc/detail/{num}";
	}
	
	//페이지
	@GetMapping(value = {"/manage", "/manage/{page}"})
	public String vocManage(BoardSearchDto boardSearchDto ,@PathVariable ("page") Optional<Integer> page, Model model) {
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get():0,10);
		Page<Voc> items = vocService.getBoardPage(boardSearchDto, pageable);
		
		model.addAttribute("items",items);
		model.addAttribute("boardSearchDto", boardSearchDto);
		model.addAttribute("maxPage", 5);
		
		return "board/voc/vocMng";
	}
	//pageable pageRequest는 springData에서 제공하는 페이지네이션 정보를 담기 위한 인터페이스와 구현체, 페이지번호와 단일 페이지 개수를 담을 수 있다.
	
	
	
	//답글
	@GetMapping("/reply/{num}")
	public String replyForm(@PathVariable("num") Long num, Model model) {
		Voc parentVoc = vocRepository.findByNum(num);
		
		model.addAttribute("vocFormDto", new VocFormDto());
		model.addAttribute("num", num);
		model.addAttribute("originNo", parentVoc.getOriginNo());
		
		return "board/voc/vocReply";
	}
	
	@PostMapping("/reply/new")
	public String newReplyVoc(@Valid VocFormDto vocFormDto, BindingResult bindingResult, Model model,
			@RequestParam("attachFile") List<MultipartFile> attachFileList,
			@RequestParam("parentNo") Long parentNo,
			@RequestParam("originNo") Long num
			) {
		
		if(attachFileList.get(0).isEmpty() && vocFormDto.getNum() == null) {
			model.addAttribute("errorMessage","첫번쨰 이미지는 필수 입니다.");
			return "board/voc/vocReply";
		}
		try {
			vocService.saveReplyVoc(vocFormDto, attachFileList, parentNo);
			
			
			
		} catch (Exception e) {
			model.addAttribute("errorMessage","상품 등록 중 에러 발생");
			return "board/voc/vocReply";
		}
		return "redirect:/voc/list";
		
	}
	
	

	
	
	

}





















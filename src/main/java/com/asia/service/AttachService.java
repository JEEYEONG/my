package com.asia.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.asia.dto.AttachDto;
import com.asia.entity.Attach;
import com.asia.repository.AttachRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AttachService {

	@Value("${attachImgLocation}") // properties에 등록한 값 불러와
	private String attachImgLocation; // 변수 itemImgLocation에 넣어

	private final AttachRepository attachRepository;
	private final FileService fileService;

	public void saveAttach(Attach attach, MultipartFile attachFile) throws Exception {
		String oriname = attachFile.getOriginalFilename();
		String name = "";
		String url = "";

		// 파일업로드
		if (!StringUtils.isEmpty(oriname)) {
			name = fileService.uploadFile(attachImgLocation, oriname, attachFile.getBytes()); // 사용자가 이미지를 등록했다면 저장할 경로,
																							// 파일이름, 파일바이트수를 파라미터로 하는
																							// uploadFile 메서드 호출
			url = "/images/attach/" + name; // 저장한 이미지를 불러올 경로 설정 ?? C:/shop/images/item/
		}

		// 이미지 정보 저장
		attach.updateImg(oriname, name, url); // 업로드했던 이미지파일의 원래이름, 실제로컬에 저장된 이미지파일 이름, 업로드결과 로컬에 저장된 이미지파일 불러오는 경로 등의
												// 이미지 정보 저장
		attachRepository.save(attach);

	}

	// 이미지 수정한 경우 이미지 업데이트
	public void updateAttach(Long attachId, MultipartFile attachFile) throws Exception {
		if (!attachFile.isEmpty()) { // 이미지를 수정한 경우 이미지 업데이트
			Attach savedAttach = attachRepository.findById(attachId).orElseThrow(EntityNotFoundException::new); // 이미지아이디를이용하여기존저장했던상품이미지엔티티조회

			// 기존 이미지 파일 삭제
			if (!StringUtils.isEmpty(savedAttach.getName())) { // 기존에 등록된 글 이미지 파일이 있을 경우 해당 파일 삭제
				fileService.deleteFile(attachImgLocation + "/" + savedAttach.getName());
			}

			String oriname = attachFile.getOriginalFilename(); // 업데이트한 이미지파일 업로드
			String name = fileService.uploadFile(attachImgLocation, oriname, attachFile.getBytes());
			String url = "/images/attach/" + name;
			savedAttach.updateImg(oriname, name, url);
		}
	}
	// 변경된 이미지 정보 세팅. 중요한점은 등록때처럼 attachRepository.save()로직을 호출하지 않는다.
	// savedAttach엔티티는 현재 영속상태이므로 데이터를 변경하는 것만으로 변경감지 기능이 동작하여 트랜잭션이 끝날때 update쿼리가
	// 실행. 주의할점은 엔티티가 영속상태여야한다.

	public void attachDelete(Long num) throws Exception {

		List<AttachDto> attachLists = attachRepository.getLists(num);
		System.out.println(attachLists);

		for (int i = 0; i < attachLists.size(); i++) {

			String attach = attachLists.get(i).getName();

			fileService.deleteFile(attachImgLocation + "/" + attach);
		}

	}
}

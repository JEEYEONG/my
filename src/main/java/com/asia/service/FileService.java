package com.asia.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.extern.java.Log;

@Service
@Log
public class FileService {
	
	public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception{
		UUID uuid = UUID.randomUUID(); //Universally Unique Identifier는 서로 다른 객체들을 구별하기 위해서 이름을 부여할 때 사용(파일명 중복문제 해결)
		String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
		String savedFileName = uuid.toString() + extension; // UUID로 받은 값과 원래 파일 이름이 확장자를 조합해서 저장될 파일이름을 만들어
		String fileUploadFullUrl = uploadPath + "/" + savedFileName;
		FileOutputStream fos = new FileOutputStream(fileUploadFullUrl); // 바이트 단위의 출력을 내보내는 클래스로 생성자로 파일이 저장될 위치와 파일의 이름을 넘겨 파일에 쓸 파일 출력 스트림을 만들어
		fos.write(fileData); //fileData를 파일출력스트림에 입력
		return savedFileName;
	}
	
	public void deleteFile(String filePath) throws Exception{
		File deleteFile = new File(filePath);
		if(deleteFile.exists()) {
			deleteFile.delete();
			log.info("파일을 삭제했습니다.");
		}else {
			log.info("파일이 존재하지 않습니다.");
		}
	}
	
	
	
	
	
	
	
	
	/* */
	
	
	
	
	
	
	
	
	

}

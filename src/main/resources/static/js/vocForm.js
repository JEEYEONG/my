$(document).ready(function(){
			var errorMessage = [[${errorMessage}]];
			if(errorMessage != null){
				alert(errorMessage);
			}
			bindDomEvent();
		});
		
function bindDomEvent(){
	$(".custom-file-input").on("change", function(){
		var fileName = $(this).val().split("\\").pop(); //이미지파일명
		var fileExt = fileName.substring(fileName.lastIndexOf(".")+1); //확장자추출
		fileExt = fileExt.toLowerCase(); //확장자 소문자로 통일해서 비교하려고
		
		if(fileExt != "jpg" && fileExt != "jpeg" && fileExt != "gif" && fileExt != "png" && fileExt != "bmp"){
			alert("이미지 파일만 등록이 가능합니다."); //파일첨부 시 이미지 파일인지 검사한다.
			$(this).val("");
			return;
			} 
		$(this).siblings(".custom-file-label").html(fileName); //라벨 태그 안의 내용을 jquery의 .html()을 이용해 파일명 입력
	});
}
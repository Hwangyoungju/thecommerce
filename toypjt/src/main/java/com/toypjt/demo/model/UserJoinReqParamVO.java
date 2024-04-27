package com.toypjt.demo.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
@Schema(title = "사용자 정보")
public class UserJoinReqParamVO {
	@Schema(title = "회원ID", required = true, example = "회원ID은 특수문자를 제외한 5~10")
	@NotBlank(message="회원ID글 입력하세요.")
	@Pattern(regexp = "^[a-z0-9-_]{5,10}$", message = "회원ID은 특수문자를 제외한 5~10자리여야 합니다.")
	private String userId;
	
	@Schema(title = "비밀번호", required = true, example = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자")
	@NotBlank(message="비밀번호글 입력하세요.")
	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,15}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
	private String userPw;
	
	@Schema(title = "닉네임", required = true, example = "닉네임은 특수문자를 제외한 2~10")
	@NotBlank(message="닉네임글 입력하세요.")
	@Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "닉네임은 특수문자를 제외한 2~10자리여야 합니다.")
	private String nickNm;
	
	@Schema(title = "이름", required = true, example = "이름은 한글 2~5")
	@NotBlank(message="이름글 입력하세요.")
	@Pattern(regexp = "^[ㄱ-ㅎ가-힣]{2,5}$", message = "이름은 한글 2~5자리여야 합니다.")
	private String userNm;
	
	@Schema(title = "전화번호", required = true, example = "전화번호는 10 ~ 11 자리의 숫자만")
	@NotBlank(message="전화번호글 입력하세요.")
	@Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", message = "전화번호는 10 ~ 11 자리의 숫자만 입력 가능합니다.")
	private String telNum;
	
	@Schema(title = "이메일", required = true, example = "aaa@ac.com")
	@NotBlank(message="이메일글 입력하세요.")
	@Email(message="이메일 형식이 맞지 않습니다.")
	private String email;
}

package com.toypjt.demo.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.toypjt.demo.dao.User;
import com.toypjt.demo.model.UserJoinReqParamVO;
import com.toypjt.demo.model.UserListDaoParamVO;
import com.toypjt.demo.model.UserUptReqParamVO;
import com.toypjt.demo.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "01.사용자", description = "사용자 관련 API")
public class UserController {

	private static final Logger logger = LogManager.getLogger("User");

	@Autowired
	private UserService uSvc;

	@PostMapping("/api/user/join")
	@Operation(summary = "사용자 등록", description = "사용자를 등록한다.", tags = { "01.사용자" })
	@ApiResponse(responseCode = "201", description = "OK")
	@ApiResponse(responseCode = "500", description = "Validatior Error")
	@ApiResponse(responseCode = "9900", description = "UnDifine Error")
	public HashMap<String, Object> userJoin(@Valid @RequestBody UserJoinReqParamVO param, BindingResult vaRtn,
			HttpServletRequest request) throws Exception {

		HashMap<String, Object> map = new HashMap<>();

		String rtnMsg = "FAL";
		String rtnCode = "9900";
		String errMsg = "NONE";

		try {
			if (vaRtn.hasErrors()) {
				FieldError fieldError = vaRtn.getFieldError();
				rtnMsg = "FAL";
				rtnCode = "500";
				errMsg = fieldError.getDefaultMessage();
				logger.error("ERROR Validatior Error - errMsg : {}", errMsg);
				throw new UserDeFineException("Validatior Error");
			}

			User user = new User();
			user.setEmail(param.getEmail());
			user.setNickNm(param.getNickNm());
			user.setUserId(param.getUserId());
			user.setUserNm(param.getUserNm());
			user.setUserPw(param.getUserPw());
			user.setTelNum(param.getTelNum());

			String msg = uSvc.createUserService(user);

			if ("SUC".equals(msg)) {
				rtnMsg = "SUC";
				rtnCode = "201";
			} else {
				rtnMsg = "SUC";
				rtnCode = "501";
				errMsg = msg;
			}

		} catch (UserDeFineException e) {
			logger.error("ERROR {} / userid : T_{} / errMsg : {}", request.getRequestURI(), param.getUserId(), errMsg);
		} catch (Exception e) {
			logger.error("ERROR {} : ", request.getRequestURI(), e);
			rtnMsg = "FAL";
			rtnCode = "9900";
		}

		map.put("result", rtnMsg);
		map.put("resultCode", rtnCode);
		map.put("errMsg", errMsg);

		logger.info("reqSeq : {} / userid : T_{} / errMsg : {}", request.getRequestURI(), param.getUserId(), errMsg);

		return map;
	}

	@GetMapping("/api/user/list")
	@Operation(summary = "사용자 목록 조회", description = "사용자 목록을 조회한다.")
	@ApiResponse(responseCode = "200", description = "OK")
	@ApiResponse(responseCode = "500", description = "Validatior Error")
	@ApiResponse(responseCode = "9900", description = "UnDifine Error")
	public HashMap<String, Object> userList(
			@Parameter(description  = "페이지 번호", required = true, example = "1~100") @RequestParam("page") int page,
			@Parameter(description  = "한 페이지에 표시될 수 있는 최대 회원 수", required = true, example = "2~30") @RequestParam("pageSize") int pageSize,
			@Parameter(description  = "정렬 항목", required = true, example = "1:가입일 순 / 2:이름순") @RequestParam("sort") int sort,
			HttpServletRequest request) throws Exception {

		HashMap<String, Object> map = new HashMap<>();

		String rtnMsg = "FAL";
		String rtnCode = "9900";
		String errMsg = "NONE";

		try {

			if (page < 0 || page > 100) {
				rtnMsg = "FAL";
				rtnCode = "500";
				errMsg = "페이지 번호는 1~100 입니다.";
				logger.error("ERROR Validatior Error - errMsg : {}", errMsg);
				throw new UserDeFineException("Validatior Error");
			}

			if (pageSize < 2 || pageSize > 30) {
				rtnMsg = "FAL";
				rtnCode = "500";
				errMsg = "페이지 크기는 2~30 입니다.";
				logger.error("ERROR Validatior Error - errMsg : {}", errMsg);
				throw new UserDeFineException("Validatior Error");
			}

			if (sort < 1 || sort > 2) {
				rtnMsg = "FAL";
				rtnCode = "500";
				errMsg = "정렬 방식은 1 : 가입일 순, 2: 이름순 입니다.";
				logger.error("ERROR Validatior Error - errMsg : {}", errMsg);
				throw new UserDeFineException("Validatior Error");
			}

			UserListDaoParamVO daoParam = new UserListDaoParamVO();
			daoParam.setPage(page);
			daoParam.setPageSize(pageSize);
			if (sort == 1) {
				daoParam.setSortColumn("createdDt");
			} else {
				daoParam.setSortColumn("userNm");
			}

			Page<User> userList = uSvc.listUserService(daoParam);

			// 페이지 정보
			logger.info("userList.getContent() = {}", userList.getContent()); // 요청 페이지에 해당하는 글
			logger.info("userList.getTotalElements() = {}", userList.getTotalElements()); // 전체 글갯수
			logger.info("userList.getNumber() = {}", userList.getNumber()); // DB로 요청한 페이지 번호
			logger.info("userList.getTotalPages() = {}", userList.getTotalPages()); // 전체 페이지 갯수
			logger.info("userList.getSize() = {}", userList.getSize()); // 한 페이지에 보여지는 글 갯수
			logger.info("userList.hasPrevious() = {}", userList.hasPrevious()); // 이전 페이지 존재 여부
			logger.info("userList.isFirst() = {}", userList.isFirst()); // 첫 페이지 여부
			logger.info("userList.isLast() = {}", userList.isLast()); // 마지막 페이지 여부

			map.put("data", userList.getContent());
			map.put("totalRowCnt", userList.getTotalElements());
			map.put("totalPage", userList.getTotalPages());
			map.put("isFirst", userList.isFirst());
			map.put("isFirst", userList.isLast());
			map.put("page", userList.getNumber());
			map.put("pageSize", userList.getSize());

			rtnMsg = "SUC";
			rtnCode = "201";

		} catch (UserDeFineException e) {
			logger.error("ERROR {} / errMsg : {}", request.getRequestURI(), errMsg);
		} catch (Exception e) {
			logger.error("ERROR {} : ", request.getRequestURI(), e);
			rtnMsg = "FAL";
			rtnCode = "9900";
		}

		map.put("result", rtnMsg);
		map.put("resultCode", rtnCode);
		map.put("errMsg", errMsg);

		logger.info("reqSeq : {} / errMsg : {}", request.getRequestURI(), errMsg);

		return map;
	}

	@PutMapping("/api/user/{userid}")
	@Operation(summary = "사용자 변경", description = "사용자를 변경한다.")
	@ApiResponse(responseCode = "200", description = "OK")
	@ApiResponse(responseCode = "500", description = "Validatior Error", content = @Content)
	@ApiResponse(responseCode = "9900", description = "UnDifine Error", content = @Content)
	public HashMap<String, Object> updateUserinfo(@PathVariable("userid") String id,
			@Valid @RequestBody UserUptReqParamVO uptUser, BindingResult vaRtn, HttpServletRequest request) {

		HashMap<String, Object> map = new HashMap<>();

		String rtnMsg = "FAL";
		String rtnCode = "9900";
		String errMsg = "NONE";

		try {

			if (vaRtn.hasErrors()) {
				FieldError fieldError = vaRtn.getFieldError();
				rtnMsg = "FAL";
				rtnCode = "500";
				errMsg = fieldError.getDefaultMessage();
				logger.error("ERROR Validatior Error - errMsg : {}", errMsg);
				throw new UserDeFineException("Validatior Error");
			}

			User rtnUser = uSvc.updateUserService(id, uptUser);

			if (rtnUser == null) {
				rtnMsg = "FAL";
				rtnCode = "500";
				throw new UserDeFineException("Validatior Error");
			}

			rtnMsg = "SUC";
			rtnCode = "200";

			map.put("userInfo", rtnUser);

		} catch (UserDeFineException e) {
			logger.error("ERROR {} / errMsg : {}", request.getRequestURI(), errMsg);
		} catch (Exception e) {
			logger.error("ERROR {} : ", request.getRequestURI(), e);
			rtnMsg = "FAL";
			rtnCode = "9900";
		}

		map.put("result", rtnMsg);
		map.put("resultCode", rtnCode);
		map.put("errMsg", errMsg);

		logger.info("reqSeq : {} / errMsg : {}", request.getRequestURI(), errMsg);

		return map;
	}

}

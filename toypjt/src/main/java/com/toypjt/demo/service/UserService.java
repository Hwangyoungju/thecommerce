package com.toypjt.demo.service;

import java.util.Optional;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.toypjt.demo.controller.UserDeFineException;
import com.toypjt.demo.dao.User;
import com.toypjt.demo.dao.UserRepository;
import com.toypjt.demo.model.UserListDaoParamVO;
import com.toypjt.demo.model.UserUptReqParamVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private static final Logger logger = LogManager.getLogger("User-Service");

	private final UserRepository userRepository;

	@Transactional
	public String createUserService(User user) {

		String rtnMsg = "FAL";

		try {
			Optional<User> rtnUser = userRepository.findById(user.getUserId());

			if (rtnUser.isPresent()) {
				rtnMsg = "이미 존재하는 사용자 아이디 입니다.";
				logger.error("createUserService UserId is Dup. ID : {} ", user.getUserId());
				throw new UserDeFineException("UserDeFine UserId is Dup");
			}

			userRepository.save(user);

			rtnMsg = "SUC";

		} catch (UserDeFineException e) {
			logger.error("createUserService Error. Error : {} ", e.getMessage());
		} catch (Exception e) {
			logger.error("createUserService Error ", e);
		}
		
		return rtnMsg;
	}

	public Page<User> listUserService(UserListDaoParamVO reqUser) {
		int page = reqUser.getPage() - 1; 
		int pageLimit = reqUser.getPageSize();

		Page<User> userlist = userRepository
				.findAll(PageRequest.of(page, pageLimit, Sort.by(Direction.ASC, reqUser.getSortColumn())));
		
		return userlist;
	}
	
    @Transactional
    public User updateUserService(String id, UserUptReqParamVO uptUser) {
    	
    	User rtnUser = null;
    			
		try {
			rtnUser = userRepository.findById(id)
	                .orElseThrow(() -> new IllegalArgumentException("Do Not Have User_Id = " + id));

			if(uptUser.getEmail() != null) {
				rtnUser.setEmail(uptUser.getEmail());
			}
			
			if(uptUser.getNickNm() != null) {
				rtnUser.setNickNm(uptUser.getNickNm());
			}
			
			if(uptUser.getTelNum() != null) {
				rtnUser.setTelNum(uptUser.getTelNum());
			}
			
			if(uptUser.getUserNm() != null) {
				rtnUser.setUserNm(uptUser.getUserNm());;
			}
			
			if(uptUser.getUserPw() != null) {
				rtnUser.setUserPw(uptUser.getUserPw());
			}
		} catch (Exception e) {
			logger.error("updateUserService Error ", e);
		}
		
        return rtnUser;
    }
}

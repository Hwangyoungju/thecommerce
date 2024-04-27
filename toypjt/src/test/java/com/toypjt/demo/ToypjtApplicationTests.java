package com.toypjt.demo;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.toypjt.demo.dao.User;
import com.toypjt.demo.dao.UserRepository;

import lombok.extern.slf4j.Slf4j;

@DataJpaTest
@Slf4j
class ToypjtApplicationTests {
	
	@Autowired
	UserRepository userRepository;
		
	@BeforeEach
	void insertTestData() {
		User user = new User();
		user.setUserId("aaaaa");
		user.setUserPw("11111111");
		user.setNickNm("김은나닉");
		user.setUserNm("김은나");
		user.setTelNum("010-1234-1111");
		user.setEmail("aaaaa@dumy.co.kr");
		userRepository.save(user);
		
		user = new User();
		user.setUserId("bbbb");
		user.setUserPw("22222222");
		user.setNickNm("박가을닉");
		user.setUserNm("박가을");
		user.setTelNum("010-2222-2222");
		user.setEmail("bbbb@dumy.co.kr");
		userRepository.save(user);
		
		user = new User();
		user.setUserId("ccccc");
		user.setUserPw("33333333");
		user.setNickNm("이지현닉");
		user.setUserNm("이지현");
		user.setTelNum("010-3333-3333");
		user.setEmail("ccccc@dumy.co.kr");
		userRepository.save(user);
		
		user = new User();
		user.setUserId("dddd");
		user.setUserPw("44444444");
		user.setNickNm("송가을닉");
		user.setUserNm("송가을");
		user.setTelNum("010-4444-4444");
		user.setEmail("dddd@dumy.co.kr");
		userRepository.save(user);
		
		user = new User();
		user.setUserId("eeeee");
		user.setUserPw("55555555");
		user.setNickNm("이소현닉");
		user.setUserNm("이소현");
		user.setTelNum("010-5555-5555");
		user.setEmail("eeeee@dumy.co.kr");
		userRepository.save(user);
	}

	@Test
	void findAllTest() { // 저장된 데이터 모두를 Spring JPA에 미리 구현된 findAll 명령을 통해 불러온다
		List<User> userList = userRepository.findAll();
		for(User u : userList) log.info("[FindAll]: {} | {}", u.getUserId(), u.getUserNm());
	}

	@Test
	void findAllCrDt() { // 저장된 데이터 모두를 Spring JPA에 미리 구현된 findAll 명령을 통해 불러온다
		Page<User> userlist = userRepository.findAll(PageRequest.of(0, 2, Sort.by(Direction.DESC, "createdDt")));
		for(User u : userlist) log.info("[findAllCrDt]: {} | {}", u.getUserId(), u.getUserNm());
	}
	
}

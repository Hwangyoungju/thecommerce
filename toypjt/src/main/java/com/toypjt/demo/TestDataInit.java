package com.toypjt.demo;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import com.toypjt.demo.dao.User;
import com.toypjt.demo.dao.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class TestDataInit {
	private final UserRepository userRepository;

	@EventListener(ApplicationReadyEvent.class)
	public void initData() {
		log.info("test data init");
		
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
}

package com.mapia.api;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mapia.dao.UserRepository;
import com.mapia.domain.User;
import com.mapia.result.LoginResult;
import com.mapia.result.SignUpResult;
import com.mapia.utils.HttpSessionUtils;

@RestController
@RequestMapping("/api")
public class ApiUserController {
	private static final Logger logger = LoggerFactory.getLogger(ApiUserController.class);
	
	@Autowired
	UserRepository userRepository;
	
	@PostMapping("/login")
	public LoginResult login(@RequestBody User loginUser, HttpSession session) {
		logger.info("loginUser email : {}", loginUser.getEmail());
		logger.info("loginUser password : {}", loginUser.getPassword());
		
		User user = userRepository.findUserByEmail(loginUser.getEmail());
		
		if (user == null) {
			return LoginResult.emailNotFound("가입되지 않은 이메일입니다.");
		}
		
		if (!user.matchPassword(loginUser)) {
			return LoginResult.invalidPassword("잘못된 비밀번호입니다.");
		}
		
		session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);
		return LoginResult.ok(user);
	}
	
	@PostMapping("/signup")
	public SignUpResult signup(@RequestBody User newUser) {
		logger.info("newUser : {}", newUser);
		
		if (userRepository.findUserByEmail(newUser.getEmail()) != null) {
			return SignUpResult.emailExist("이미 가입된 이메일입니다.");
		}
		
		if (userRepository.findUserByNickname(newUser.getNickname()) != null) {
			return SignUpResult.nicknameExist("이미 사용중인 닉네임입니다.");
		}
		
		userRepository.userInsert(newUser);
		return SignUpResult.ok();
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
		return "logged out";
	}
}
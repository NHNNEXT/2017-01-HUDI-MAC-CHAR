package com.mapia;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mapia.dao.UserRepository;
import com.mapia.model.User;

public class UserRepositoryTest {
	@Autowired
	private UserRepository userRepository;

	@Test
	public void test() {
		User user = userRepository.findUserByEmail("asdf@asdf");
		
		assertEquals("1", user.getId() + "");
		assertEquals("asdf@asdf", user.getEmail());
		assertEquals("1234", user.getPassword());
		assertEquals("asdf", user.getNickname());
	}

}

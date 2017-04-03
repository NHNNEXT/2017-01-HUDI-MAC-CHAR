package com.mapia.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapia.domain.User;

@Repository
public class UserRepository {
	private static final Logger log = LoggerFactory.getLogger(UserRepository.class);
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public UserRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public User findUserByEmail(String email) {
		String query = "SELECT * FROM User WHERE email = ?";
		User resultUser;
		try {
			resultUser = jdbcTemplate.queryForObject(query, new Object[]{email}, new UserRowMapper());
		} catch (EmptyResultDataAccessException e) {
			log.error(e.getMessage());
			return null;
		}
		return resultUser;
	}
	
	public int userInsert(User user) {
		String query = "INSERT INTO User(email, password, nickname) VALUES (?,?,?)";
		return jdbcTemplate.update(query, user.getEmail(), user.getPassword(), user.getNickname());
	}
	
	public User findUserByNickname(String nickname) {
		String query = "SELECT * FROM User WHERE nickname = ?";
		User resultUser;
		try {
			resultUser = jdbcTemplate.queryForObject(query, new Object[]{nickname}, new UserRowMapper());
		} catch (EmptyResultDataAccessException e) {
			log.error(e.getMessage());
			return null;
		}
		return resultUser;
	}
}

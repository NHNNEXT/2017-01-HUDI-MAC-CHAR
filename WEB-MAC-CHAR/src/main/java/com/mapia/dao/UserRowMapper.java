package com.mapia.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mapia.domain.User;

//TODO @대훈 UserRepository에서 한번 사용하고 버릴 코드는 UserRepository에 static inner class로 구현하는 것이 더 좋은 접근방법임.
// 자바 8의 람다 활용해 UserRowMapper를 생성하지 않고 구현해볼 것을 추천함.
public final class UserRowMapper implements RowMapper<User> {
	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User(rs);
		return user;
	}
}

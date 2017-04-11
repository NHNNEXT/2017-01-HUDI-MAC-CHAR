package mapia.domain;

public class User {
	private long id;
	private String email;
	private String password;
	private String nickname;
	
	public User(long id, String nickname) {
		this.id = id;
		this.nickname = nickname;
	}

	public User(long id, String email, String password, String nickname) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.nickname = nickname;
	}

	public long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getNickname() {
		return nickname;
	}
}

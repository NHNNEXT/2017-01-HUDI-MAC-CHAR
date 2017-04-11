package mapia.domain;

public class Player {
	long id;
	private String nickname;
	private Status status;

	public Player(long id, String nickname) {
		this.id = id;
		this.nickname = nickname;
		this.status = Status.LOBBY;
	}
	
	public boolean matchId(long id) {
		return this.id == id;
	}
	
	public long getId() {
		return id;
	}
	
	public void enterRoom() {
		this.status = status.enterRoom();
	}
	
	public void ready() {
		this.status = status.ready();
	}
	
	public void startGame() {
		this.status = status.startGame();
	}
	
	private enum Status {
        LOBBY, READY, NOT_READY, IN_GAME;

		boolean isLobby() {
        	return this == Status.LOBBY;
		}
		
		boolean isNotReady() {
        	return this == Status.NOT_READY;
		}
		
		boolean isReady() {
        	return this == Status.READY;
		}
		
		Status enterRoom() {
			if (!isLobby()) {
				throw new IllegalStateException("게임에 참여중인 관계로 방에 입장할 수 없습니다.");
			}
			return Status.NOT_READY;
		}
		
		Status ready() {
			if (!isNotReady()) {
				throw new IllegalStateException("게임에 참여 상태가 아니라 Ready 상태로 변경할 수 없습니다.");
			}
			return Status.READY;
		}
		
		Status startGame() {
			if (!isReady()) {
				throw new IllegalStateException("준비 상태가 아니라 게임을 시작할 수 없습니다.");
			}
			return Status.IN_GAME;
		}
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((nickname == null) ? 0 : nickname.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (id != other.id)
			return false;
		if (nickname == null) {
			if (other.nickname != null)
				return false;
		} else if (!nickname.equals(other.nickname))
			return false;
		return true;
	}
}

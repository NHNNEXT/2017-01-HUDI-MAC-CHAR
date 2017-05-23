package com.mapia.game;

/**
 * Created by Jbee on 2017. 5. 22..
 */
public class GameResult {
    private static final String MAFIA_WIN_MESSAGE = "마피아가 승리하였습니다.";
    private static final String CITIZEN_WIN_MESSAGE = "시민이 승리하였습니다.";

    private boolean isFinished;
    private String msg;

    public GameResult() {
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public GameResult(GameResultType type) {
        this.isFinished = true;
        switch (type) {
            case MAFIA_WIN:
                this.msg = MAFIA_WIN_MESSAGE;
            case CITIZEN_WIN:
                this.msg = CITIZEN_WIN_MESSAGE;
        }
    }

    public GameResult(String killedUser) {
        this.isFinished = false;
        this.msg = killedUser;
    }

    public static GameResult VotingStatus() {
        return new GameResult("투표가 진행중입니다 :)");
    }

    @Override
    public String toString() {
        return String.format("[isFinished:%s] msg: %s", isFinished, msg);
    }
}

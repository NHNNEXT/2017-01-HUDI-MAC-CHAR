package com.zimincom.mafiaonline.item;

/**
 * Created by Zimincom on 2017. 5. 24..
 */

public class GameResult {

    private static final String MAFIA_WIN_MESSAGE = "마피아가 승리하였습니다.";
    private static final String CITIZEN_WIN_MESSAGE = "시민이 승리하였습니다.";

    private String msg;
    private boolean isFinished;
    private boolean completeVote;

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "GameResult{" +
                "msg='" + msg + '\'' +
                ", isFinished=" + isFinished +
                ", completeVote=" + completeVote +
                '}';
    }
}

package com.zimincom.mafiaonline;

/**
 * Created by Zimincom on 2017. 5. 21..
 */

public class GameManager {

    GameState gameState;
    int gameTime = 0;

    enum GameState {
        WAITING, DAY, NIGHT, VOTE
    }

    public void changeState(GameState gameState) {
        this.gameState = gameState;
    }

    public void startGame() {


    }

}

package com.zimincom.mafiaonline.item;

/**
 * Created by Zimincom on 2017. 5. 23..
 */

public class GameConfig {

    int gameTime;
    GameState gameState;

    public GameConfig(int gameTime, GameState gameState) {
        this.gameTime = gameTime;
        this.gameState = gameState;
    }

    public enum GameState {
        WAITING, DAY, VOTE, NIGHT
    }

    public int getGameTime() {
        return gameTime;
    }
}

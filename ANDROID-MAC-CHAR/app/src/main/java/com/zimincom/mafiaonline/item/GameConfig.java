package com.zimincom.mafiaonline.item;

/**
 * Created by Zimincom on 2017. 5. 23..
 */

public class GameConfig {

    int gameTime;
    GameState gameState;
    String gameMessage;
    
    public GameConfig(int gameTime, GameState gameState, String gameMessage) {
        this.gameTime = gameTime;
        this.gameState = gameState;
        this.gameMessage = gameMessage;
    }

    public String getGameMessage() {
        return gameMessage;
    }

    public enum GameState {
        WAITING, DAY, VOTE, NIGHT
    }

    public int getGameTime() {
        return gameTime;
    }

    public GameState getGameState() {
        return gameState;
    }
}

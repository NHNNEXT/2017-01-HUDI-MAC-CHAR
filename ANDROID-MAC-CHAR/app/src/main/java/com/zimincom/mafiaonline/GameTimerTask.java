package com.zimincom.mafiaonline;

import android.os.Handler;
import android.os.Message;

import com.orhanobut.logger.Logger;

import java.util.TimerTask;

/**
 * Created by Zimincom on 2017. 5. 23..
 */

public class GameTimerTask extends TimerTask{

    public static final int PHASE_CHANGE = 11;
    int gameTime;
    Handler handler;

    public GameTimerTask(int gameTime, Handler handler){
        this.gameTime = gameTime;
        this.handler = handler;
    }

    @Override
    public void run() {
        try {
            Message message = handler.obtainMessage();
            message.arg1 = gameTime;
            handler.sendMessage(message);
            if (gameTime == 0) {
                this.cancel();
                //send handler to change game phase
                handler.sendEmptyMessage(PHASE_CHANGE);
                Logger.d("task canceld");
            }
            gameTime--;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

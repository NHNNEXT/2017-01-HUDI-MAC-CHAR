package com.zimincom.mafiaonline.tasks;

import android.os.Handler;

import com.zimincom.mafiaonline.item.ResponseItem;

import java.util.TimerTask;

import retrofit2.Call;

/**
 * Created by Zimincom on 2017. 6. 2..
 */

public class RoomUpdateTask extends TimerTask {

    public final static int ROOM_LIST_UPDATE = 202;
    Handler handler;
    Call<ResponseItem> call;

    public RoomUpdateTask(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {

        handler.sendEmptyMessage(ROOM_LIST_UPDATE);

    }

}

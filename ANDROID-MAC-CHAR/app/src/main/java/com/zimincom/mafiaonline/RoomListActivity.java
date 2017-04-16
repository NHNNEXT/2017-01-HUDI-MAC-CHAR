package com.zimincom.mafiaonline;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.zimincom.mafiaonline.item.ResponseItem;
import com.zimincom.mafiaonline.item.Room;
import com.zimincom.mafiaonline.remote.MafiaRemoteService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.zimincom.mafiaonline.remote.MafiaRemoteService.retrofit;

public class RoomListActivity extends Activity implements View.OnClickListener{


    Context context = this;
    Button roomCreate;
    ArrayList<Room> rooms = new ArrayList<>();
    RecyclerView roomListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);

        roomCreate = (Button)findViewById(R.id.create_room);
        roomCreate.setOnClickListener(this);


        roomListView = (RecyclerView) findViewById(R.id.room_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        roomListView.setLayoutManager(layoutManager);
        roomListView.setItemAnimator(new DefaultItemAnimator());
        RoomAdapter roomAdapter = new RoomAdapter(context,rooms,R.layout.item_room);
        roomListView.setAdapter(roomAdapter);

        getRoomList();

    }

    public void getRoomList(){

        MafiaRemoteService mafiaRemoteService = retrofit.create(MafiaRemoteService.class);
        Call<ResponseItem> call = mafiaRemoteService.getRoomList();

        call.enqueue(new Callback<ResponseItem>() {
            @Override
            public void onResponse(Call<ResponseItem> call, Response<ResponseItem> response) {
                if (response.isSuccessful()) {
                    Logger.d(response.body());
                    rooms = response.body().getRooms();

                    Logger.d(rooms.get(0).toString());


                    roomListView.setAdapter(new RoomAdapter(context,rooms,R.layout.item_room));


                }

            }

            @Override
            public void onFailure(Call<ResponseItem> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getApplicationContext(),"서버 점검중입니다",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getApplicationContext(),GameRoomActivity.class);

        startActivity(intent);
    }
}

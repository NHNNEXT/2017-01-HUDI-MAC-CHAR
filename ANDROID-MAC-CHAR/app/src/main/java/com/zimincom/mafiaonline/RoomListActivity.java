package com.zimincom.mafiaonline;

import android.app.Activity;
import android.content.Context;
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
import com.zimincom.mafiaonline.item.User;
import com.zimincom.mafiaonline.remote.MafiaRemoteService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.zimincom.mafiaonline.remote.MafiaRemoteService.retrofit;

public class RoomListActivity extends Activity implements View.OnClickListener{


    Context context = this;
    Button roomCreate;
    Button logout;
    ArrayList<Room> rooms = new ArrayList<>();
    RecyclerView roomListView;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);

        user = (User)getIntent().getSerializableExtra("user");
        Logger.d(user.getNickName());

        roomCreate = (Button)findViewById(R.id.create_room);
        logout = (Button) findViewById(R.id.logout);

        roomCreate.setOnClickListener(this);
        logout.setOnClickListener(this);

        roomListView = (RecyclerView) findViewById(R.id.room_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        roomListView.setLayoutManager(layoutManager);
        roomListView.setItemAnimator(new DefaultItemAnimator());
        RoomAdapter roomAdapter = new RoomAdapter(context,rooms,user,R.layout.item_room);
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
                    rooms = response.body().getRooms();
                    roomListView.setAdapter(new RoomAdapter(context,rooms,user,R.layout.item_room));
                }

            }

            @Override
            public void onFailure(Call<ResponseItem> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getApplicationContext(),"서버 점검중입니다",Toast.LENGTH_LONG).show();
            }
        });
    }

    void logoutUser(){
        MafiaRemoteService mafiaRemoteService = retrofit.create(MafiaRemoteService.class);
        Call<String> call = mafiaRemoteService.getLogout();

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                    Toast.makeText(getBaseContext(),"로그아웃 성공", Toast.LENGTH_LONG).show();
                    finish();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getBaseContext(),"서버오류", Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.create_room) {
        } else if (view.getId() == R.id.logout) {
            logoutUser();
        }


    }
}

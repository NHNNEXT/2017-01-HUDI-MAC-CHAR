package com.zimincom.mafiaonline;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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


    Button roomCreate;
    ArrayList<Room> rooms = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);

        roomCreate = (Button)findViewById(R.id.create_room);
        roomCreate.setOnClickListener(this);

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

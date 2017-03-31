package com.zimincom.mafiaonline;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RoomListActivity extends Activity implements View.OnClickListener{


    Button roomCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);

        roomCreate = (Button)findViewById(R.id.create_room);
        roomCreate.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getApplicationContext(),GameRoomActivity.class);

        startActivity(intent);
    }
}

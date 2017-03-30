package com.zimincom.mafiaonline;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.java_websocket.WebSocket;

import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.client.StompClient;

public class GameRoomActivity extends AppCompatActivity implements View.OnClickListener{


    private StompClient mStompClient;
    Gson gson;
    MessageItem messageItem;
    Button sendButton;
    EditText messageInput;
    LinearLayout messageContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_room);


        sendButton = (Button)findViewById(R.id.send_button);
        messageInput = (EditText) findViewById(R.id.message_input);
        messageContainer =(LinearLayout)findViewById(R.id.messages_container);

        mStompClient = Stomp.over(WebSocket.class,"ws://192.168.1.222:8080/websockethandler/websocket");
        mStompClient.connect();


        mStompClient.topic("/topic/roomId").subscribe(topicMessage -> {
            TextView textView = new TextView(this);
            textView.setText(topicMessage.getPayload());

            Log.d("MainActivity", topicMessage.getPayload());
            messageContainer.addView(textView);
        });

        gson = new Gson();



        sendButton.setOnClickListener(this);



    }


    public void send(String message){
        MessageItem messageItem = new MessageItem(message);
        mStompClient.send("/app/hello",gson.toJson(new MessageItem("hello"))).subscribe();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mStompClient = Stomp.over(WebSocket.class,"ws://192.168.1.222:8080/websockethandelr/websocket");
        mStompClient.connect();
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.send_button){
            String message = messageInput.getText().toString();
            Log.d("MainActivity",message);
            messageInput.setText("");
            send(message);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mStompClient.disconnect();
    }
}

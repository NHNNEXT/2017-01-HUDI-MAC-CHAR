package com.zimincom.mafiaonline;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.zimincom.mafiaonline.item.MessageItem;
import com.zimincom.mafiaonline.item.ReadySignal;
import com.zimincom.mafiaonline.item.ResponseItem;
import com.zimincom.mafiaonline.item.User;
import com.zimincom.mafiaonline.remote.MafiaRemoteService;
import com.zimincom.mafiaonline.remote.ServiceGenerator;

import org.java_websocket.WebSocket;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.client.StompClient;


public class GameRoomActivity extends AppCompatActivity implements View.OnClickListener {


    final public String socketLink = MafiaRemoteService.SOCKET_URL;
    Gson gson;
    MessageItem messageItem;
    Toolbar toolbar;
    Button sendButton;
    Button readyButton;
    EditText messageInput;
    ChatLayout chatLayout;
    TextView textView;
    TextView timer;
    LinearLayout messageContainer;
    Intent intent;
    String roomId;
    String userName;
    User user;

    int gameTime = 120;
    boolean isGameStarted = false;
    private StompClient mStompClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_room);

        toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setTitle("입장대기중");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        user = (User) getIntent().getSerializableExtra("user");

        sendButton = (Button) findViewById(R.id.send_button);
        readyButton = (Button) findViewById(R.id.ready_button);
        timer = (TextView) findViewById(R.id.timer_view);
        messageInput = (EditText) findViewById(R.id.message_input);
        messageContainer = (LinearLayout) findViewById(R.id.messages_container);
        intent = getIntent();
        roomId = intent.getStringExtra("roomId");
        userName = intent.getStringExtra("userName");


        MafiaRemoteService mafiaRemoteService = ServiceGenerator.createService(MafiaRemoteService.class, getBaseContext());
        Call<ResponseItem> call = mafiaRemoteService.enterRoom(roomId);

        call.enqueue(new Callback<ResponseItem>() {
            @Override
            public void onResponse(Call<ResponseItem> call, Response<ResponseItem> response) {
                if (response.isSuccessful()) {
                    Logger.d(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseItem> call, Throwable t) {
                t.printStackTrace();
            }
        });

        mStompClient = Stomp.over(WebSocket.class, socketLink);
        mStompClient.connect();


        mStompClient.topic("/from/chat/" + roomId).subscribe(topicMessage -> {

            runOnUiThread(() -> {
                chatLayout = new ChatLayout(getBaseContext());
                MessageItem messageItem = gson.fromJson(topicMessage.getPayload(), MessageItem.class);
                chatLayout.setName(messageItem.userName);
                if (messageItem.userName.equals(userName)) {
                    chatLayout.setToMyMessage();
                }
                chatLayout.setMessage(messageItem.content);
                messageContainer.addView(chatLayout);

            });
        });


        mStompClient.topic("/from/ready/" + roomId).subscribe(message -> {

            runOnUiThread(() -> {
                ReadySignal readySignal = gson.fromJson(message.getPayload(), ReadySignal.class);
                if (readySignal.isStartTimer()) {
                    toolbar.setTitle("게임이 시작되었습니다.");
                    startTimer();
                }
            });

        });

        gson = new Gson();

        sendButton.setOnClickListener(this);
        readyButton.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void send(String message) {
        mStompClient.send("/to/chat/" + roomId, gson.toJson(new MessageItem(userName, message))).subscribe();
    }


    private void startTimer() {

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    runOnUiThread(() -> {
                        gameTime--;
                        int min = gameTime / 60;
                        int sec = gameTime % 60;
                        String time = min + ":" + sec;
                        timer.setText(time);
                        if (gameTime == 0) {
                            this.cancel();
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        runOnUiThread(timerTask);

        Timer mTimer = new Timer();
        mTimer.schedule(timerTask, 0, 1000);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mStompClient = Stomp.over(WebSocket.class, socketLink);
        mStompClient.connect();
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.send_button) {
            String message = messageInput.getText().toString();
            Log.d("MainActivity", message);
            messageInput.setText("");
            send(message);
        } else if (view.getId() == R.id.ready_button) {
            mStompClient.send("/to/ready/" + roomId, gson.toJson(new ReadySignal(userName))).subscribe();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mStompClient.disconnect();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (!isGameStarted)
                    onBackPressed();
                else {
                    Toast.makeText(getBaseContext(), "게임중에는 나갈 수 없습니다.", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}

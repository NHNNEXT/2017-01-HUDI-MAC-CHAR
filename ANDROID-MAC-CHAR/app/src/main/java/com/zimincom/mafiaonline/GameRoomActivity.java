package com.zimincom.mafiaonline;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.zimincom.mafiaonline.adapter.PlayerAdapter;
import com.zimincom.mafiaonline.item.ClientAccess;
import com.zimincom.mafiaonline.item.GameStart;
import com.zimincom.mafiaonline.item.MessageItem;
import com.zimincom.mafiaonline.item.ReadySignal;
import com.zimincom.mafiaonline.item.ResponseItem;
import com.zimincom.mafiaonline.item.User;
import com.zimincom.mafiaonline.remote.MafiaRemoteService;
import com.zimincom.mafiaonline.remote.ServiceGenerator;

import org.java_websocket.WebSocket;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.client.StompClient;


public class GameRoomActivity extends AppCompatActivity implements View.OnClickListener {


    final public String socketLink = MafiaRemoteService.SOCKET_URL;
    Gson gson;

    @BindView(R.id.toolBar) Toolbar toolbar;
    @BindView(R.id.send_button) Button sendButton;
    @BindView(R.id.ready_button) Button readyButton;
    @BindView(R.id.message_input) EditText messageInput;
    @BindView(R.id.timer_view) TextView timer;
    @BindView(R.id.messages_container) LinearLayout messageContainer;
    @BindView(R.id.sliding_layout) SlidingUpPanelLayout slidingLayout;
    @BindView(R.id.player_list) RecyclerView playerListView;


    Intent intent;
    String roomId;
    String userName;
    User user;
    ArrayList<User> users;
    PlayerAdapter playerAdapter;

    int gameTime = 0;
    boolean isGameStarted = false;
    private StompClient mStompClient;
    MafiaRemoteService mafiaRemoteService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_room);
        ButterKnife.bind(this);
        
        toolbar.setTitle("입장대기중");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        gson = new Gson();
        mafiaRemoteService = ServiceGenerator
                .createService(MafiaRemoteService.class, getBaseContext());
        ArrayList<User> users = new ArrayList<>();

        intent = getIntent();
        user = (User) getIntent().getSerializableExtra("user");
        roomId = intent.getStringExtra("roomId");
        userName = intent.getStringExtra("userName");

        enterRoom(roomId);

        playerAdapter = new PlayerAdapter(getBaseContext(), users, R.layout.item_player, userName);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getBaseContext(),4);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        playerListView.setLayoutManager(gridLayoutManager);
        playerListView.setItemAnimator(new DefaultItemAnimator());
        playerListView.setAdapter(playerAdapter);


        slidingLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {

            }
        });



        sendButton.setOnClickListener(this);
        readyButton.setOnClickListener(this);



    }

    private void subscribeSockets() {
        mStompClient.topic("/from/chat/" + roomId)
                .subscribe(message -> runOnUiThread(() -> {
                    ChatLayout chatLayout = new ChatLayout(getBaseContext());
                    MessageItem messageItem = gson.fromJson(message.getPayload(), MessageItem.class);
                    chatLayout.setName(messageItem.userName);
                    if (messageItem.userName.equals(userName)) {
                         chatLayout.setToMyMessage();
                    }
                    chatLayout.setMessage(messageItem.content);
                    messageContainer.addView(chatLayout);

        }));

        mStompClient.topic("/from/ready/" + roomId)
                .subscribe(message -> runOnUiThread(() -> {
                    ReadySignal readySignal = gson.fromJson(message.getPayload(), ReadySignal.class);
                    Logger.d(readySignal);
                    playerAdapter.ready(readySignal.getUserName());
                    if (readySignal.isStartTimer()) {
                        toolbar.setTitle("게임이 시작되었습니다.");
                    startTimer(5);
                }
        }));

        mStompClient.topic("/from/access/" + roomId)
                .subscribe(message -> runOnUiThread(() -> {
                    ClientAccess clientAccess = gson.fromJson(message.getPayload(), ClientAccess.class);
                    users = clientAccess.getUsers();
                    String accessState = clientAccess.getAccess();
                    String newUserName = clientAccess.getUserName();
                    if (accessState.equals("enter")) {

                        if (newUserName.equals(userName))
                            playerAdapter.addItems(users);
                        else
                            playerAdapter.addItemByNickName(newUserName);

                    } else if (accessState.equals("exit")) {
                        playerAdapter.removeItemByNickName(newUserName);
                    }
                }));

        mStompClient.topic("/from/gameStart/" + roomId + "/" + userName)
                .subscribe(message -> runOnUiThread(() -> {
                    String role = gson.fromJson(message.getPayload(), String.class);
                    Logger.d(role);
                }));

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void send(String message) {
        mStompClient.send("/to/chat/" + roomId, gson.toJson(new MessageItem(userName, message))).subscribe();
    }


    private void startTimer(int time) {

        gameTime = time;
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    runOnUiThread(() -> {
                        gameTime--;
                        int min = gameTime / 60;
                        int sec = gameTime % 60;
                        String clockText;
                        if (sec < 10) {
                            clockText = min + ":0" + sec;
                        } else {
                            clockText = min + ":" + sec;
                        }
                        timer.setText(clockText);
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

    private void enterRoom(String roomId) {
        Call<ResponseItem> call = mafiaRemoteService.enterRoom(roomId);

        call.enqueue(new Callback<ResponseItem>() {
            @Override
            public void onResponse(Call<ResponseItem> call, Response<ResponseItem> response) {
                if (response.isSuccessful()) {
                    Logger.d(response.body());
                    mStompClient.send("/to/access/" + roomId, gson.toJson(new ClientAccess(userName, "enter"))).subscribe();
                }
            }

            @Override
            public void onFailure(Call<ResponseItem> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
    private void leaveRoom() {
        mStompClient.send("/to/access/" + roomId, gson.toJson(new ClientAccess(userName, "exit"))).subscribe();
        Call<ResponseItem> call = mafiaRemoteService.leaveRoom();
        call.enqueue(new Callback<ResponseItem>() {
            @Override
            public void onResponse(Call<ResponseItem> call, Response<ResponseItem> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getBaseContext(),"로비로 돌아왔습니다.",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseItem> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getBaseContext(),"정상적으로 처리되지 않았습니다",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mStompClient = Stomp.over(WebSocket.class, socketLink);
        mStompClient.connect();
        subscribeSockets();
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
            mStompClient.send("/to/gameStart/" + roomId + userName, gson.toJson(new GameStart())).subscribe();
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
                if (!isGameStarted) {
                    leaveRoom();
                    onBackPressed();
                }
                else {
                    Toast.makeText(getBaseContext(), "게임중에는 나갈 수 없습니다.", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!isGameStarted) {
            leaveRoom();
        }
        else {
            Toast.makeText(getBaseContext(), "게임중에는 나갈 수 없습니다.", Toast.LENGTH_SHORT).show();
        }
    }
}

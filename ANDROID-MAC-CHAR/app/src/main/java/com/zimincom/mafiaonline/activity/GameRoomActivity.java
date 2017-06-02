package com.zimincom.mafiaonline.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.zimincom.mafiaonline.layout.ChatLayout;
import com.zimincom.mafiaonline.tasks.GameTimerTask;
import com.zimincom.mafiaonline.R;
import com.zimincom.mafiaonline.adapter.MessageAdapter;
import com.zimincom.mafiaonline.adapter.PlayerAdapter;
import com.zimincom.mafiaonline.item.ClientAccess;
import com.zimincom.mafiaonline.item.GameConfig;
import com.zimincom.mafiaonline.item.GameResult;
import com.zimincom.mafiaonline.item.GameStart;
import com.zimincom.mafiaonline.item.InvestMessage;
import com.zimincom.mafiaonline.item.MessageItem;
import com.zimincom.mafiaonline.item.ReadySignal;
import com.zimincom.mafiaonline.item.ResponseItem;
import com.zimincom.mafiaonline.item.User;
import com.zimincom.mafiaonline.item.VoteMessage;
import com.zimincom.mafiaonline.remote.MafiaRemoteService;
import com.zimincom.mafiaonline.remote.ServiceGenerator;

import org.java_websocket.WebSocket;

import java.util.ArrayList;
import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.client.StompClient;

import static com.zimincom.mafiaonline.R.raw.day;


public class GameRoomActivity extends AppCompatActivity implements View.OnClickListener {


    final public String socketLink = MafiaRemoteService.SOCKET_URL;
    Gson gson;

    @BindView(R.id.toolBar)
    Toolbar toolbar;
    @BindView(R.id.send_button)
    Button sendButton;
    @BindView(R.id.slide)
    Button slideButton;
    @BindView(R.id.message_input)
    EditText messageInput;
    @BindView(R.id.timer_view)
    TextView timer;
    @BindView(R.id.messages_container)
    RecyclerView messageContainer;
    @BindView(R.id.sliding_layout)
    SlidingUpPanelLayout slidingLayout;
    @BindView(R.id.player_list)
    RecyclerView playerListView;
    @BindView(R.id.info_message)
    TextView infoText;

    Intent intent;
    String roomId;
    String userName;
    String gameState;
    GameConfig.GameState stage;
    User user;
    ArrayList<User> users;
    ArrayList<MessageItem> messages;
    PlayerAdapter playerAdapter;
    MessageAdapter messageAdapter;
    ArrayList<GameConfig> gConfigs;

    int gameTime = 0;
    boolean isGameStarted = false;
    MafiaRemoteService mafiaRemoteService;
    MediaPlayer bgm;

    private StompClient mStompClient;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mStompClient = Stomp.over(WebSocket.class, socketLink);
        mStompClient.connect();
        subscribeSockets();
    }

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
        bgm = MediaPlayer.create(getBaseContext(), day);
        bgm.setLooping(true);
        bgm.start();

        gConfigs = new ArrayList<>();
        gConfigs.add(new GameConfig(5, GameConfig.GameState.WAITING, "시작 대기중입니다."));
        gConfigs.add(new GameConfig(60, GameConfig.GameState.DAY, "의심되는 플레이어를 선택하세요"));
        gConfigs.add(new GameConfig(5, GameConfig.GameState.WAITING, "결과 처리중입니다."));
        gConfigs.add(new GameConfig(30, GameConfig.GameState.NIGHT, "역할에 따라 선택하세요."));
        gConfigs.add(new GameConfig(5, GameConfig.GameState.WAITING, "결과 처리중입니다."));

        ArrayList<User> users = new ArrayList<>();
        messages = new ArrayList<>();

        intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        roomId = intent.getStringExtra("roomId");
        userName = intent.getStringExtra("userName");

        enterRoom(roomId);

        playerAdapter = new PlayerAdapter(getBaseContext(), users, R.layout.item_player, userName, " ", gameHandler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getBaseContext(), 4);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        playerListView.setLayoutManager(gridLayoutManager);
        playerListView.setItemAnimator(new DefaultItemAnimator());
        playerListView.setAdapter(playerAdapter);


        messageAdapter = new MessageAdapter(getBaseContext(), messages, R.layout.my_chat, userName);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
        messageContainer.setLayoutManager(linearLayoutManager);
        messageContainer.setItemAnimator(new DefaultItemAnimator());
        messageContainer.setAdapter(messageAdapter);


        slidingLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState
                    previousState, SlidingUpPanelLayout.PanelState newState) {
            }
        });

        sendButton.setOnClickListener(this);
        slideButton.setOnClickListener(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        bgm.stop();
        mStompClient.disconnect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        leaveRoom();
    }

    private void subscribeSockets() {
        mStompClient.topic("/from/chat/" + roomId)
                .subscribe(message -> runOnUiThread(() -> {
                    ChatLayout chatLayout = new ChatLayout(getBaseContext());
                    //change to recycler
                    MessageItem messageItem =
                            gson.fromJson(message.getPayload(), MessageItem.class);
                    messageAdapter.addMessage(messageItem);
                }));

        mStompClient.topic("/from/ready/" + roomId)
                .subscribe(message -> runOnUiThread(() -> {
                    ReadySignal readySignal =
                            gson.fromJson(message.getPayload(), ReadySignal.class);
                    Logger.d(message.getPayload());
                    if (!readySignal.getUserName().equals(userName))
                        playerAdapter.ready(readySignal.getUserName());
                    if (readySignal.isStartTimer()) {
                        toolbar.setTitle("게임이 시작되었습니다.");
                        mStompClient.send("/to/gameStart/" + roomId + "/" + userName,
                                gson.toJson(new GameStart(userName))).subscribe();
                    }
                }));

        mStompClient.topic("/from/access/" + roomId)
                .subscribe(message -> runOnUiThread(() -> {
                    ClientAccess clientAccess =
                            gson.fromJson(message.getPayload(), ClientAccess.class);
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
                    String role = message.getPayload().trim();
                    Logger.d(role);
                    user.setRoleTo(role);
                    String roleMessage = "당신의 직업은 " + role + "입니다";
                    infoText.setText(roleMessage);
                    //게임 타이머 시작
                    gameHandler.sendEmptyMessage(GameTimerTask.PHASE_CHANGE);
                    //게임상태를 낮으로. String 이 아니라 다른것이 필요 .
                    playerAdapter.setState("day");

                }));

        mStompClient.topic("/from/votestart/")
                .subscribe(message -> runOnUiThread(() -> {
                    Logger.d("phase chaged");
                }));

        mStompClient.topic("/from/vote/" + roomId)
                .subscribe(message -> runOnUiThread(() -> {
                    GameResult gameResult = gson.fromJson(message.getPayload(), GameResult.class);
                 //  killPlayer(gameResult.getMsg());
                    Logger.d("killedPlayer: %s", gameResult.getMsg());
                    if (gameResult.isFinished()) {
                        Toast.makeText(getBaseContext(), "게임이 끝났습니다", Toast.LENGTH_SHORT).show();
                        return;//how to end game and prepare next game?
                    }
                    playerAdapter.killByNickName(gameResult.getMsg());
                }));

        mStompClient.topic("/from/invest/" + roomId + "/" + userName)
                .subscribe(message -> runOnUiThread(() -> {
                    String investMessage = message.getPayload();
                    Toast.makeText(getBaseContext(), "조사결과:" + investMessage, Toast.LENGTH_LONG).show();
                }));
    }

//    private void killPlayer(String votedNickname) {
//        Logger.d("killed " + votedNickname);
//    }

    private void send(String message) {
        mStompClient.send("/to/chat/" + roomId,
                gson.toJson(new MessageItem(userName, message))).subscribe();
    }

    private void startTimer(int time) {

        GameTimerTask gameTimerTask = new GameTimerTask(time, gameHandler);
        Timer mTimer = new Timer();
        mTimer.schedule(gameTimerTask, 0, 1000);
    }


    Handler gameHandler = new Handler() {
        int phaseNum = 0;

        public void handleMessage(Message message) {

            if (phaseNum == gConfigs.size()) phaseNum = 1;

            if (message.what == GameTimerTask.PHASE_CHANGE && phaseNum < gConfigs.size()) {

                String votedUser = playerAdapter.getVotedUserName();
                if (!votedUser.equals("")) {
                    Logger.d("send vote info");
                    sendVoteInfo(votedUser);
                } else if (user.getRole() == User.Role.POLICE) {
                    Logger.d("send investigation");
                    requestInvestigation(votedUser);
                }
                stage = gConfigs.get(phaseNum).getGameState();
                startTimer(gConfigs.get(phaseNum).getGameTime());
                infoText.setText(gConfigs.get(phaseNum).getGameMessage());
                phaseNum++;
                return;
            } else if (message.what == PlayerAdapter.READY_MESSAGE) {
                mStompClient.send("/to/ready/" + roomId, gson.toJson(new ReadySignal(userName)))
                        .subscribe();
            } else {
                //timer num update
                int gameTime = message.arg1;
                int min = gameTime / 60;
                int sec = gameTime % 60;
                String clockText;
                if (sec < 10) {
                    clockText = min + ":0" + sec;
                } else {
                    clockText = min + ":" + sec;
                }
                timer.setText(clockText);
            }

        }
    };

    private void requestInvestigation(String votedUser) {
        mStompClient.send("/to/invest/" + roomId + "/" + userName, gson.toJson(new InvestMessage(votedUser)))
                .subscribe();
    }

    private void sendVoteInfo(String votedUser) {
        if (stage == GameConfig.GameState.DAY)
            mStompClient.send("/to/vote/" + roomId, gson.toJson(new VoteMessage(userName, votedUser, "day")))
                    .subscribe();

        if (stage == GameConfig.GameState.NIGHT)
            mStompClient.send("/to/vote/" + roomId, gson.toJson(new VoteMessage(userName, votedUser, "night")))
                    .subscribe();

    }

    private void enterRoom(String roomId) {
        Call<ResponseItem> call = mafiaRemoteService.enterRoom(roomId);

        call.enqueue(new Callback<ResponseItem>() {
            @Override
            public void onResponse(Call<ResponseItem> call, Response<ResponseItem> response) {
                if (response.isSuccessful()) {
                    Logger.d(response.body());
                    mStompClient.send("/to/access/" + roomId,
                            gson.toJson(new ClientAccess(userName, "enter"))).subscribe();
                }
            }

            @Override
            public void onFailure(Call<ResponseItem> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    private void leaveRoom() {
        mStompClient.send("/to/access/" + roomId, gson.toJson(new ClientAccess(userName, "exit")))
                .subscribe();
        Call<ResponseItem> call = mafiaRemoteService.leaveRoom();
        call.enqueue(new Callback<ResponseItem>() {
            @Override
            public void onResponse(Call<ResponseItem> call, Response<ResponseItem> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getBaseContext(), "로비로 돌아왔습니다.", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseItem> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getBaseContext(), "정상적으로 처리되지 않았습니다", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.send_button) {
            String message = messageInput.getText().toString();
            Log.d("MainActivity", message);
            messageInput.setText("");
            send(message);

        } else if (view.getId() == R.id.slide) {

            slidingLayout.setPanelState(
                    (slidingLayout.getPanelState() == SlidingUpPanelLayout.PanelState.COLLAPSED)
                            ? SlidingUpPanelLayout.PanelState.EXPANDED
                            : SlidingUpPanelLayout.PanelState.COLLAPSED);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (!isGameStarted) {
                    leaveRoom();
                    onBackPressed();
                } else {
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
        } else {
            Toast.makeText(getBaseContext(), "게임중에는 나갈 수 없습니다.", Toast.LENGTH_SHORT).show();
        }
    }
}

package com.zimincom.mafiaonline;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.zimincom.mafiaonline.item.ResponseItem;
import com.zimincom.mafiaonline.item.User;
import com.zimincom.mafiaonline.remote.MafiaRemoteService;
import com.zimincom.mafiaonline.remote.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {


    Context context = LoginActivity.this;
    ImageView logoImage;
    ImageView logoText;
    EditText emailInput;
    EditText passwordInput;
    Button loginButton;
    Button signInButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        logoImage = (ImageView) findViewById(R.id.logo_image);
        logoText = (ImageView) findViewById(R.id.logo_text);
        emailInput = (EditText) findViewById(R.id.email);
        passwordInput = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.login);
        signInButton = (Button) findViewById(R.id.signin);

        startMainAnimation();
        setButtons();

    }


    void setButtons() {
        loginButton.setOnClickListener(view -> {

            String email = emailInput.getText().toString();
            String password = passwordInput.getText().toString();

            sendUserData(email, password);

        });

        loginButton.setOnLongClickListener(view -> {

            Intent intent = new Intent(getApplicationContext(), RoomListActivity.class);
            startActivity(intent);
            return false;
        });

        signInButton.setOnClickListener(view -> {

            Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
            startActivity(intent);

        });
    }

    void sendUserData(String userEmail, String userPassword) {

        User user = new User(userEmail, userPassword);

        MafiaRemoteService mafiaRemoteService = ServiceGenerator.createService(MafiaRemoteService.class, getBaseContext());
        Call<ResponseItem> call = mafiaRemoteService.sendLoginInput(user);

        call.enqueue(new Callback<ResponseItem>() {

            @Override
            public void onResponse(Call<ResponseItem> call, Response<ResponseItem> response) {
                if (response.isSuccessful()) {
                    ResponseItem responseItem = response.body();
                    User user = responseItem.getUser();
                    Logger.i(user.toString());

                    if (responseItem.isOk()) {
                        Toast.makeText(context, user.getNickName() + "님 환영합니다 ", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(context, RoomListActivity.class);
                        intent.putExtra("user", user);
                        startActivity(intent);
                    } else if (responseItem.isEmailNotFound()) {
                        Toast.makeText(context, "존재하지 않는 이메일입니다.", Toast.LENGTH_LONG).show();
                    } else if (responseItem.isPasswordInvaild()) {
                        Toast.makeText(context, "잘못된 비밀번호 입니다.", Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseItem> call, Throwable t) {
                Log.d("response", "error");
                t.printStackTrace();
            }
        });

    }


    void startMainAnimation() {
        TranslateAnimation moveUpAnimation = new TranslateAnimation(0, 0, 0, -1000);

        moveUpAnimation.setDuration(1000);
        moveUpAnimation.setFillAfter(true);
        moveUpAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                emailInput.setVisibility(View.VISIBLE);
                passwordInput.setVisibility(View.VISIBLE);
                loginButton.setVisibility(View.VISIBLE);
                signInButton.setVisibility(View.VISIBLE);

            }
        });

        logoText.startAnimation(moveUpAnimation);
    }
}

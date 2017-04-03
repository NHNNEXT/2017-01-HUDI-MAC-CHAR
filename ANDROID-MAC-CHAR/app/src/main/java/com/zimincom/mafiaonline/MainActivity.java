package com.zimincom.mafiaonline;

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

import com.zimincom.mafiaonline.item.User;
import com.zimincom.mafiaonline.remote.MafiaRemoteService;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity {


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

        logoImage= (ImageView)findViewById(R.id.logo_image);
        logoText = (ImageView)findViewById(R.id.logo_text);
        emailInput = (EditText)findViewById(R.id.email);
        passwordInput = (EditText)findViewById(R.id.password);
        loginButton = (Button)findViewById(R.id.login);
        signInButton = (Button)findViewById(R.id.signin);
        startMainAnimation();


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();


                sendUserData(email,password);

                Log.i("mainact",email);
                Log.i("mainact",password);

            }


        });

        loginButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(getApplicationContext(),RoomListActivity.class);
                startActivity(intent);
                return false;
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SignInActivity.class);
                startActivity(intent);
            }
        });
    }


    void sendUserData(String userEmail,String userPassword){


        String result = "";


        User user = new User(userEmail,userPassword);

        MafiaRemoteService mafiaRemoteService = MafiaRemoteService.retrofit.create(MafiaRemoteService.class);
        Call<String> call = mafiaRemoteService.sendLoginInput(user);
        try{
             result = call.execute().body();
        }catch (Exception e){
            e.printStackTrace();
        }

        Log.d("MainAct",result);


    }



    void startMainAnimation(){
        TranslateAnimation moveUpAnimation = new TranslateAnimation(0,0,0,-1000);

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

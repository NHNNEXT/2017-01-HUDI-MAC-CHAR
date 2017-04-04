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

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.zimincom.mafiaonline.item.User;
import com.zimincom.mafiaonline.remote.MafiaRemoteService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.zimincom.mafiaonline.remote.MafiaRemoteService.retrofit;

public class MainActivity extends AppCompatActivity {


    Context context = MainActivity.this;
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

        MafiaRemoteService mafiaRemoteService = retrofit.create(MafiaRemoteService.class);
        Call<ResponseItem> call = mafiaRemoteService.sendLoginInput(user);
//
//        try{
//            ResponseItem responseItem = call.execute().body();
//            Log.d("main",responseItem.toString());
//        }catch(Exception e){
//            e.printStackTrace();
//        }


        Gson gson = new Gson();


        call.enqueue(new Callback<ResponseItem>() {

            @Override
            public void onResponse(Call<ResponseItem> call, Response<ResponseItem> response) {
                Logger.d(response.body());
                ResponseItem responseItem = response.body();

                if (responseItem.statusCode.equals("Ok")){
                    Toast.makeText(context,responseItem.statusCode,Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context,RoomListActivity.class);
                    startActivity(intent);
                }else if(responseItem.statusCode.equals("EmailNotFound")){
                    Toast.makeText(context,responseItem.statusCode,Toast.LENGTH_LONG).show();
                }else if(responseItem.statusCode.equals("InvalidPassword")) {
                    Toast.makeText(context,responseItem.statusCode,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseItem> call, Throwable t) {
                Log.d("response","error");
                t.printStackTrace();
            }
        });


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

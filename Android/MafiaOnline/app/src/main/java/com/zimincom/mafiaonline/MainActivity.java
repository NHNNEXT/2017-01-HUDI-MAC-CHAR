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
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    ImageView logoImage;
    ImageView logoText;
    EditText emailInput;
    EditText passwordInput;
    Button loginButton;
    Button signInButton;

    private String userEmail = "admin";
    private String userPassword = "admin";

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

                if (email.equals(userEmail)&&password.equals(userPassword)){

                    Intent intent = new Intent(getApplicationContext(),RoomListActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),"환영합니다 admin!!",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"정확하지 않은 입력입니다",Toast.LENGTH_SHORT).show();
                }
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

        String data = userEmail + userPassword;
        Log.i("MainActivity",data);

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

package com.zimincom.mafiaonline;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.zimincom.mafiaonline.item.ResponseItem;
import com.zimincom.mafiaonline.item.User;
import com.zimincom.mafiaonline.remote.MafiaRemoteService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.zimincom.mafiaonline.remote.MafiaRemoteService.retrofit;

public class SignUpActivity extends AppCompatActivity {


    Context context = SignUpActivity.this;

    EditText nickNameInput;
    EditText emailInput;
    EditText passwordInput;
    EditText passwordCheckInput;
    Button signInButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nickNameInput = (EditText)findViewById(R.id.nickname_input);
        emailInput = (EditText)findViewById(R.id.email_input);
        passwordInput = (EditText)findViewById(R.id.password);
        passwordCheckInput = (EditText)findViewById(R.id.password_check);
        signInButton = (Button)findViewById(R.id.signin);

        signInButton.setOnClickListener(view -> {
            String nickName = nickNameInput.getText().toString();
            String email = emailInput.getText().toString();
            String password = passwordInput.getText().toString();

            User user = new User(nickName,email,password);

            MafiaRemoteService mafiaRemoteService = retrofit.create(MafiaRemoteService.class);
            Call<ResponseItem> call = mafiaRemoteService.sendSignUpInfo(user);

            call.enqueue(new Callback<ResponseItem>() {

                @Override
                public void onResponse(Call<ResponseItem> call, Response<ResponseItem> response) {
                    Logger.d(response.body());
                    ResponseItem responseItem = response.body();

                    if (responseItem.status.equals("Ok")){
                        Toast.makeText(context,responseItem.status,Toast.LENGTH_LONG).show();
                        finish();
                    }else if(responseItem.status.equals("EmailExits")){
                        Toast.makeText(context,responseItem.status,Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<ResponseItem> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}

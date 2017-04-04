package com.zimincom.mafiaonline;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.EditText;

public class SignInActivity extends AppCompatActivity {

    EditText nickNameInput;
    EditText emailInput;
    EditText passwordInput;
    EditText passwordCheckInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nickNameInput = (EditText)findViewById(R.id.nickname_input);
        emailInput = (EditText)findViewById(R.id.email_input);
        passwordInput = (EditText)findViewById(R.id.password);
        passwordCheckInput = (EditText)findViewById(R.id.password_check);




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

package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    ImageButton backBtn, iddelBtn, pwddelBtn, naverBtn, kakaoBtn;
    textboxJoin idBox, pwdBox;
    CheckBox autoidBtn;
    Button infoSearchBtn, loginBtn, joinBtn;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        backBtn = (ImageButton)findViewById(R.id.backBtn);
        loginBtn = (Button)findViewById(R.id.loginBtn);
        infoSearchBtn = (Button) findViewById(R.id.infoSearchBtn);
        joinBtn = (Button)findViewById(R.id.joinBtn);
        naverBtn = (ImageButton)findViewById(R.id.naverBtn);
        idBox = findViewById(R.id.idBox);
        pwdBox = findViewById(R.id.pwdBox);
        autoidBtn = findViewById(R.id.autoidBtn);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        Boolean IdisChecked = preferences.getBoolean("idSave", false);
        if(IdisChecked){
            autoidBtn.setChecked(true);
            String saveId = preferences.getString("id", "");
            idBox.editText.setText(saveId);
        }

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Editable userId = idBox.editText.getText();

        idBox.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                idBox.GuideTxt.setVisibility(View.GONE);
                idBox.delBtn.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        idBox.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userId.clear();
            }
        });

        Editable userPwd = pwdBox.editText.getText();

        pwdBox.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                pwdBox.GuideTxt.setVisibility(View.GONE);
                pwdBox.delBtn.setVisibility(View.VISIBLE);
                pwdBox.ConfirmBtn.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        pwdBox.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userPwd.clear();
            }
        });




        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    String result;

                    ConnectLoginActivity task = new ConnectLoginActivity();
                    result = task.execute(userId.toString(), userPwd.toString()).get();

                    if(result.equals("success")){
                        finish();
                        Intent intent = new Intent(LoginActivity.this,
                                FragmenthomeActivity.class);
                        intent.putExtra("id", userId.toString());
                        intent.putExtra("checked", "home");
                        startActivity(intent);
                    }else if(result.equals("fail")){
                        Toast.makeText(LoginActivity.this,"아이디 또는 비밀번호를 잘못 입력했습니다.",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    Log.i("DBtest", "Error");
                }


            }
        });


        infoSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,
                        infofindActivity.class);
                intent.putExtra("radio_checked",1000019);
                startActivity(intent);
            }
        });

        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,
                        JoinActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("idSave", autoidBtn.isChecked());
        editor.putString("id", idBox.editText.getText().toString());

        editor.apply();
    }
}
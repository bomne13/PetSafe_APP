package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class pwdresultActivity extends AppCompatActivity {

    ImageButton backBtn;
    Button submitBtn;
    textboxJoin pwdBox, checkpwdBox;

    String checkEqualsPwd="false";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwdresult);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String phone = intent.getStringExtra("phone");
        String before_pwd = intent.getStringExtra("before_pwd");

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        pwdBox = findViewById(R.id.pwdBox);
        Editable pwd = pwdBox.editText.getText();

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
                if(pwd.length() == 0){
                    pwdBox.GuideTxt.setVisibility(View.VISIBLE);
                    pwdBox.GuideTxt.setText("비밀번호를 입력해주세요.");
                    pwdBox.delBtn.setVisibility(View.GONE);
                    pwdBox.setLayoutLine(R.drawable.roundborder_r4);
                    pwdBox.ConfirmBtn.setVisibility(View.GONE);
                }else{
                    pwdBox.GuideTxt.setVisibility(View.GONE);
                    pwdBox.setLayoutLine(R.drawable.roundborder_g4);
                }
            }
        });

        pwdBox.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pwd.clear();
            }
        });

        checkpwdBox = findViewById(R.id.checkpwdBox);
        Editable checkPwd = checkpwdBox.editText.getText();


        checkpwdBox.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkpwdBox.GuideTxt.setVisibility(View.GONE);
                checkpwdBox.delBtn.setVisibility(View.VISIBLE);
                checkpwdBox.ConfirmBtn.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(checkPwd.length() == 0){
                    checkpwdBox.GuideTxt.setVisibility(View.VISIBLE);
                    checkpwdBox.GuideTxt.setText("비밀번호를 입력해주세요.");
                    checkpwdBox.delBtn.setVisibility(View.GONE);
                    checkpwdBox.setLayoutLine(R.drawable.roundborder_r4);
                    checkpwdBox.ConfirmBtn.setVisibility(View.GONE);
                    checkEqualsPwd="false";
                }else if(!checkPwd.toString().equals(pwd.toString())){
                    checkpwdBox.GuideTxt.setVisibility(View.VISIBLE);
                    checkpwdBox.GuideTxt.setText("비밀번호가 일치하지 않습니다.");
                    checkpwdBox.delBtn.setVisibility(View.VISIBLE);
                    checkpwdBox.setLayoutLine(R.drawable.roundborder_r4);
                    checkpwdBox.ConfirmBtn.setVisibility(View.GONE);
                    checkEqualsPwd="false";
                }else{
                    checkpwdBox.GuideTxt.setVisibility(View.GONE);
                    checkpwdBox.setLayoutLine(R.drawable.roundborder_g4);
                    checkEqualsPwd="true";
                }
            }
        });

        checkpwdBox.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPwd.clear();
            }
        });

        submitBtn=findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkEqualsPwd.equals("false")) {
                    Toast.makeText(pwdresultActivity.this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                }else if(pwd.length() == 0 || checkPwd.length() == 0){
                    Toast.makeText(pwdresultActivity.this, "필수 입력란을 입력해주세요", Toast.LENGTH_SHORT).show();
                }else if(pwd.toString().equals(before_pwd)){
                    pwdBox.GuideTxt.setVisibility(View.VISIBLE);
                    pwdBox.GuideTxt.setText("이전 비밀번호와 같습니다. 다른 비밀번호를 입력해주세요");
                    pwdBox.delBtn.setVisibility(View.VISIBLE);
                    pwdBox.setLayoutLine(R.drawable.roundborder_r4);
                    pwdBox.ConfirmBtn.setVisibility(View.GONE);
                }else{
                    try {
                        String pwdChangeResult;

                        ConnectPwdChangeActivity task = new ConnectPwdChangeActivity();
                        pwdChangeResult = task.execute(id.toString(), phone.toString(), checkPwd.toString()).get();

                        if (pwdChangeResult.equals("success")) {
                            finish();
                            Intent intent = new Intent(pwdresultActivity.this,
                                    LoginActivity.class);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(),"비밀번호가 변경되었습니다.",Toast.LENGTH_SHORT).show();
                        } else if(pwdChangeResult.equals("fail")){
                            Toast.makeText(pwdresultActivity.this, "비밀번호 변경에 실패했습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(pwdresultActivity.this, pwdChangeResult, Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        Log.i("DBtest", "Error");
                    }
                }

            }
        });

    }
}
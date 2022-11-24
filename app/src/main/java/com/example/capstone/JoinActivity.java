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

public class JoinActivity extends AppCompatActivity {

    ImageButton backBtn;
    Button submitBtn;
    textboxJoin idBox, pwdBox, checkpwdBox, nameBox, nicknameBox, phoneBox, checkphoneBox;

    String checkId="false", checkNickname="false", checkEqualsPwd="false";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);



        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        idBox = findViewById(R.id.idBox);
        Editable userId = idBox.editText.getText();

        idBox.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                idBox.GuideTxt.setVisibility(View.GONE);
                idBox.delBtn.setVisibility(View.VISIBLE);
                idBox.ConfirmBtn.setVisibility(View.VISIBLE);
                idBox.setConfirmBtn(R.drawable.roundbtn_y4);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(userId.length() == 0){
                    idBox.GuideTxt.setVisibility(View.VISIBLE);
                    idBox.GuideTxt.setText("아이디를 입력해주세요.");
                    idBox.delBtn.setVisibility(View.GONE);
                    idBox.setLayoutLine(R.drawable.roundborder_r4);
                    idBox.ConfirmBtn.setVisibility(View.VISIBLE);
                    idBox.setConfirmBtn(R.drawable.roundbtn_g4);
                    checkId = "false";
                }else{
                    idBox.GuideTxt.setVisibility(View.GONE);
                    idBox.setLayoutLine(R.drawable.roundborder_g4);
                }
            }
        });

        idBox.ConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String idCheckResult;

                    ConnectIdCheckActivity task = new ConnectIdCheckActivity();
                    idCheckResult = task.execute(userId.toString()).get();

                    if(idCheckResult.equals("success")){
                        idBox.setLayoutLine(R.drawable.roundborder_g4);
                        idBox.GuideTxt.setVisibility(View.VISIBLE);
                        idBox.GuideTxt.setText("사용 가능한 아이디 입니다.");
                        checkId = "true";
                    }else if(idCheckResult.equals("fail")){
                        idBox.setLayoutLine(R.drawable.roundborder_r4);
                        idBox.GuideTxt.setVisibility(View.VISIBLE);
                        idBox.GuideTxt.setText("이미 사용중인 아이디 입니다.");
                        checkId = "false";
                    }else{
                        Toast.makeText(JoinActivity.this, idCheckResult, Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    Log.i("DBtest", "Error");
                }
            }
        });

        idBox.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userId.clear();
            }
        });

        pwdBox = findViewById(R.id.pwdBox);
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
                if(userPwd.length() == 0){
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
                userPwd.clear();
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
                }else if(!checkPwd.toString().equals(userPwd.toString())){
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

        nameBox = findViewById(R.id.nameBox);
        Editable name = nameBox.editText.getText();

        nameBox.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                nameBox.GuideTxt.setVisibility(View.GONE);
                nameBox.delBtn.setVisibility(View.VISIBLE);
                nameBox.ConfirmBtn.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(name.length() == 0){
                    nameBox.GuideTxt.setVisibility(View.VISIBLE);
                    nameBox.GuideTxt.setText("이름을 입력해주세요.");
                    nameBox.delBtn.setVisibility(View.GONE);
                    nameBox.setLayoutLine(R.drawable.roundborder_r4);
                    nameBox.ConfirmBtn.setVisibility(View.GONE);
                }else{
                    nameBox.GuideTxt.setVisibility(View.GONE);
                    nameBox.setLayoutLine(R.drawable.roundborder_g4);
                }
            }
        });

        nameBox.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.clear();
            }
        });

        nicknameBox = findViewById(R.id.nicknameBox);
        Editable nickname = nicknameBox.editText.getText();

        nicknameBox.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                nicknameBox.GuideTxt.setVisibility(View.GONE);
                nicknameBox.delBtn.setVisibility(View.VISIBLE);
                nicknameBox.ConfirmBtn.setVisibility(View.VISIBLE);
                nicknameBox.setConfirmBtn(R.drawable.roundbtn_y4);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(nickname.length() == 0){
                    nicknameBox.GuideTxt.setVisibility(View.VISIBLE);
                    nicknameBox.GuideTxt.setText("닉네임를 입력해주세요.");
                    nicknameBox.delBtn.setVisibility(View.GONE);
                    nicknameBox.setLayoutLine(R.drawable.roundborder_r4);
                    nicknameBox.ConfirmBtn.setVisibility(View.VISIBLE);
                    nicknameBox.setConfirmBtn(R.drawable.roundbtn_g4);
                }else{
                    nicknameBox.GuideTxt.setVisibility(View.GONE);
                    nicknameBox.setLayoutLine(R.drawable.roundborder_g4);
                }
            }
        });

        nicknameBox.ConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String nicknameCheckResult;

                    ConnectNicknameCheckActivity task = new ConnectNicknameCheckActivity();
                    nicknameCheckResult = task.execute(nickname.toString()).get();

                    if(nicknameCheckResult.equals("success")){
                        nicknameBox.setLayoutLine(R.drawable.roundborder_g4);
                        nicknameBox.GuideTxt.setVisibility(View.VISIBLE);
                        nicknameBox.GuideTxt.setText("사용 가능한 닉네임 입니다.");
                        checkNickname = "true";
                    }else if(nicknameCheckResult.equals("fail")){
                        nicknameBox.setLayoutLine(R.drawable.roundborder_r4);
                        nicknameBox.GuideTxt.setVisibility(View.VISIBLE);
                        nicknameBox.GuideTxt.setText("이미 사용중인 닉네임 입니다.");
                        checkNickname = "false";
                    }else{
                        Toast.makeText(JoinActivity.this, nicknameCheckResult, Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    Log.i("DBtest", "Error");
                }
            }
        });

        nicknameBox.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nickname.clear();
            }
        });



        phoneBox = findViewById(R.id.phoneBox);
        Editable phone = phoneBox.editText.getText();

        phoneBox.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                phoneBox.GuideTxt.setVisibility(View.GONE);
                phoneBox.delBtn.setVisibility(View.VISIBLE);
                phoneBox.ConfirmBtn.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(phone.length() == 0){
                    phoneBox.GuideTxt.setVisibility(View.VISIBLE);
                    phoneBox.GuideTxt.setText("전화번호를 입력해주세요.");
                    phoneBox.delBtn.setVisibility(View.GONE);
                    phoneBox.setLayoutLine(R.drawable.roundborder_r4);
                    phoneBox.ConfirmBtn.setVisibility(View.GONE);
                }else{
                    phoneBox.GuideTxt.setVisibility(View.GONE);
                    phoneBox.setLayoutLine(R.drawable.roundborder_g4);
                }
            }
        });

        phoneBox.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone.clear();
            }
        });


        submitBtn = findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checkId.equals("false")||checkNickname.equals("false")){
                    Toast.makeText(JoinActivity.this, "중복 확인을 진행해 주세요.", Toast.LENGTH_SHORT).show();
                }else if(userPwd.length()==0||checkEqualsPwd.equals("false")||phone.length()==0||name.length()==0){
                    Toast.makeText(JoinActivity.this, "필수 입력란을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }else{
                    try {
                        String joinResult;

                        ConnectJoinActivity task = new ConnectJoinActivity();
                        joinResult = task.execute(userId.toString(), userPwd.toString(), name.toString(), nickname.toString(), phone.toString()).get();

                        if (joinResult.equals("success")) {
                            finish();
                            Intent intent = new Intent(JoinActivity.this,
                                    LoginActivity.class);
                            startActivity(intent);
                            Toast.makeText(JoinActivity.this, "회원가입 되었습니다.", Toast.LENGTH_SHORT).show();
                        } else if(joinResult.equals("fail")) {
                            finish();
                            Toast.makeText(JoinActivity.this, "회원가입에 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(JoinActivity.this, joinResult, Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        Log.i("DBtest", "Error");
                    }
                }


            }
        });
    }
}
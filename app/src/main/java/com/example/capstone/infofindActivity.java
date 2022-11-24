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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class infofindActivity extends AppCompatActivity {

    LinearLayout id_findLayout, pwd_findLayout;
    TextView topText;
    RadioGroup selectGroup;
    RadioButton idFindBtn, pwdFindBtn;
    ImageButton backBtn;
    Button id_submitBtn, pwd_submitBtn;
    textboxJoin id_NameBox, id_PhoneBox, pwd_IdBox, pwd_PhoneBox;
    String[] id_Result, pwd_Result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infofind);

        backBtn = findViewById(R.id.backBtn);
        selectGroup = findViewById(R.id.selectGroup);
        topText = findViewById(R.id.topText);
        id_findLayout = findViewById(R.id.id_findLayout);
        pwd_findLayout = findViewById(R.id.pwd_findLayout);
        id_submitBtn = findViewById(R.id.id_submitBtn);
        pwd_submitBtn = findViewById(R.id.pwd_submitBtn);

        id_NameBox = findViewById(R.id.id_NameBox);
        id_PhoneBox = findViewById(R.id.id_PhoneBox);
        pwd_IdBox = findViewById(R.id.pwd_IdBox);
        idFindBtn = findViewById(R.id.idFindBtn);
        pwd_PhoneBox = findViewById(R.id.pwd_PhoneBox);

        pwdFindBtn = findViewById(R.id.pwdFindBtn);

        Intent intent = getIntent();
        int radio_checked = intent.getExtras().getInt("radio_checked");

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if(radio_checked == 1000513){
            pwdFindBtn.setChecked(true);
        }else{
            idFindBtn.setChecked(true);
        }

        selectGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.idFindBtn:
                        topText.setText("아이디 찾기");
                        id_findLayout.setVisibility(View.VISIBLE);
                        pwd_findLayout.setVisibility(View.GONE);
                        break;
                    case R.id.pwdFindBtn:
                        topText.setText("비밀번호 찾기");
                        id_findLayout.setVisibility(View.GONE);
                        pwd_findLayout.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        Editable id_name = id_NameBox.editText.getText();
        id_NameBox.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                id_NameBox.GuideTxt.setVisibility(View.GONE);
                id_NameBox.delBtn.setVisibility(View.VISIBLE);
                id_NameBox.ConfirmBtn.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(id_name.length() == 0){
                    id_NameBox.GuideTxt.setVisibility(View.VISIBLE);
                    id_NameBox.GuideTxt.setText("이름을 입력해주세요.");
                    id_NameBox.delBtn.setVisibility(View.GONE);
                    id_NameBox.setLayoutLine(R.drawable.roundborder_r4);
                    id_NameBox.ConfirmBtn.setVisibility(View.GONE);
                }else{
                    id_NameBox.GuideTxt.setVisibility(View.GONE);
                    id_NameBox.setLayoutLine(R.drawable.roundborder_g4);
                }
            }
        });

        id_NameBox.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id_name.clear();
            }
        });

        Editable id_phone = id_PhoneBox.editText.getText();
        id_PhoneBox.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                id_PhoneBox.GuideTxt.setVisibility(View.GONE);
                id_PhoneBox.delBtn.setVisibility(View.VISIBLE);
                id_PhoneBox.ConfirmBtn.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(id_phone.length() == 0){
                    id_PhoneBox.GuideTxt.setVisibility(View.VISIBLE);
                    id_PhoneBox.GuideTxt.setText("휴대폰 번호를 입력해주세요.");
                    id_PhoneBox.delBtn.setVisibility(View.GONE);
                    id_PhoneBox.setLayoutLine(R.drawable.roundborder_r4);
                    id_PhoneBox.ConfirmBtn.setVisibility(View.GONE);
                }else{
                    id_PhoneBox.GuideTxt.setVisibility(View.GONE);
                    id_PhoneBox.setLayoutLine(R.drawable.roundborder_g4);
                }
            }
        });

        id_PhoneBox.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id_phone.clear();
            }
        });

        id_submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String idFindResult;

                    ConnectIdFindActivity task = new ConnectIdFindActivity();
                    idFindResult = task.execute(id_name.toString(), id_phone.toString()).get();

                    id_Result = idFindResult.split("/");


                    if(id_Result[0].equals("success")){
                        finish();
                        Intent intent = new Intent(infofindActivity.this,
                                idresultActivity.class);
                        intent.putExtra("id", id_Result[1]);
                        intent.putExtra("regdate", id_Result[2]);
                        startActivity(intent);
                    }else if(id_Result[0].equals("fail")){
                        Toast.makeText(infofindActivity.this, "회원님의 정보와 일치하는 아이디가 없습니다", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(infofindActivity.this, idFindResult, Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    Log.i("DBtest", "Error");
                }

            }
        });

        Editable pwd_id = pwd_IdBox.editText.getText();
        pwd_IdBox.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                pwd_IdBox.GuideTxt.setVisibility(View.GONE);
                pwd_IdBox.delBtn.setVisibility(View.VISIBLE);
                pwd_IdBox.ConfirmBtn.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(pwd_id.length() == 0){
                    pwd_IdBox.GuideTxt.setVisibility(View.VISIBLE);
                    pwd_IdBox.GuideTxt.setText("아이디를 입력해주세요.");
                    pwd_IdBox.delBtn.setVisibility(View.GONE);
                    pwd_IdBox.setLayoutLine(R.drawable.roundborder_r4);
                    pwd_IdBox.ConfirmBtn.setVisibility(View.GONE);
                }else{
                    pwd_IdBox.GuideTxt.setVisibility(View.GONE);
                    pwd_IdBox.setLayoutLine(R.drawable.roundborder_g4);
                    pwd_IdBox.ConfirmBtn.setVisibility(View.GONE);
                }
            }
        });

        pwd_IdBox.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pwd_id.clear();
            }
        });

        Editable pwd_phone = pwd_PhoneBox.editText.getText();
        pwd_PhoneBox.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                pwd_PhoneBox.GuideTxt.setVisibility(View.GONE);
                pwd_PhoneBox.delBtn.setVisibility(View.VISIBLE);
                pwd_PhoneBox.ConfirmBtn.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(pwd_phone.length() == 0){
                    pwd_PhoneBox.GuideTxt.setVisibility(View.VISIBLE);
                    pwd_PhoneBox.GuideTxt.setText("휴대폰 번호를 입력해주세요.");
                    pwd_PhoneBox.delBtn.setVisibility(View.GONE);
                    pwd_PhoneBox.setLayoutLine(R.drawable.roundborder_r4);
                    pwd_PhoneBox.ConfirmBtn.setVisibility(View.GONE);
                }else{
                    pwd_PhoneBox.GuideTxt.setVisibility(View.GONE);
                    pwd_PhoneBox.setLayoutLine(R.drawable.roundborder_g4);
                    pwd_PhoneBox.ConfirmBtn.setVisibility(View.VISIBLE);
                }
            }
        });

        pwd_PhoneBox.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pwd_phone.clear();
            }
        });


        pwd_submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String pwdFindResult;

                    ConnectPwdFindActivity task = new ConnectPwdFindActivity();
                    pwdFindResult = task.execute(pwd_id.toString(), pwd_phone.toString()).get();

                    pwd_Result = pwdFindResult.split("/");

                    if(pwd_Result[0].equals("success")){
                        finish();
                        Intent intent = new Intent(infofindActivity.this,
                                pwdresultActivity.class);
                        intent.putExtra("id", pwd_Result[1].toString());
                        intent.putExtra("phone", pwd_Result[2].toString());
                        intent.putExtra("before_pwd", pwd_Result[3].toString());
                        startActivity(intent);
                    }else if(pwd_Result[0].equals("fail")){
                        Toast.makeText(infofindActivity.this, "회원님의 정보와 일치하는 정보가 없습니다", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(infofindActivity.this, pwdFindResult, Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    Log.i("DBtest", "Error");
                }
            }
        });
    }
}
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
import android.widget.TextView;
import android.widget.Toast;

public class EditPropileActivity extends AppCompatActivity {

    ImageButton backBtn, chechkBtn;
    Button idConfirmBtn2;
    textboxJoin nicknameBox, phoneBox;
    String checkNickname="false";
    TextView idTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_propile);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String Getnickname = intent.getStringExtra("nickname");
        String Getphone = intent.getStringExtra("phone");

        backBtn = (ImageButton) findViewById(R.id.backBtn);
        chechkBtn = (ImageButton) findViewById(R.id.checkBtn);
        idTxt = findViewById(R.id.idTxt);

        nicknameBox = findViewById(R.id.nicknameBox);
        Editable nickname = nicknameBox.editText.getText();

        if(id != null){
            idTxt.setText(id);
        }

        if(Getnickname != null){
            nicknameBox.editText.setHint(Getnickname);
        }


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
                        Toast.makeText(EditPropileActivity.this, nicknameCheckResult, Toast.LENGTH_SHORT).show();
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

        if(Getphone != null){
            phoneBox.editText.setHint(Getphone);
        }

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




        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        chechkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(nickname.length() == 0 && phone.length() != 0){
                    try {
                        String profileResult;

                        ConnectProfileChangeActivity task = new ConnectProfileChangeActivity();
                        profileResult = task.execute(id.toString(), Getnickname, phone.toString()).get();

                        if (profileResult.equals("success")) {
                            finish();
                            Intent intent = new Intent(EditPropileActivity.this,
                                    FragmenthomeActivity.class);
                            intent.putExtra("id", id);
                            intent.putExtra("checked", "mypage");
                            startActivity(intent);

                            Toast.makeText(EditPropileActivity.this, "프로필 수정 되었습니다.", Toast.LENGTH_SHORT).show();
                        } else if(profileResult.equals("fail")) {
                            finish();
                            Toast.makeText(EditPropileActivity.this, "프로필 수정에 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(EditPropileActivity.this, profileResult, Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        Log.i("DBtest", "Error");
                    }
                }else if(nickname.length() != 0 && phone.length() == 0){
                    if(checkNickname.equals("false")){
                        Toast.makeText(EditPropileActivity.this, "닉네임 중복 확인을 진행해주세요", Toast.LENGTH_SHORT).show();
                    }else{
                        try {
                            String profileResult;

                            ConnectProfileChangeActivity task = new ConnectProfileChangeActivity();
                            profileResult = task.execute(id.toString(), nickname.toString(), Getphone).get();

                            if (profileResult.equals("success")) {
                                finish();
                                Intent intent = new Intent(EditPropileActivity.this,
                                        FragmenthomeActivity.class);
                                intent.putExtra("id", id);
                                intent.putExtra("checked", "mypage");
                                startActivity(intent);

                                Toast.makeText(EditPropileActivity.this, "프로필 수정 되었습니다.", Toast.LENGTH_SHORT).show();
                            } else if(profileResult.equals("fail")) {
                                finish();
                                Toast.makeText(EditPropileActivity.this, "프로필 수정에 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(EditPropileActivity.this, profileResult, Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            Log.i("DBtest", "Error");
                        }
                    }
                }else if(nickname.length() != 0 && phone.length() != 0){

                    if(checkNickname.equals("false")){
                        Toast.makeText(EditPropileActivity.this, "닉네임 중복 확인을 진행해주세요", Toast.LENGTH_SHORT).show();
                    }else{
                        try {
                            String profileResult;

                            ConnectProfileChangeActivity task = new ConnectProfileChangeActivity();
                            profileResult = task.execute(id.toString(), nickname.toString(), phone.toString()).get();

                            if (profileResult.equals("success")) {
                                finish();
                                Intent intent = new Intent(EditPropileActivity.this,
                                        FragmenthomeActivity.class);
                                intent.putExtra("id", id);
                                intent.putExtra("checked", "mypage");
                                startActivity(intent);

                                Toast.makeText(EditPropileActivity.this, "프로필 수정 되었습니다.", Toast.LENGTH_SHORT).show();
                            } else if(profileResult.equals("fail")) {
                                finish();
                                Toast.makeText(EditPropileActivity.this, "프로필 수정에 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(EditPropileActivity.this, profileResult, Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            Log.i("DBtest", "Error");
                        }
                    }

                }
            }
        });


    }
}
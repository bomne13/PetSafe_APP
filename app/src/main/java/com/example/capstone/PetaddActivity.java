package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class PetaddActivity extends AppCompatActivity {

    TextView tvDate;
    DatePickerDialog.OnDateSetListener setListener;
    ImageButton backBtn, chechkBtn;
    textboxJoin petNameBox, petBreedBox, memoBox;
    RadioGroup selectGroup;
    RadioButton maleBtn, femaleBtn;
    String date;
    String petsex = "", petimg = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petadd);

        tvDate = findViewById(R.id.tv_date);
        backBtn = (ImageButton) findViewById(R.id.backBtn);
        chechkBtn = (ImageButton) findViewById(R.id.checkBtn);

        petNameBox = findViewById(R.id.petNameBox);
        Editable name = petNameBox.editText.getText();

        petBreedBox = findViewById(R.id.petBreedBox);
        Editable pettype = petBreedBox.editText.getText();

        memoBox = findViewById(R.id.memoBox);
        Editable memo = memoBox.editText.getText();

        selectGroup = findViewById(R.id.selectGroup);
        maleBtn = findViewById(R.id.maleBtn);
        femaleBtn = findViewById(R.id.femaleBtn);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        Intent intent = getIntent();
        String userId = intent.getStringExtra("id");


        petNameBox.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                petNameBox.GuideTxt.setVisibility(View.GONE);
                petNameBox.delBtn.setVisibility(View.VISIBLE);
                petNameBox.ConfirmBtn.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(name.length() == 0){
                    petNameBox.GuideTxt.setVisibility(View.VISIBLE);
                    petNameBox.GuideTxt.setText("이름을 입력해주세요.");
                    petNameBox.delBtn.setVisibility(View.GONE);
                    petNameBox.setLayoutLine(R.drawable.roundborder_r4);
                    petNameBox.ConfirmBtn.setVisibility(View.GONE);
                }else{
                    petNameBox.GuideTxt.setVisibility(View.GONE);
                    petNameBox.setLayoutLine(R.drawable.roundborder_g4);
                }
            }
        });

        petNameBox.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.clear();
            }
        });



        petBreedBox.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                petBreedBox.GuideTxt.setVisibility(View.GONE);
                petBreedBox.delBtn.setVisibility(View.VISIBLE);
                petBreedBox.ConfirmBtn.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(pettype.length() == 0){
                    petBreedBox.GuideTxt.setVisibility(View.VISIBLE);
                    petBreedBox.GuideTxt.setText("반려동물의 종을 입력해주세요.");
                    petBreedBox.delBtn.setVisibility(View.GONE);
                    petBreedBox.setLayoutLine(R.drawable.roundborder_r4);
                    petBreedBox.ConfirmBtn.setVisibility(View.GONE);
                }else{
                    petBreedBox.GuideTxt.setVisibility(View.GONE);
                    petBreedBox.setLayoutLine(R.drawable.roundborder_g4);
                }
            }
        });

        petBreedBox.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pettype.clear();
            }
        });

        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        PetaddActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        , setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                month = month + 1;
                date = year + "-" + month + "-" + day;
                tvDate.setText(date);
            }
        };

        selectGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.maleBtn:
                        petsex = "m";
                        break;
                    case R.id.femaleBtn:
                        petsex = "f";
                        break;
                }
            }
        });


        memoBox.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                memoBox.GuideTxt.setVisibility(View.GONE);
                memoBox.delBtn.setVisibility(View.VISIBLE);
                memoBox.ConfirmBtn.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        memoBox.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                memo.clear();
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
                if(name.length()==0||pettype.length()==0|| date.length() == 0 ||petsex.length()==0){
                    Toast.makeText(PetaddActivity.this, "필수 입력란을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }else{
                    try {
                        String petAddResult;

                        ConnectPetaddActivity task = new ConnectPetaddActivity();

                        petAddResult = task.execute(userId, name.toString(), date, pettype.toString(), memo.toString(), petsex, petimg).get();

                        if (petAddResult.equals("success")) {
                            finish();
                            Intent intent = new Intent(PetaddActivity.this,
                                    FragmenthomeActivity.class);
                            intent.putExtra("id", userId);
                            intent.putExtra("checked", "mypage");
                            startActivity(intent);
                            Toast.makeText(PetaddActivity.this, "반려동물이 추가 되었습니다.", Toast.LENGTH_SHORT).show();
                        } else if(petAddResult.equals("fail")) {
                            finish();
                            Toast.makeText(PetaddActivity.this, "반려동물 추가에 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(PetaddActivity.this, petAddResult, Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        Log.i("DBtest", "Error");
                    }
                }
            }
        });
    }
}
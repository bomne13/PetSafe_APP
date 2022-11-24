package com.example.capstone;

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

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class PeteditActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_petedit);

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
        String pno = intent.getStringExtra("pno");


        ArrayList<String[]> array = new ArrayList<>();

        try{
            String Petresult;
            ConnectPetActivity task = new ConnectPetActivity();
            Petresult = task.execute(userId, pno).get();

            //[0] name : [1] birthday : [2] type : [3] memo : [4] sex ; [5] img
            String[] sp = Petresult.split("/");
            array.add(sp);

            petNameBox.editText.setHint(sp[0]);
            tvDate.setText(sp[1]);
            petBreedBox.editText.setHint(sp[2]);
            memoBox.editText.setHint(sp[3]);
            if(sp[4].equals("m")){
                maleBtn.setChecked(true);
                petsex="m";
            }else{
                femaleBtn.setChecked(true);
                petsex="f";
            }


        }catch (Exception e){
            Log.i("DBtest", "Error");
        }


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
                        PeteditActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth
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
                String petnameInput, pettypeInput, petmemoInput, petimgInput, petBirthdayInput;
                //a(0)[0] name : a(0)[1] birthday : a(0)[2] type : a(0)[3] memo : a(0)[4] sex ; a(0)[5] img

                if(name.length()==0){
                    petnameInput = array.get(0)[0];
                }else{
                    petnameInput = name.toString();
                }

                if(pettype.length() == 0){
                    pettypeInput = array.get(0)[2];
                }else{
                    pettypeInput = pettype.toString();
                }

                if(memo.length() == 0){
                    petmemoInput = array.get(0)[3];
                }else{
                    petmemoInput = memo.toString();
                }

                if(petimg.length() == 0){
                    petimgInput = array.get(0)[5];
                }else{
                    petimgInput = petimg;
                }

                if (date == null){
                    petBirthdayInput = array.get(0)[1];
                }else{
                    petBirthdayInput = date;
                }


                try {
                    String petChangeesult;
                    ConnectPetChangetActivity task = new ConnectPetChangetActivity();

                    petChangeesult = task.execute(userId, pno, petnameInput, petBirthdayInput, pettypeInput, petmemoInput, petsex, petimgInput).get();

                    if (petChangeesult.equals("success")) {
                        finish();
                        Intent intent = new Intent(PeteditActivity.this,
                                FragmenthomeActivity.class);
                        intent.putExtra("id", userId);
                        intent.putExtra("checked", "mypage");
                        startActivity(intent);
                        Toast.makeText(PeteditActivity.this, "반려동물의 정보가 수정 되었습니다.", Toast.LENGTH_SHORT).show();
                    } else if(petChangeesult.equals("fail")) {
                        finish();
                        Toast.makeText(PeteditActivity.this, "반려동물 정보 수정에 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(PeteditActivity.this, petChangeesult, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Log.i("DBtest", "Error");
                }

            }
        });
    }
}
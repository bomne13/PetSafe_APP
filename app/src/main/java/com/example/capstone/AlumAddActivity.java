package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.motion.utils.ViewState;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import petrov.kristiyan.colorpicker.ColorPicker;

public class AlumAddActivity extends AppCompatActivity {

    TextView date_text;
    TextView time_text;
    TextView selecteColorTxt;
    TextView alumName;
    EditText cmemo;
    int t2Hour,t2Minute;
    ImageButton backBtn, text_color_choice, checkBtn;
    DatePickerDialog.OnDateSetListener setListener;

    private String regDate = "";
    private String timeResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alum_add);

        Intent intent = getIntent();
        String userId = intent.getStringExtra("userId");

        backBtn=(ImageButton)findViewById(R.id.backBtn);
        time_text=findViewById(R.id.time_text);
        date_text=findViewById(R.id.date_text);
        text_color_choice = findViewById(R.id.text_color_choice);
        selecteColorTxt = findViewById(R.id.selecteColorTxt);
        checkBtn = findViewById(R.id.checkBtn);
        alumName = findViewById(R.id.alumName);
        cmemo = findViewById(R.id.cmemo);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        text_color_choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openColorPicker();
            }
        });


        time_text.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        AlumAddActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                //Initialize hour and minute
                                t2Hour=hourOfDay;
                                t2Minute=minute;
                                //Store hour and minute in string
                                String time= t2Hour+":"+t2Minute;
                                timeResult = time;
                                SimpleDateFormat f24Hours=new SimpleDateFormat("HH:mm");
                                try {
                                    Date date=f24Hours.parse(time);
                                    SimpleDateFormat f12Hours= new SimpleDateFormat("aa hh:mm");
                                    time_text.setText(f12Hours.format(date));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        },12,0,false

                );

                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(t2Hour,t2Minute);
                timePickerDialog.show();
            }
        });

        Calendar calendar= Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        date_text.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        AlumAddActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        ,setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();

            }

        });

        setListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                String date=year+"/"+month+"/"+day;
                date_text.setText(date);
            }
        };


        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regDate = date_text.getText().toString() + " " + timeResult;
                if(alumName.getText().length() == 0 || date_text.getText().length() == 0 || time_text.getText().length() == 0){
                    Toast.makeText(AlumAddActivity.this, "필수 입력란을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }else{
                    try {
                        String alumAddResult;

                        ConnectAlumAddActivity task = new ConnectAlumAddActivity();
                        alumAddResult = task.execute(userId, alumName.getText().toString(), regDate, cmemo.getText().toString(), selecteColorTxt.getText().toString()).get();

                        if (alumAddResult.equals("success")) {
                            Toast.makeText(AlumAddActivity.this, "알람이 추가 되었습니다.", Toast.LENGTH_SHORT).show();
                            finish();
                            Intent intent = new Intent(AlumAddActivity.this,
                                    FragmenthomeActivity.class);
                            intent.putExtra("id", userId);
                            intent.putExtra("checked", "alum");
                            startActivity(intent);
                        } else if(alumAddResult.equals("fail")) {
                            Toast.makeText(AlumAddActivity.this, "알람 추가에 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(AlumAddActivity.this, alumAddResult, Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        Log.i("DBtest", "Error");
                    }
                }
            }
        });



    }

    public void openColorPicker() {
        final ColorPicker colorPicker = new ColorPicker(this);  // ColorPicker 객체 생성
        ArrayList<String> colors = new ArrayList<>();  // Color 넣어줄 list

        colors.add("#ffab91");
        colors.add("#F48FB1");
        colors.add("#ce93d8");
        colors.add("#b39ddb");
        colors.add("#9fa8da");
        colors.add("#90caf9");
        colors.add("#81d4fa");
        colors.add("#80deea");
        colors.add("#80cbc4");
        colors.add("#c5e1a5");
        colors.add("#e6ee9c");
        colors.add("#FFE699");
        colors.add("#ffe082");
        colors.add("#ffcc80");
        colors.add("#bcaaa4");

        colorPicker.setColors(colors)  // 만들어둔 list 적용
                .setColumns(5)  // 5열로 설정
                .setRoundColorButton(true)  // 원형 버튼으로 설정
                .setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {

                    @Override
                    public void onCancel() {
                        // Cancel 버튼 클릭 시 이벤트
                    }

                    @Override
                    public void onChooseColor(int position, int color) {
                        // OK 버튼 클릭 시 이벤트
                        selecteColorTxt.setText(colors.get(position).toString());
                        text_color_choice.setBackgroundTintList(ColorStateList.valueOf(color));
                        text_color_choice.setBackgroundTintMode(PorterDuff.Mode.MULTIPLY);
                    }
                }).show();  // dialog 생성
    }

    }

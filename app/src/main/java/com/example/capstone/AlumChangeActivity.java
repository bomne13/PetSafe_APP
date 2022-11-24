package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import petrov.kristiyan.colorpicker.ColorPicker;

public class AlumChangeActivity extends AppCompatActivity {

    TextView date_text, time_text, selecteColorTxt;
    EditText memoTxt, alumName;
    int t2Hour,t2Minute;
    ImageButton backBtn, text_color_choice, checkBtn;
    LinearLayout delLinear;
    DatePickerDialog.OnDateSetListener setListener;
    Dialog dialog01;
    private String regDate = "";
    private String timeResult="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alum_change);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String cno = intent.getStringExtra("cno");

        backBtn=(ImageButton)findViewById(R.id.backBtn);
        time_text=findViewById(R.id.time_text);
        date_text=findViewById(R.id.date_text);
        memoTxt = findViewById(R.id.memoTxt);
        alumName = findViewById(R.id.alumName);
        text_color_choice=findViewById(R.id.text_color_choice);
        selecteColorTxt =findViewById(R.id.selecteColorTxt);
        delLinear = findViewById(R.id.delLinear);
        checkBtn = findViewById(R.id.checkBtn);

        dialog01 = new Dialog(AlumChangeActivity.this);
        dialog01.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog01.setContentView(R.layout.dialog_alumlayout);
        dialog01.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ArrayList<String[]> array = new ArrayList<>();

        try{
            String alumResult;
            ConnectAlumActivity task = new ConnectAlumActivity();
            alumResult = task.execute(id, cno).get().trim();

            if(alumResult.length() != 0){
                String[] sp = alumResult.split("㏂");

                for (int j = 0; j < sp.length; j++) {
                    String[] ssp = sp[j].split("`");
                    array.add(ssp);
                }
            }
        }catch (Exception e){
            Log.i("DBtest", "Error");
        }

        SimpleDateFormat yearDays = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat MonthDays = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat f24Hours= new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat f12Hours= new SimpleDateFormat("aa hh:mm");

        if(array.size()>0){
            //[0] Ctitle : [1] RegDate : [2] Cmemo : [3] Rgb : [4] Onoff
            String[] date = array.get(0)[1].split(" ");
            String mont1 = "";
            String date2 ="";

            try {
                Date date1 = f24Hours.parse(date[1]);
                date2 = f12Hours.format(date1);

                Date month1 = yearDays.parse(date[0]);
                mont1 = MonthDays.format(month1);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            int color = Color.parseColor(array.get(0)[3]);

            selecteColorTxt.setText(array.get(0)[3]);
            text_color_choice.setBackgroundTintList(ColorStateList.valueOf(color));
            text_color_choice.setBackgroundTintMode(PorterDuff.Mode.MULTIPLY);
            alumName.setText(array.get(0)[0]);
            date_text.setText(mont1);
            time_text.setText(date2);
            memoTxt.setText(array.get(0)[2]);

        }

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
                        AlumChangeActivity.this,
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
                        AlumChangeActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        ,setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                datePickerDialog.show();
            }

        });

        setListener = new DatePickerDialog.OnDateSetListener() {
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
                if(timeResult.length() == 0){
                    String timeT="";
                    try {
                        Date date3 = f12Hours.parse(time_text.getText().toString());
                        timeT = f24Hours.format(date3);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    timeResult = timeT;
                }

                regDate = date_text.getText().toString() + " " + timeResult;
                try {
                    String AlumChangeResult;
                    ConnectAlumChangeActivity task = new ConnectAlumChangeActivity();
                    AlumChangeResult = task.execute(id, cno, alumName.getText().toString(), regDate, memoTxt.getText().toString(), selecteColorTxt.getText().toString()).get();

                    if (AlumChangeResult.equals("success")) {
                        finish();
                        Intent intent = new Intent(AlumChangeActivity.this,
                                PostActivity.class);
                        intent.putExtra("id", id);
                        intent.putExtra("cno", cno);
                        startActivity(intent);
                        Toast.makeText(AlumChangeActivity.this, "알람이 수정 되었습니다.", Toast.LENGTH_SHORT).show();
                    } else if (AlumChangeResult.equals("fail")) {
                        finish();
                        Toast.makeText(AlumChangeActivity.this, "알람 수정에 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AlumChangeActivity.this, AlumChangeResult, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.i("DBtest", "Error");
                }
            }
        });

        delLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog01.show();

                Button noBtn = dialog01.findViewById(R.id.noBtn);
                noBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog01.dismiss(); //닫기
                    }
                });

                Button DelBtn = dialog01.findViewById(R.id.DelBtn);
                DelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try{
                            String delResult;
                            ConnectAlumDelActivity task = new ConnectAlumDelActivity();
                            delResult = task.execute(cno).get();

                            if (delResult.equals("success")) {
                                finish();
                                Intent intent = new Intent(AlumChangeActivity.this,
                                        FragmenthomeActivity.class);
                                intent.putExtra("id", id);
                                intent.putExtra("checked", "community");
                                startActivity(intent);
                            } else if (delResult.equals("fail")) {
                                Toast.makeText(AlumChangeActivity.this, "게시글 삭제에 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(AlumChangeActivity.this, delResult, Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            Log.i("DBtest", "Error");
                        }
                    }
                });
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
package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class ObjectSet extends AppCompatActivity {

    ImageButton back_chart, checkBtn;
    TextView now_walk;
    NumberPicker picker1;

    String[] walkResult;

    int minvalue = 1000;
    int maxvalue = 50000;
    int step = 100;	//선택가능 값들의 간격

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.object_set);

        Intent intent = getIntent();
        String userId = intent.getStringExtra("userId");
        String obj = intent.getStringExtra("obj");

        back_chart = findViewById(R.id.back_chart);
        checkBtn = findViewById(R.id.checkBtn);

        picker1 = (NumberPicker)findViewById(R.id.picker1);
        picker1.setMinValue(0);
        picker1.setMaxValue(maxvalue);
        picker1.setWrapSelectorWheel(false);

        String[] myValues = getArrayWithSteps(minvalue, maxvalue, step);

        picker1.setValue((Integer.parseInt(obj) - minvalue) / step);
        picker1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        picker1.setDisplayedValues(myValues);

        back_chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                picker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        now_walk.setText(newVal);
                    }
                });

            }
        });

        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               try{
                    String result;
                    ConnectWalkobChangejActivity task = new ConnectWalkobChangejActivity();
                    result = task.execute(userId, String.valueOf(((picker1.getValue()*step)+minvalue))).get();

                    if(result.equals("success")){
                        finish();
                        Intent intent = new Intent(ObjectSet.this, FragmenthomeActivity.class);
                        intent.putExtra("id", userId);
                        intent.putExtra("checked", "exercise");
                        startActivity(intent);
                    }else if(result.equals("fail")){
                        Toast.makeText(ObjectSet.this, "DB 연동 실패", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(ObjectSet.this, result, Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    Toast.makeText(ObjectSet.this, "에러 : " + e, Toast.LENGTH_SHORT).show();
                    Log.i("DBtest", "Error");
                }
            }
        });
    }
    public String[] getArrayWithSteps(int min, int max, int step) {

        int number_of_array = (max - min) / step + 1;

        String[] result = new String[number_of_array];

        for (int i = 0; i < number_of_array; i++) {
            result[i] = String.valueOf(min + step * i);
        }
        return result;
    }
}
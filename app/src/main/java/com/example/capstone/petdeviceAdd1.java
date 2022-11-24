package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

public class petdeviceAdd1 extends AppCompatActivity {

    CheckBox confirmCheck;
    Button confirmBtn;
    ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petdevice_add1);

        confirmCheck = (CheckBox) findViewById(R.id.confirmCheck);
        confirmBtn = (Button) findViewById(R.id.confirmBtn);
        backBtn = findViewById(R.id.backBtn);


        if(confirmCheck.isChecked()){
            /*
            confirmBtn.setEnabled(true);
            confirmBtn.setBackgroundResource(R.drawable.roundbtn_y4);*/
            Toast.makeText(getApplicationContext(),"checked", Toast.LENGTH_SHORT);
        }else{
            /*
            confirmBtn.setEnabled(false);
            confirmBtn.setBackgroundResource(R.drawable.roundbtn_g4);*/
            Toast.makeText(getApplicationContext(),"no check", Toast.LENGTH_SHORT);



        }

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(petdeviceAdd1.this,
                        Adddevice.class);
                startActivity(intent);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
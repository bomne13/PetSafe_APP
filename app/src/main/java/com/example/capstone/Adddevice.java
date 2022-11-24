package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class Adddevice extends AppCompatActivity {

    LinearLayout homeBtn, petBtn;
    ImageButton backBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adddevice);

        homeBtn = (LinearLayout) findViewById(R.id.homeBtn);
        petBtn = (LinearLayout) findViewById(R.id.petBtn);
        backBtn = (ImageButton)findViewById(R.id.backBtn);

        petBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Adddevice.this,
                        petdeviceAdd1.class);
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
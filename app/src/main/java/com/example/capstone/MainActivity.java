package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImageView image;
    ImageButton imgBtn1, imgBtn2,imgBtn3,imgBtn4, naverBtn, kakaoBtn;
    Button loginBtn, joinBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        loginBtn = (Button) findViewById(R.id.loginBtn);
        joinBtn = (Button)findViewById(R.id.joinBtn);
//        naverBtn = (ImageButton) findViewById(R.id.naverBtn);
//        kakaoBtn = (ImageButton) findViewById(R.id.kakaoBtn);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,
                        LoginActivity.class);
                startActivity(intent);
            }
        });

        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,
                        JoinActivity.class);
                startActivity(intent);
            }
        });



    }
}
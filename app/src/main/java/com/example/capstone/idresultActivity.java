package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class idresultActivity extends AppCompatActivity {

    ImageButton backBtn;
    Button pwdSearchBtn, loginBtn;
    TextView useridTxt, regdateTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idresult);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String regdate = intent.getStringExtra("regdate");

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        useridTxt = findViewById(R.id.useridTxt);
        useridTxt.setText(id);


        regdateTxt = findViewById(R.id.regdateTxt);
        regdateTxt.setText(regdate);

        pwdSearchBtn = findViewById(R.id.pwdSearchBtn);
        pwdSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent idresult = new Intent(idresultActivity.this,
                        infofindActivity.class);
                idresult.putExtra("radio_checked",1000513);
                startActivity(idresult);
            }
        });

        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(idresultActivity.this,
                        LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}
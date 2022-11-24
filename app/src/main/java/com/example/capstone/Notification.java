package com.example.capstone;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class Notification extends AppCompatActivity{

    ImageButton backBtn;

    private List<AlarmData> alarmList;
    private AlarmAdapter alarmAdapter;
    private RecyclerView alarm_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        backBtn = (ImageButton)findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        alarmList = new ArrayList<>();
        alarmList.add(new AlarmData(R.drawable.ic_imgicon_circle, "기기명1", "2022-08-07 20:45"));

        alarmAdapter = new AlarmAdapter(alarmList);
        alarm_view.setAdapter(alarmAdapter);
        alarm_view.setLayoutManager(new LinearLayoutManager(this));
    }

}
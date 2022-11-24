package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    ImageButton backBtn;




    private List<SearchbestData> searchbestList;
    private SearchbestAdapter searchbestAdapter;
    private RecyclerView searchbest_view;

    private List<ResearchData> researchList;
    private ResearchAdapter researchAdapter;
    private RecyclerView research_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        backBtn = (ImageButton) findViewById(R.id.backBtn);



        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




        searchbest_view = (RecyclerView) findViewById(R.id.searchbest_view);
        research_view = (RecyclerView) findViewById(R.id.research_view);
        //LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);





        searchbestList = new ArrayList<>();
        searchbestList.add(new SearchbestData("강아지"));
        searchbestList.add(new SearchbestData("고양이"));
        searchbestList.add(new SearchbestData("산책"));
        searchbestList.add(new SearchbestData("강아지 간식"));
        searchbestList.add(new SearchbestData("고양이 간식"));
        searchbestList.add(new SearchbestData("사료추천"));

        researchList = new ArrayList<>();
        researchList.add(new ResearchData("이동장 추천1"));
        researchList.add(new ResearchData("이동장 추천2"));
        researchList.add(new ResearchData("이동장 추천3"));
        researchList.add(new ResearchData("이동장 추천4"));







        searchbestAdapter = new SearchbestAdapter(searchbestList);
        searchbest_view.setAdapter(searchbestAdapter);
        searchbest_view.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        researchAdapter = new ResearchAdapter(researchList);
        research_view.setAdapter(researchAdapter);
        research_view.setLayoutManager(new GridLayoutManager(this, 2));






    }
}
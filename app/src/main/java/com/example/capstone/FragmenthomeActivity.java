package com.example.capstone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentResultListener;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationBarView;

public class FragmenthomeActivity extends AppCompatActivity {

    HomeFragment homeFragment;
    AlumFragment alumFragment;
    CommunityFragment communityFragment;
    ExerciseFragment exerciseFragment;
    MypageFragment mypageFragment;
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragmenthome);

        homeFragment = new HomeFragment();
        alumFragment = new AlumFragment();
        communityFragment = new CommunityFragment();
        exerciseFragment = new ExerciseFragment();
        mypageFragment = new MypageFragment();

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String checked = intent.getStringExtra("checked");

        Bundle bundle = new Bundle();
        bundle.putString("userId", id);

        AlumFragment alumFragment = new AlumFragment();
        alumFragment.setArguments(bundle);

        ExerciseFragment exerciseFragment = new ExerciseFragment();
        exerciseFragment.setArguments(bundle);

        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(bundle);

        CommunityFragment communityFragment = new CommunityFragment();
        communityFragment.setArguments(bundle);

        MypageFragment mypageFragment = new MypageFragment();
        mypageFragment.setArguments(bundle);


        NavigationBarView navigationBarView = findViewById(R.id.bottom_nav);
        menu = navigationBarView.getMenu();

        if(checked.equals("alum")){
            getSupportFragmentManager().beginTransaction().replace(R.id.container, alumFragment).commit();
            navigationBarView.setSelectedItemId(R.id.alum);
        }else if(checked.equals("exercise")){
            getSupportFragmentManager().beginTransaction().replace(R.id.container, exerciseFragment).commit();
            navigationBarView.setSelectedItemId(R.id.exercise);
        }else if(checked.equals("home")){
            getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
            navigationBarView.setSelectedItemId(R.id.home);
        }else if(checked.equals("community")){
            getSupportFragmentManager().beginTransaction().replace(R.id.container, communityFragment).commit();
            navigationBarView.setSelectedItemId(R.id.community);
        }else if(checked.equals("mypage")){
            getSupportFragmentManager().beginTransaction().replace(R.id.container, mypageFragment).commit();
            navigationBarView.setSelectedItemId(R.id.mypage);
        }

        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.alum:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, alumFragment).commit();
                        item.setIcon(R.drawable.ic_calendar_f);
                        menu.findItem(R.id.exercise).setIcon(R.drawable.ic_exerciseicon_nf);
                        menu.findItem(R.id.home).setIcon(R.drawable.ic_homeicon_nf);
                        menu.findItem(R.id.community).setIcon(R.drawable.ic_peticon_nf);
                        menu.findItem(R.id.mypage).setIcon(R.drawable.ic_mypage_nf);
                        return true;
                    case R.id.exercise:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, exerciseFragment).commit();
                        item.setIcon(R.drawable.ic_exerciseicon_f);
                        menu.findItem(R.id.alum).setIcon(R.drawable.ic_calendar_nf);
                        menu.findItem(R.id.home).setIcon(R.drawable.ic_homeicon_nf);
                        menu.findItem(R.id.community).setIcon(R.drawable.ic_peticon_nf);
                        menu.findItem(R.id.mypage).setIcon(R.drawable.ic_mypage_nf);
                        return true;
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                        item.setIcon(R.drawable.ic_homeicon_f);
                        menu.findItem(R.id.alum).setIcon(R.drawable.ic_calendar_nf);
                        menu.findItem(R.id.exercise).setIcon(R.drawable.ic_exerciseicon_nf);
                        menu.findItem(R.id.community).setIcon(R.drawable.ic_peticon_nf);
                        menu.findItem(R.id.mypage).setIcon(R.drawable.ic_mypage_nf);
                        return true;
                    case R.id.community:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, communityFragment).commit();
                        item.setIcon(R.drawable.ic_peticon_f);
                        menu.findItem(R.id.alum).setIcon(R.drawable.ic_calendar_nf);
                        menu.findItem(R.id.exercise).setIcon(R.drawable.ic_exerciseicon_nf);
                        menu.findItem(R.id.home).setIcon(R.drawable.ic_homeicon_nf);
                        menu.findItem(R.id.mypage).setIcon(R.drawable.ic_mypage_nf);
                        return true;
                    case R.id.mypage:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, mypageFragment).commit();
                        item.setIcon(R.drawable.ic_mypage_f);
                        menu.findItem(R.id.alum).setIcon(R.drawable.ic_calendar_nf);
                        menu.findItem(R.id.exercise).setIcon(R.drawable.ic_exerciseicon_nf);
                        menu.findItem(R.id.home).setIcon(R.drawable.ic_homeicon_nf);
                        menu.findItem(R.id.community).setIcon(R.drawable.ic_peticon_nf);
                        return true;
                }return false;

            }
        });
    }
}
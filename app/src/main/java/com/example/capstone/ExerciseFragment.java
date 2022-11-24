package com.example.capstone;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

import java.util.ArrayList;


public class ExerciseFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    String userId;
    String[] walkResult;
    int obj;
    int weekAvg;
    int monthAvg;

    LinearLayout walkLayout;
    TextView now_walk, objective_walk, termTxt, walkAvgTxt;
    RadioGroup selectGroup;
    RadioButton weekBtn, monthBtn;
    ProgressBar progressBar;


    public ExerciseFragment() {
        // Required empty public constructor
    }


    public static ExerciseFragment newInstance(String param1, String param2) {
        ExerciseFragment fragment = new ExerciseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_exercise, container, false);
        walkLayout = rootView.findViewById(R.id.walkLayout);
        objective_walk = rootView.findViewById(R.id.objective_walk);
        termTxt = rootView.findViewById(R.id.termTxt);
        walkAvgTxt = rootView.findViewById(R.id.walkAvgTxt);
        selectGroup = rootView.findViewById(R.id.selectGroup);
        weekBtn = rootView.findViewById(R.id.weekBtn);
        monthBtn = rootView.findViewById(R.id.monthBtn);
        now_walk = rootView.findViewById(R.id.now_walk);
        progressBar = rootView.findViewById(R.id.progressBar);

        if(getArguments() != null){
            userId = getArguments().getString("userId");
        }

        //오늘 걸음수
        try{
            String TodayResult;
            ConnectWalkTodayActivity task = new ConnectWalkTodayActivity();
            TodayResult = task.execute(userId).get().trim();

            if(TodayResult.length() != 0){
                now_walk.setText(String.valueOf(TodayResult));
                progressBar.setProgress(Integer.valueOf(TodayResult));
            }

        }catch (Exception e){
            Log.i("DBtest", "Error");
        }

        //목표값
        try{
            String ObjResult;
            ConnectWalkobjActivity task = new ConnectWalkobjActivity();
            ObjResult = task.execute(userId).get();

            walkResult = ObjResult.split("/");

            if(walkResult[0].equals("success")){
                objective_walk.setText(walkResult[1]);
                obj = Integer.parseInt(walkResult[1]);
                progressBar.setMax(obj);
            }else if(walkResult[0].equals("fail")){
                Toast.makeText(getActivity(),"DB 연동 실패",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getActivity(),ObjResult,Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e){
            Log.i("DBtest", "Error");
        }



        walkLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),
                        ObjectSet.class);
                intent.putExtra("userId", userId);
                intent.putExtra("obj", String.valueOf(obj));
                startActivity(intent);
            }
        });

        //기본 차트 값
        BarChart mBarChart = rootView.findViewById(R.id.barchart);
        ArrayList<String[]> array = new ArrayList<>();
        try{
            String weekResult;
            ConnectWalkWeekActivity task = new ConnectWalkWeekActivity();
            weekResult = task.execute(userId).get().trim();

            if(weekResult.length() != 0){
                String[] sp = weekResult.split("㏂");

                for (int j = 0; j < sp.length; j++) {
                    String[] ssp = sp[j].split("`");
                    array.add(ssp);
                }
            }

        }catch (Exception e){
            Log.i("DBtest", "Error");
        }
        if(array.size()>0){
            for (int i = 0; i < array.size(); i++) {
                mBarChart.addBar(new BarModel(array.get(i)[0], Integer.parseInt(array.get(i)[1]), R.color.mainColor));
                weekAvg += Integer.parseInt(array.get(i)[1]);
            }
            mBarChart.startAnimation();
            termTxt.setText(array.get(0)[0] + " - " + array.get(6)[0]);
            walkAvgTxt.setText(String.valueOf(weekAvg/7));
        }

        //radio 버튼에 따른 차트 값 변경
        selectGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.weekBtn:
                        mBarChart.clearChart();
                        ArrayList<String[]> array2 = new ArrayList<>();
                        try{
                            String weekResult;
                            ConnectWalkWeekActivity task = new ConnectWalkWeekActivity();
                            weekResult = task.execute(userId).get().trim();

                            if(weekResult.length() != 0){
                                String[] sp = weekResult.split("㏂");

                                for (int j = 0; j < sp.length; j++) {
                                    String[] ssp = sp[j].split("`");
                                    array2.add(ssp);
                                }
                            }

                        }catch (Exception e){
                            Log.i("DBtest", "Error");
                        }

                        if(array.size()>0){
                            for (int i = 0; i < array2.size(); i++) {
                                mBarChart.addBar(new BarModel(array2.get(i)[0], Integer.parseInt(array.get(i)[1]), R.color.mainColor));
                                weekAvg += Integer.parseInt(array2.get(i)[1]);
                            }
                            mBarChart.startAnimation();
                            termTxt.setText(array2.get(0)[0] + " - " + array2.get(6)[0]);
                            walkAvgTxt.setText(String.valueOf(weekAvg/7));
                        }
                        break;
                    case R.id.monthBtn:
                        mBarChart.clearChart();
                        ArrayList<String[]> array1 = new ArrayList<>();
                        try{
                            String monthResult;
                            ConnectWalkMonthActivity task = new ConnectWalkMonthActivity();
                            monthResult = task.execute(userId).get().trim();

                            if(monthResult.length() != 0){
                                String[] sp = monthResult.split("㏂");

                                for (int j = 0; j < sp.length; j++) {
                                    String[] ssp = sp[j].split("`");
                                    array1.add(ssp);
                                }
                            }

                        }catch (Exception e){
                            Log.i("DBtest", "Error");
                        }

                        if(array1.size()>0){
                            for (int i = 0; i < array1.size(); i++) {
                                mBarChart.addBar(new BarModel(array1.get(i)[0], Integer.parseInt(array1.get(i)[1]), R.color.mainColor));
                                monthAvg += Integer.parseInt(array1.get(i)[1]);
                            }
                            mBarChart.startAnimation();
                            termTxt.setText(array1.get(0)[0] + " - " + array1.get(29)[0]);
                            walkAvgTxt.setText(String.valueOf(monthAvg/30));
                        }
                }
            }
        });



        return rootView;
    }
}
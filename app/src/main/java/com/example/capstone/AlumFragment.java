package com.example.capstone;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class AlumFragment extends Fragment implements AlumAdapter.OnListItemSelectedInterface, AlumAdapter.OnListItemSwitchInterface{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    TextView noalum_txt, selectedDate;
    ImageButton alumAddBtn;
    CalendarView calendarView;

    private List<AlumData> alumList;
    private AlumAdapter alumAdapter;
    private RecyclerView alum_view;

    static int alum_density;
    private String viewDate="";

    private String userId;

    private boolean firstChecked=true;

    public AlumFragment() {
        // Required empty public constructor
    }

    public static AlumFragment newInstance(String param1, String param2) {
        AlumFragment fragment = new AlumFragment();
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

        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_alum, container, false);

        calendarView = rootView.findViewById(R.id.calendarView);
        selectedDate = rootView.findViewById(R.id.selectedDate);

        if(getArguments() != null){
            userId = getArguments().getString("userId");
        }

        Date dateNow = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());

        alumAddBtn = rootView.findViewById(R.id.alumAddBtn);
        alum_density = (int)getResources().getDisplayMetrics().density;
        alum_view = rootView.findViewById(R.id.alum_view);
        noalum_txt = rootView.findViewById(R.id.noalum_txt);

        alumList = new ArrayList<>();
        ArrayList<String[]> array = new ArrayList<>();

        if(viewDate.length() ==  0){
            viewDate = format.format(dateNow);
        }

        try{
            String Alumresult;
            ConnectAlumListActivity task = new ConnectAlumListActivity();
            Alumresult = task.execute(userId, viewDate).get().trim();

            if(Alumresult.length() != 0){
                String[] sp = Alumresult.split("㏂");

                for (int j = 0; j < sp.length; j++) {
                    String[] ssp = sp[j].split("`");
                    array.add(ssp);
                }

            }
        }catch (Exception e){
            Log.i("DBtest", "Error");
        }

        SimpleDateFormat yearDays = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat MonthDays = new SimpleDateFormat("MM.dd");
        SimpleDateFormat f24Hours=new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat f12Hours= new SimpleDateFormat("aa hh:mm");

        if(array.size()>0){
            //[0] cno : [1] RegDate : [2] Ctitle : [3] Cmemo : [4] Rgb : [5] Onoff
            for (int i = 0; i < array.size(); i++) {
                String[] date = array.get(i)[1].split(" ");
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
                
                boolean power=false;
                if(array.get(i)[5].equals("0")){
                    power=false;
                }else if (array.get(i)[5].equals("1")){
                    power=true;
                }

                //alumList(cno, alum_date, imgView, alum_time, alum_name)
                alumList.add(new AlumData(Integer.parseInt(array.get(i)[0]), mont1, array.get(i)[4], date2, array.get(i)[2], power));
            }
            alumAdapter = new AlumAdapter(alumList, this, this);
            alum_view.setAdapter(alumAdapter);
            alum_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        }

        if(alumList.size() == 0){
            alum_view.setVisibility(View.GONE);
            noalum_txt.setVisibility(View.VISIBLE);
        }else if(alumList.size() > 3){
            alum_view.setVisibility(View.VISIBLE);
            noalum_txt.setVisibility(View.GONE);

            ViewGroup.LayoutParams params = alum_view.getLayoutParams();
            params.height = 280 * alum_density;
            alum_view.setLayoutParams(params);
        }else{
            alum_view.setVisibility(View.VISIBLE);
            noalum_txt.setVisibility(View.GONE);
        }


        calendarView.setOnDateChangeListener (new CalendarView.OnDateChangeListener()  {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayofMonth)  {
                selectedDate.setText(String.format("%d월 %d일", month+1, dayofMonth));
                viewDate = String.format("%d/%d/%d", year, month+1, dayofMonth);

                array.clear();
                alumList.clear();

                try{
                    String result;
                    ConnectAlumListActivity task = new ConnectAlumListActivity();
                    result = task.execute(userId, viewDate).get().trim();

                    if(result.length() != 0){
                        String[] sp = result.split("㏂");

                        for (int j = 0; j < sp.length; j++) {
                            String[] ssp = sp[j].split("`");
                            array.add(ssp);
                        }

                    }
                }catch (Exception e){
                    Log.i("DBtest", "Error");
                }

                if(array.size()>0){
                    //[0] cno : [1] RegDate : [2] Ctitle : [3] Cmemo : [4] Rgb : [5] Onoff
                    for (int i = 0; i < array.size(); i++) {
                        String[] date = array.get(i)[1].split(" ");
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

                        boolean power=false;
                        if(array.get(i)[5].equals("0")){
                            power=false;
                        }else if (array.get(i)[5].equals("1")){
                            power=true;
                        }

                        //alumList(cno, alum_date, imgView, alum_time, alum_name)
                        alumList.add(new AlumData(Integer.parseInt(array.get(i)[0]), mont1, array.get(i)[4], date2, array.get(i)[2], power));
                    }
                }
                alumAdapter = new AlumAdapter(alumList, AlumFragment.this, AlumFragment.this);
                alum_view.setAdapter(alumAdapter);
                alum_view.setLayoutManager(new LinearLayoutManager(getActivity()));

                if(alumList.size() == 0){
                    alum_view.setVisibility(View.GONE);
                    noalum_txt.setVisibility(View.VISIBLE);
                }else if(alumList.size() > 3){
                    alum_view.setVisibility(View.VISIBLE);
                    noalum_txt.setVisibility(View.GONE);

                    ViewGroup.LayoutParams params = alum_view.getLayoutParams();
                    params.height = 280 * alum_density;
                    alum_view.setLayoutParams(params);
                }else{
                    alum_view.setVisibility(View.VISIBLE);
                    noalum_txt.setVisibility(View.GONE);
                }

            }
        });

        alumAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AlumAddActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });


        return rootView;
    }

    @Override
    public void onItemSelected(View v, int position) {
        AlumAdapter.AlumHolder viewHolder = (AlumAdapter.AlumHolder)alum_view.findViewHolderForAdapterPosition(position);

        Intent intent = new Intent(getActivity(), AlumChangeActivity.class);
        intent.putExtra("id", userId);
        intent.putExtra("cno", viewHolder.cnoTxt.getText());
        startActivity(intent);
    }

    @Override
    public void onSwitchSelected(boolean b, int position) {
        AlumAdapter.AlumHolder Holder = (AlumAdapter.AlumHolder)alum_view.findViewHolderForAdapterPosition(position);

        if(firstChecked == false){
            String cnoTxt = (String) Holder.cnoTxt.getText();
            int power;
            if(b){
                power = 1;
            }else{
                power = 0;
            }

            try{
                String Poweresult;
                ConnectAlumPowerActivity task = new ConnectAlumPowerActivity();
                Poweresult = task.execute(cnoTxt, String.valueOf(power)).get();

                if(Poweresult.equals("success")){
                    Toast.makeText(getActivity(), "알람 power 변경 성공", Toast.LENGTH_SHORT).show();
                } else if (Poweresult.equals("fail")) {
                    Toast.makeText(getActivity(), "알람 power 변경 실패", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), Poweresult, Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){
                Log.i("DBtest", "Error");
            }
        }
    }
}
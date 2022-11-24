package com.example.capstone;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MypageFragment extends Fragment implements PetAdapter.OnListItemSelectedInterface{


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    Button editproileBtn;
    ImageButton pluspetBtn, settingBtn;
    ImageView edit_pet;
    TextView idTxt, nicknameTxt;
    LinearLayout noPetLinear;

    private List<PetData> petList;
    private PetAdapter petAdapter;
    private RecyclerView pet_view;

    private String userId;
    String[] mypageResult;


    static int pet_density;

    String nickname, phone, Getnickname;

    public MypageFragment() {
        // Required empty public constructor
    }


    public static MypageFragment newInstance(String param1, String param2) {
        MypageFragment fragment = new MypageFragment();
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


        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_mypage, container, false);
        editproileBtn = rootView.findViewById(R.id.editpropileBtn);
        pluspetBtn = rootView.findViewById(R.id.pluspetBtn);
        settingBtn = rootView.findViewById(R.id.settingBtn);
        idTxt = rootView.findViewById(R.id.idTxt);
        nicknameTxt = rootView.findViewById(R.id.nicknameTxt);
        noPetLinear = rootView.findViewById(R.id.noPetLinear);
        pet_view = rootView.findViewById(R.id.pet_view);




        //리스트 높이 제한
        pet_density = (int)getResources().getDisplayMetrics().density;

        if(getArguments() != null){
            userId = getArguments().getString("userId");
            idTxt.setText(userId);
        }

        try{
            String result;
            ConnectUserActivity task = new ConnectUserActivity();
            result = task.execute(userId).get();

            mypageResult = result.split("/");

            if(mypageResult[0].equals("success")){
                idTxt.setText(mypageResult[1]);

                nickname = mypageResult[3];
                nicknameTxt.setText(nickname);

                phone = mypageResult[2];

            }else if(mypageResult[0].equals("fail")){
                Toast.makeText(getActivity(),"DB 연동 실패",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getActivity(),result,Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e){
            Log.i("DBtest", "Error");
        }

        editproileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),
                        EditPropileActivity.class);
                intent.putExtra("id", userId);
                intent.putExtra("nickname", nickname);
                intent.putExtra("phone", phone);

                startActivity(intent);
            }
        });

        pluspetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),
                        PetaddActivity.class);
                intent.putExtra("id", userId);
                startActivity(intent);
            }
        });

        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),
                        SettingActivity.class);
                startActivity(intent);
            }
        });

        pet_view = rootView.findViewById(R.id.pet_view);
        petList = new ArrayList<>();
        ArrayList<String[]> array = new ArrayList<>();

        try{
            String Petresult;
            ConnectPetListActivity task = new ConnectPetListActivity();
            Petresult = task.execute(userId).get().trim();

            if(Petresult.length() != 0){
                String[] sp = Petresult.split("#");

                for (int j = 0; j < sp.length; j++) {
                    String[] ssp = sp[j].split("/");
                    array.add(ssp);
                }
            }

        }catch (Exception e){
            Log.i("DBtest", "Error");
        }

        //[0] pno : [1] petName : [2] birthday : [3] petImg
        for (int i = 0; i < array.size(); i++) {
            //petList(img, petName, petBirthday, pno)
            petList.add(new PetData(R.drawable.circle_g, array.get(i)[1], array.get(i)[2], Integer.parseInt(array.get(i)[0].trim())));
        }

        petAdapter = new PetAdapter(petList,this);
        pet_view.setAdapter(petAdapter);
        pet_view.setLayoutManager(new LinearLayoutManager(getActivity()));

        if(petList.size() == 0){
            pet_view.setVisibility(View.GONE);
            noPetLinear.setVisibility(View.VISIBLE);
        }else if(petList.size() > 5){
            pet_view.setVisibility(View.VISIBLE);
            noPetLinear.setVisibility(View.GONE);

            ViewGroup.LayoutParams params = pet_view.getLayoutParams();
            params.height = 300 * pet_density;
            pet_view.setLayoutParams(params);
        }else{
            pet_view.setVisibility(View.VISIBLE);
            noPetLinear.setVisibility(View.GONE);
        }

        return rootView;
    }

    @Override
    public void onItemSelected(View v, int position) {
        PetAdapter.PetHolder viewHolder = (PetAdapter.PetHolder)pet_view.findViewHolderForAdapterPosition(position);

        Intent intent = new Intent(getActivity(), PeteditActivity.class);
        intent.putExtra("id", userId);
        intent.putExtra("pno", viewHolder.pet_pno.getText());
        startActivity(intent);
    }
}
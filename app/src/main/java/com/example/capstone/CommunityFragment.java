package com.example.capstone;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class CommunityFragment extends Fragment implements WriteAdapter.OnListItemSelectedInterface{


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    ImageButton searchBtn, bellBtn, writeplusBtn;
    Button communityBtn;
    SwipeRefreshLayout content_refresh;

    String userId;


    private List<WriteData> writeList;
    private WriteAdapter writeAdapter;
    private RecyclerView write_view;

    public CommunityFragment() {
        // Required empty public constructor
    }

    public static CommunityFragment newInstance(String param1, String param2) {
        CommunityFragment fragment = new CommunityFragment();
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
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_community, container, false);

        searchBtn = rootView.findViewById(R.id.searchBtn);
        writeplusBtn = rootView.findViewById(R.id.writeplusBtn);
        bellBtn = rootView.findViewById(R.id.bellBtn);
        content_refresh = rootView.findViewById(R.id.content_refresh);

        if(getArguments() != null){
            userId = getArguments().getString("userId");
        }

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),
                        SearchActivity.class);
                startActivity(intent);
            }
        });

        writeplusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),
                        PostwriteActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

        bellBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),
                        PostActivity.class);
                startActivity(intent);
            }
        });

        write_view = rootView.findViewById(R.id.write_view);
        writeList = new ArrayList<>();
        ArrayList<String[]> array = new ArrayList<>();

        try{
            String Boardresult;
            ConnectBoardListActivity task = new ConnectBoardListActivity();
            Boardresult = task.execute().get();

            String[] sp = Boardresult.split("#");

            for (int j = 0; j < sp.length; j++) {
                String[] ssp = sp[j].split("/");
                array.add(ssp);
            }

        }catch (Exception e){
            Log.i("DBtest", "Error");
        }

        //[0] bno : [1] title : [2] replycnt : [3] recommendcnt : [4] writerNickname : [5] regDate : [6] userid
        for (int i = 0; i < array.size(); i++) {
            //writeList(img, title, write_Date, writer, heart, reply, bno)
            writeList.add(new WriteData(R.drawable.circle_g, array.get(i)[1], array.get(i)[5], array.get(i)[4], array.get(i)[3], array.get(i)[2], Integer.parseInt(array.get(i)[0].trim())));
        }

        writeAdapter = new WriteAdapter(writeList, this);
        write_view.setAdapter(writeAdapter);
        write_view.setLayoutManager(new LinearLayoutManager(getActivity()));

        content_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                writeList.clear();
                array.clear();
                try{
                    String result;
                    ConnectBoardListActivity task = new ConnectBoardListActivity();
                    result = task.execute().get();

                    String[] sp = result.split("#");

                    for (int j = 0; j < sp.length; j++) {
                        String[] ssp = sp[j].split("/");
                        array.add(ssp);
                    }

                }catch (Exception e){
                    Log.i("DBtest", "Error");
                }
                for (int i = 0; i < array.size(); i++) {
                    //petList(img, title, write_Date, writer, heart, reply, bno)
                    writeList.add(new WriteData(R.drawable.circle_g, array.get(i)[1], array.get(i)[5], array.get(i)[4], array.get(i)[3], array.get(i)[2], Integer.parseInt(array.get(i)[0].trim())));
                }

                write_view.setAdapter(writeAdapter);
                write_view.setLayoutManager(new LinearLayoutManager(getActivity()));
                content_refresh.setRefreshing(false);

            }
        });


        rootView.findViewById(R.id.communityBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final PopupMenu popupMenu = new PopupMenu(getContext(), view);
                popupMenu.getMenuInflater().inflate(R.menu.popup1, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.action_menu1) {
                            Toast.makeText(getActivity(), "전체글 클릭", Toast.LENGTH_SHORT).show();
                        } else if (menuItem.getItemId() == R.id.action_menu2) {
                            Toast.makeText(getActivity(), "자유게시판 클릭", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "갤러리 클릭", Toast.LENGTH_SHORT).show();
                        }

                        return false;
                    }
                });

                popupMenu.show();
            }
        });

        return rootView;
    }


    @Override
    public void onItemSelected(View v, int position) {
        WriteAdapter.WriteHolder viewHolder = (WriteAdapter.WriteHolder)write_view.findViewHolderForAdapterPosition(position);

        Intent intent = new Intent(getActivity(), PostActivity.class);
        intent.putExtra("bno", viewHolder.bno.getText());
        intent.putExtra("id", userId);
        startActivity(intent);
    }


}
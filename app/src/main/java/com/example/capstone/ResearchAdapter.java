package com.example.capstone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ResearchAdapter extends RecyclerView.Adapter<ResearchAdapter.ResearchHolder> {

    private List<ResearchData> dataList;


    public ResearchAdapter(List<ResearchData> dataList) {
        this.dataList = dataList;

    }

    //처음 불러올때 생성
    @NonNull
    @Override
    public ResearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.research_list, parent, false);
        return new ResearchHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResearchHolder holder, int position) {
        ResearchData item = dataList.get(position);

        holder.research1.setText(item.getResearch1());

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    //Holder
    public class ResearchHolder extends RecyclerView.ViewHolder {


        private TextView research1;
        //ImageButton powerBtn;

        public ResearchHolder(@NonNull View itemView) {
            super(itemView);
            research1 = (TextView) itemView.findViewById(R.id.research1);

        }
    }

}

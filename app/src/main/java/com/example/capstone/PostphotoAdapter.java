package com.example.capstone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PostphotoAdapter extends RecyclerView.Adapter<PostphotoAdapter.PostphotoHolder> {

        private List<PostphotoData> dataList;


    public PostphotoAdapter(List<PostphotoData> dataList) {
        this.dataList = dataList;

    }

    //처음 불러올때 생성
    @NonNull
    @Override
    public PostphotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.postwrite_list, parent, false);
        return new PostphotoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostphotoHolder holder, int position) {
        PostphotoData item = dataList.get(position);


        holder.photo1.setImageResource(item.getPhoto1());


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    //Holder
    public class PostphotoHolder extends RecyclerView.ViewHolder {

        private ImageView photo1;



        public PostphotoHolder(@NonNull View itemView) {
            super(itemView);
            photo1 = (ImageView) itemView.findViewById(R.id.photo1);

        }
    }

}

package com.example.capstone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.PetHolder> {

    private List<PetData> dataList;


    public interface OnListItemSelectedInterface{
        void onItemSelected(View v, int position);
    }

    private OnListItemSelectedInterface mListener;

    public PetAdapter(List<PetData> dataList,
                      OnListItemSelectedInterface listener){
        this.dataList = dataList;
        this.mListener = listener;
    }


    //처음 불러올때 생성
    @NonNull
    @Override
    public PetHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pet_list, parent, false);
        return new PetHolder(view);
    }

    //데이터 바인딩 될때 호출
    @Override
    public void onBindViewHolder(@NonNull PetHolder holder, int position) {
        PetData item = dataList.get(position);

        holder.pet_photo.setImageResource(item.getPet_photo());
        holder.pet_name.setText(item.getPet_name());
        holder.pet_date.setText(item.getPet_date());
        holder.pet_pno.setText(String.valueOf(item.getPet_pno()));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    //Holder
    public class PetHolder extends RecyclerView.ViewHolder {

        private ImageView pet_photo;
        public TextView pet_name, pet_date;
        public TextView pet_pno;


        public PetHolder(@NonNull View itemView) {
            super(itemView);
            pet_photo = (ImageView) itemView.findViewById(R.id.pet_photo);
            pet_name = (TextView) itemView.findViewById(R.id.pet_name);
            pet_date = (TextView) itemView.findViewById(R.id.pet_date);
            pet_pno = (TextView)itemView.findViewById(R.id.pet_pno);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        mListener.onItemSelected(view, position);
                    }
                }
            });

        }
    }

}

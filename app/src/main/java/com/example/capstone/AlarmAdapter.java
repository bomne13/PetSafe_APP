package com.example.capstone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmHolder> {

    private List<AlarmData> dataList;

//    public interface OnListItemSelectedInterface{
//        void onItemSelected(View v, int position);
//    }
//
//    private AlarmAdapter.OnListItemSelectedInterface mListener;

    public AlarmAdapter(List<AlarmData> dataList
                        ){
        this.dataList = dataList;
    }

    //처음 불러올때 생성
   @NonNull
    @Override
    public AlarmHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm_list, parent, false);
        return new AlarmHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmHolder holder, int position) {
        AlarmData item = dataList.get(position);

        holder.imgView.setImageResource(item.getImgView());
        holder.alarm_name.setText(item.getAlarm_name());
        holder.alarm_date.setText(item.getAlarm_date());

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    //Holder
    public class AlarmHolder extends RecyclerView.ViewHolder{

        private ImageView imgView;
        private TextView alarm_name, alarm_date;

        public AlarmHolder(@NonNull View itemView) {
            super(itemView);
            imgView = (ImageView) itemView.findViewById(R.id.imgView);
            alarm_name = (TextView)itemView.findViewById(R.id.alarm_name);
            alarm_date = (TextView) itemView.findViewById(R.id.alarm_date);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int position = getAdapterPosition();
//                    if(position != RecyclerView.NO_POSITION){
//                        mListener.onItemSelected(view, position);
//                    }
//                }
//            });
        }
    }
}

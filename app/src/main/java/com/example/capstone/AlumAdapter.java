package com.example.capstone;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AlumAdapter extends RecyclerView.Adapter<AlumAdapter.AlumHolder> {
    private List<AlumData> dataList;

    public interface OnListItemSelectedInterface{
        void onItemSelected(View v, int position);
    }

    public interface OnListItemSwitchInterface{
        void onSwitchSelected(boolean b, int position);
    }

    private AlumAdapter.OnListItemSelectedInterface mListener;
    private AlumAdapter.OnListItemSwitchInterface sListener;

    public AlumAdapter(List<AlumData> dataList){
        this.dataList = dataList;
    }


    public AlumAdapter(List<AlumData> dataList,
                       AlumAdapter.OnListItemSelectedInterface listener,
                       AlumAdapter.OnListItemSwitchInterface Listener){
        this.dataList = dataList;
        this.mListener = listener;
        this.sListener = Listener;
    }


    @NonNull
    @Override
    public AlumHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alum_list, parent, false);
        return new AlumHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlumHolder holder, int position) {

        AlumData item = dataList.get(position);

        int color = Color.parseColor(item.getImgView());

        holder.cnoTxt.setText(String.valueOf(item.getCnoTxt()));
        holder.alum_date.setText(item.getAlum_date());
        holder.alum_time.setText(item.getAlum_time());
        holder.alum_name.setText(item.getAlum_name());
        holder.imgView.setBackgroundTintList(ColorStateList.valueOf(color));
        holder.imgView.setBackgroundTintMode(PorterDuff.Mode.MULTIPLY);
        holder.SwitchCompat.setChecked(item.isAlum_checked());

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class AlumHolder extends RecyclerView.ViewHolder{
        private ImageView imgView;
        private TextView alum_time, alum_name, alum_date;
        public TextView cnoTxt;
        public SwitchCompat SwitchCompat;

        public AlumHolder(@NonNull View itemView) {
            super(itemView);
            cnoTxt = (TextView)itemView.findViewById(R.id.cnoTxt);
            alum_date = (TextView) itemView.findViewById(R.id.alum_date);
            imgView = (ImageView) itemView.findViewById(R.id.imgView);
            alum_time = (TextView)itemView.findViewById(R.id.alum_time);
            alum_name = (TextView) itemView.findViewById(R.id.alum_name);
            SwitchCompat = (SwitchCompat)itemView.findViewById(R.id.powerBtn);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        mListener.onItemSelected(view, position);
                    }
                }
            });

            SwitchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        sListener.onSwitchSelected(b, position);
                    }

                }
            });

        }
    }
}

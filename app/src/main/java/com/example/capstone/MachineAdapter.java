package com.example.capstone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MachineAdapter extends RecyclerView.Adapter<MachineAdapter.MachineHolder> {

    private List<MachineData> dataList;

    public interface OnListItemSwitchInterface{
        void onSwitchSelected(boolean b, int position);
    }

    private MachineAdapter.OnListItemSwitchInterface sListener;

    public MachineAdapter(List<MachineData> dataList,
                          MachineAdapter.OnListItemSwitchInterface listener) {
        this.dataList = dataList;
        this.sListener = listener;
    }

    //처음 불러올때 생성
   @NonNull
    @Override
    public MachineHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.machine_list, parent, false);
        return new MachineHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MachineHolder holder, int position) {
        MachineData item = dataList.get(position);

        holder.machine_nickname.setText(item.getMachine_nickname());
        holder.machine_name.setText(item.getMachine_name());
        holder.imgView.setImageResource(item.getImgView());
        holder.powerBtn.setChecked(item.isPowerBtn());


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    //Holder
    public class MachineHolder extends RecyclerView.ViewHolder{

        private ImageView imgView;
        public TextView machine_nickname, machine_name;
        public SwitchCompat powerBtn;

        public MachineHolder(@NonNull View itemView) {
            super(itemView);
            imgView = (ImageView) itemView.findViewById(R.id.imgView);
            machine_nickname = (TextView)itemView.findViewById(R.id.machine_nickname);
            machine_name = (TextView) itemView.findViewById(R.id.machine_name);
            powerBtn = (SwitchCompat) itemView.findViewById(R.id.powerBtn);

            powerBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        sListener.onSwitchSelected(b, position);
                    }
                }
            });
        }
    }
}

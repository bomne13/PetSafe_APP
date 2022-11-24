package com.example.capstone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.ReplyHolder> {

    private List<ReplyData> dataList;

    public interface OnListItemSelectedInterface{
        void onItemSelected(View v, int position);
    }

    private OnListItemSelectedInterface mListener;


    public ReplyAdapter(List<ReplyData> dataList,
                        OnListItemSelectedInterface listener) {
        this.dataList = dataList;
        this.mListener = listener;
    }

    //처음 불러올때 생성
    @NonNull
    @Override
    public ReplyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reply_list, parent, false);
        return new ReplyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReplyHolder holder, int position) {
        ReplyData item = dataList.get(position);

        holder.imgView.setImageResource(item.getImgView());
        holder.reply_writer.setText(item.getReply_writer());
        holder.reply_write_date.setText(item.getReply_write_date());
        holder.reply_content.setText(item.getReply_content());
        holder.reply_heartbtn1.setText(item.getReply_heartbtn1());
        holder.rno.setText(String.valueOf(item.getRno()));
        holder.id.setText(item.getId());

    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    //Holder
    public class ReplyHolder extends RecyclerView.ViewHolder {

        public ImageView imgView;
        public ImageButton reply_etc;
        public TextView reply_writer, reply_write_date, reply_content;
        public TextView rno, id;
        private Button reply_heartbtn1;

        public ReplyHolder(@NonNull View itemView) {
            super(itemView);
            imgView = (ImageView) itemView.findViewById(R.id.imgView);
            reply_etc = (ImageButton) itemView.findViewById(R.id.reply_etc);
            reply_writer = (TextView) itemView.findViewById(R.id.reply_writer);
            reply_write_date = (TextView) itemView.findViewById(R.id.reply_write_date);
            reply_content = (TextView) itemView.findViewById(R.id.reply_content);
            reply_heartbtn1 = (Button) itemView.findViewById(R.id.reply_heartbtn1);
            rno = (TextView) itemView.findViewById(R.id.rno);
            id = (TextView)itemView.findViewById(R.id.id);

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

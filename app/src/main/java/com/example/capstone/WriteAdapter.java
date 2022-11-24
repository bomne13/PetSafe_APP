package com.example.capstone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WriteAdapter extends RecyclerView.Adapter<WriteAdapter.WriteHolder> {

    private List<WriteData> dataList;

    public interface OnListItemSelectedInterface{
        void onItemSelected(View v, int position);
    }

    private OnListItemSelectedInterface mListener;


    public WriteAdapter(List<WriteData> dataList,
                        OnListItemSelectedInterface listener) {
        this.dataList = dataList;
        this.mListener = listener;
    }

    //처음 불러올때 생성
    @NonNull
    @Override
    public WriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.write_list, parent, false);
        return new WriteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WriteHolder holder, int position) {
        WriteData item = dataList.get(position);

        holder.title.setText(item.getTitle());
        holder.write_date.setText(item.getWrite_date());
        holder.writer.setText(item.getWriter());
        holder.imgView.setImageResource(item.getImgView());
        holder.heartbtn1.setText(item.getHeart());
        holder.replyBtn.setText(item.getReply());
        holder.bno.setText(String.valueOf(item.getBno()));


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    //Holder
    public class WriteHolder extends RecyclerView.ViewHolder {

        private ImageView imgView;
        private TextView title, write_date, writer;
        public TextView bno;
        private Button heartbtn1, replyBtn;
        //ImageButton powerBtn;

        public WriteHolder(@NonNull View itemView) {
            super(itemView);
            imgView = (ImageView) itemView.findViewById(R.id.imgView);
            title = (TextView) itemView.findViewById(R.id.title);
            write_date = (TextView) itemView.findViewById(R.id.write_date);
            writer = (TextView) itemView.findViewById(R.id.writer);
            heartbtn1 = (Button) itemView.findViewById(R.id.heartbtn1);
            replyBtn = (Button) itemView.findViewById(R.id.replyBtn);
            bno = (TextView) itemView.findViewById(R.id.bno);

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

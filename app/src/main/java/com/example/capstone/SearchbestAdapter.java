package com.example.capstone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SearchbestAdapter extends RecyclerView.Adapter<SearchbestAdapter.SearchbestHolder> {

private List<SearchbestData> dataList;



public SearchbestAdapter(List<SearchbestData> dataList) {
        this.dataList = dataList;

        }

//처음 불러올때 생성
@NonNull
@Override
public SearchbestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchbest_list, parent, false);
        return new SearchbestHolder(view);
        }

@Override
public void onBindViewHolder(@NonNull SearchbestHolder holder, int position) {
        SearchbestData item = dataList.get(position);
        holder.searchbest.setText(item.getSearchbest());

        }

@Override
public int getItemCount() {
        return dataList.size();
        }

//Holder
public class SearchbestHolder extends RecyclerView.ViewHolder {

    private TextView searchbest;

    public SearchbestHolder(@NonNull View itemView) {
        super(itemView);

        searchbest = (TextView) itemView.findViewById(R.id.searchbest);
    }
}

}

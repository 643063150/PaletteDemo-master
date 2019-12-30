package com.example.administrator.colorfulbanner.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.colorfulbanner.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {
    private ArrayList<String> mList;
    private Context context;
//    RequestOptions options;

    public MyAdapter(ArrayList<String> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int pos) {
        myHolder.textView.setText(mList.get(pos));
        ArrayList<String> arrayList=new ArrayList<>();
        for (int i=0;i<3;i++){
            arrayList.add(i+"");
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        myHolder.list.setLayoutManager(layoutManager);
        MyAdapters myAdapter = new MyAdapters(arrayList,context);
        myHolder.list.setAdapter(myAdapter);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView textView;
        RecyclerView list;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.txt);
            list = itemView.findViewById(R.id.list);
        }
    }
}

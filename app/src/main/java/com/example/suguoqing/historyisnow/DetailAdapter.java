package com.example.suguoqing.historyisnow;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.suguoqing.util.RetrofitTool;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {
    private static final String TAG = "DetailAdapter";

    private List<Detail> list;
    private Context mContext;


    static class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView detail_img;
        TextView title;
        TextView date;
        TextView lunar;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            detail_img = itemView.findViewById(R.id.detail_img);
            title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.date);
            lunar = itemView.findViewById(R.id.lunar);

        }
    }

    public DetailAdapter(List<Detail> list) {
        this.list = list;
    }

    public DetailAdapter(List<Detail> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public DetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.detail_item,parent,false);
        final DetailAdapter.ViewHolder viewHolder = new ViewHolder(view);

        //点击事件处理每个item
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "跳转到下一个页面", Toast.LENGTH_SHORT).show();
                int postion = viewHolder.getAdapterPosition();
                Detail detail = list.get(postion);
                Intent intent = new Intent(mContext,AllInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("detail",detail);
                intent.putExtra("bundle",bundle);
                mContext.startActivity(intent);

            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DetailAdapter.ViewHolder holder, int position) {
        Detail detail = list.get(position);
        holder.title.setText(detail.getTitle());
        holder.date.setText(detail.getYear()+"年"+detail.getMonth()+"月"+detail.getDay()+"日");
        holder.lunar.setText(detail.getLunar());
        //缓存
        if("".equals(detail.getPic())|detail.getPic().length() == 0){
            Glide.with(mContext).load(R.mipmap.img_notfind).into(holder.detail_img);
        }else{
            Glide.with(mContext).load(detail.getPic()).into(holder.detail_img);
        }



    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}

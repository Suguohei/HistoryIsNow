package com.example.suguoqing.historyisnow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.ViewHolder> {
    private static final String TAG = "SettingAdapter";
    private Context mcontext;

    private List<String> list;

    public SettingAdapter(List<String> list,Context mcontext) {
        this.list = list;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.settings_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        //点击触发
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: -------------------点击触发");
                int postion = holder.getAdapterPosition();
                String settingname = list.get(postion);
                switch (postion){
                    case 0:
                        //处理第一个设置
                        break;
                    case 1:
                        //处理第二个
                        break;
                    case 2:
                        //处理第三个
                        SharedPreferences pref = parent.getContext().getSharedPreferences("com.example.suguoqing.historyisnow_preferences", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.clear();
                        editor.commit();
                        //点击退出当前账户之后回到初始界面,同时清除之前的所有界面
                        Intent intent = new Intent(mcontext,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        mcontext.startActivity(intent);
                        Log.d(TAG, "onClick: ---------------清除");
                        break;
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String str = list.get(position);
        holder.textView.setText(str);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.setting_item_textview);
        }
    }
}

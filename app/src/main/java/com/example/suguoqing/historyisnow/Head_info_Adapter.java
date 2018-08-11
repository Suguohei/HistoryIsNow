package com.example.suguoqing.historyisnow;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

public class Head_info_Adapter extends RecyclerView.Adapter<Head_info_Adapter.ViewHolder> {
    private List<String> list;
    private Context mContext;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.head_info_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        //这里是设置点击后的阴影效果
        TypedValue value = new TypedValue();
        mContext.getTheme().resolveAttribute(R.attr.selectableItemBackground,value,true);
        view.setBackgroundResource(value.resourceId);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "点击触发", Toast.LENGTH_SHORT).show();
                int postion = holder.getAdapterPosition();
                String str = list.get(postion);

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

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textview);
        }
    }

    public Head_info_Adapter(List<String> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }
}

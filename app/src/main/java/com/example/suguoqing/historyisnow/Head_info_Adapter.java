package com.example.suguoqing.historyisnow;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suguoqing.bean.User;

import org.w3c.dom.Text;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Head_info_Adapter extends RecyclerView.Adapter<Head_info_Adapter.ViewHolder> {
    private static final String TAG = "Head_info_Adapter";
    private List<String> list;
    private Context mContext;
    private User mUser;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.head_info_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        //这里是设置点击后的阴影效果
        TypedValue value = new TypedValue();
        mContext.getTheme().resolveAttribute(R.attr.selectableItemBackground,value,true);
        view.setBackgroundResource(value.resourceId);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int postion = holder.getAdapterPosition();
                switch (postion){
                    case 0:
                        Log.d(TAG, "onClick: ------------------"+mUser);
                        final AlertDialog.Builder editDialog = new AlertDialog.Builder(mContext);
                        final EditText editText = new EditText(mContext);
                        editDialog.setTitle("请输入姓名")
                                .setIcon(R.mipmap.img_write)
                                .setView(editText)
                                .setNegativeButton("取消",null)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        SharedPreferences preferences =
                                                mContext.getSharedPreferences("com.example.suguoqing.historyisnow_preferences",MODE_PRIVATE);
                                        SharedPreferences.Editor editor = preferences.edit().putString("username",editText.getText().toString());
                                        editor.apply();
                                        mUser.setName(editText.getText().toString());
                                        mUser.save();
                                        Log.d(TAG, "onClick: ----------------"+mUser);
                                        Intent intent = new Intent(mContext,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        mContext.startActivity(intent);
                                    }
                                })
                                .show();
                        break;

                    case 1:
                        AlertDialog.Builder editDialog2 = new AlertDialog.Builder(mContext);
                        final EditText editText1 = new EditText(mContext);
                        editDialog2.setTitle("请输入email")
                                .setIcon(R.mipmap.img_emil)
                                .setView(editText1)
                                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mUser.setEmail(editText1.getText().toString());
                                        mUser.save();
                                        Intent intent = new Intent(mContext,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        mContext.startActivity(intent);
                                    }
                                })
                                .setNegativeButton("取消",null)
                                .show();

                        break;
                        default:
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textview);
        }
    }

    public Head_info_Adapter(List<String> list, Context mContext,User user) {
        this.list = list;
        this.mContext = mContext;
        this.mUser = user;
    }
}

package com.example.suguoqing.historyisnow;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.suguoqing.bean.Note;

import java.util.List;
import java.util.zip.Inflater;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder>{
    private List<Note> notes;
    private Context mContext;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item,parent,false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            holder.title.setText(notes.get(position).getTitle());
            holder.time.setText(notes.get(position).getTime());


    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
         TextView title;
         TextView time;
        public ViewHolder(View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.note_time);
            title = itemView.findViewById(R.id.note_title);
        }
    }

    public NoteAdapter(List<Note> notes, Context mContext) {
        this.notes = notes;
        this.mContext = mContext;
    }
}

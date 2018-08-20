package com.example.suguoqing.historyisnow;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.suguoqing.bean.Note;

import java.util.List;
import java.util.zip.Inflater;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder>{
    private static final String TAG = "NoteAdapter";
    private List<Note> notes;
    private Context mContext;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int postion = holder.getAdapterPosition();
                Note currentNote = notes.get(postion);
                Intent intent = new Intent(mContext,EditAcitvity.class);
                intent.putExtra("currentNote",currentNote);
                mContext.startActivity(intent);

            }
        });

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

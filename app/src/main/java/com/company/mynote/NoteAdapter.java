package com.company.mynote;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private ArrayList<Note> noteList;
    private OnNoteClickListener onNoteClickListener;

    public NoteAdapter(ArrayList<Note> noteList) {
        this.noteList = noteList;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note currentNote = noteList.get(position);
        holder.titleTextView.setText(currentNote.getTitle());
        holder.contentTextView.setText(currentNote.getContent());

        holder.deleteButton.setOnClickListener(v -> {
            if (onNoteClickListener != null) {
                onNoteClickListener.onNoteDelete(position);
            }
        });

        holder.archiveButton.setOnClickListener(v -> {
            if (onNoteClickListener != null) {
                onNoteClickListener.onNoteArchive(position);
            }
        });

        holder.itemView.setOnClickListener(v -> {
            if (onNoteClickListener != null) {
                onNoteClickListener.onNoteClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, contentTextView;
        ImageButton deleteButton, archiveButton;

        public NoteViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.tvNoteTitle);
            contentTextView = itemView.findViewById(R.id.tvNoteContent);
            deleteButton = itemView.findViewById(R.id.delete_btn);
            archiveButton = itemView.findViewById(R.id.archive_btn);
        }
    }

    // واجهة التفاعل
    public interface OnNoteClickListener {
        void onNoteClick(int position);
        void onNoteDelete(int position);
        void onNoteArchive(int position);
    }

    public void setOnNoteClickListener(OnNoteClickListener listener) {
        onNoteClickListener = listener;
    }
}

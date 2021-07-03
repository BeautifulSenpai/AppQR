package com.example.appqr.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appqr.R;
import com.example.appqr.entities.Note;
import com.example.appqr.listeners.NoteListener;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    private List<Note> notes;

    private NoteListener noteListener;

    public NotesAdapter(List<Note> notes, NoteListener noteListener) {
        this.notes = notes;
        this.noteListener = noteListener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.note_container_item,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.setNote(notes.get(position));
        holder.layoutNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteListener.onNoteClicked(notes.get(position), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder{
        TextView titleText, noteText;
        LinearLayout layoutNote;

        NoteViewHolder(@NonNull View itemView){
            super(itemView);
            titleText = itemView.findViewById(R.id.titleText);
            noteText = itemView.findViewById(R.id.noteText);
            layoutNote = itemView.findViewById(R.id.noteLayout);
        }

        void setNote(Note note){
            titleText.setText(note.getTitle());
            if(note.getTextNote().trim().isEmpty()){
                noteText.setVisibility(View.GONE);
            }else{
                noteText.setText(note.getTextNote());
            }
        }
    }
}

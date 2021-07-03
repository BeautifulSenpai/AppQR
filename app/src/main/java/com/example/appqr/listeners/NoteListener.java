package com.example.appqr.listeners;

import com.example.appqr.entities.Note;

public interface NoteListener {
    void onNoteClicked(Note note, int position);
}

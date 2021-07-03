package com.example.appqr.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appqr.R;
import com.example.appqr.database.NoteDatabase;
import com.example.appqr.entities.Note;

public class NoteCreateActivity extends AppCompatActivity {

    private EditText noteInputTitle, noteInputText;

    private Note alreadyAvailableNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_create);

        ImageView backImage = findViewById(R.id.backImage);
        backImage.setOnClickListener(v -> onBackPressed());

        noteInputTitle = findViewById(R.id.noteTitle);
        noteInputText = findViewById(R.id.noteInput);

        ImageView saveImage = findViewById(R.id.saveImage);
        saveImage.setOnClickListener(v -> saveNote());

        if(getIntent().getBooleanExtra("isViewOrUpdate", false)){
            alreadyAvailableNote = (Note) getIntent().getSerializableExtra("note");
            setViewOrUpdateNote();
        }


    }

    private void setViewOrUpdateNote(){
        noteInputTitle.setText(alreadyAvailableNote.getTitle());
        noteInputText.setText(alreadyAvailableNote.getTextNote());

    }

    private void saveNote(){
        if(noteInputTitle.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Введите название!", Toast.LENGTH_SHORT).show();
            return;
        }else if(noteInputText.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Запись не может быть пуста!", Toast.LENGTH_SHORT).show();
            return;
        }

        final Note note = new Note();
        note.setTitle(noteInputTitle.getText().toString());
        note.setTextNote(noteInputText.getText().toString());

        if(alreadyAvailableNote != null){
            note.setId(alreadyAvailableNote.getId());
        }

        @SuppressLint("StaticFieldLeak")
        class SaveNoteTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids){
                NoteDatabase.getDatabase(getApplicationContext()).noteDao().insertNote(note);
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid){
                super.onPostExecute(aVoid);
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        }
        new SaveNoteTask().execute();
    }
}
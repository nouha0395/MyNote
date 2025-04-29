package com.company.mynote;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddNoteActivity extends AppCompatActivity {

    private EditText titleEditText, contentEditText, subjectEditText, timeEditText;
    private ImageButton saveNoteBtn;
    private NotesDatabaseHelper dbHelper;

    private boolean isSaved = false;
    private Note currentNote = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_note);

        dbHelper = new NotesDatabaseHelper(this);

        titleEditText = findViewById(R.id.notes_title_text);
        contentEditText = findViewById(R.id.notes_content_text);
        subjectEditText = findViewById(R.id.subject_text);
        timeEditText = findViewById(R.id.alarm_time_text);
        saveNoteBtn = findViewById(R.id.save_note_btn);

        TextWatcher watcher = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                autoSaveNote();
            }
        };

        titleEditText.addTextChangedListener(watcher);
        contentEditText.addTextChangedListener(watcher);
        subjectEditText.addTextChangedListener(watcher);
        timeEditText.addTextChangedListener(watcher);

        saveNoteBtn.setOnClickListener(view -> {
            saveNote();
        });
    }

    private void saveNote() {
        String title = titleEditText.getText().toString().trim();
        String content = contentEditText.getText().toString().trim();
        String subject = subjectEditText.getText().toString().trim();
        String time = timeEditText.getText().toString().trim();

        if (title.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, "Title and content are required!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (currentNote == null) {
            currentNote = new Note(0, title, content, subject, time, false);
            dbHelper.addNote(currentNote);
        } else {
            currentNote.setTitle(title);
            currentNote.setContent(content);
            currentNote.setSubject(subject);
            currentNote.setTime(time);
            dbHelper.updateNote(currentNote);
        }

        isSaved = true;
        Toast.makeText(this, "Note saved!", Toast.LENGTH_SHORT).show();
    }

    private void autoSaveNote() {
        if (!isSaved) {
            saveNote();
        }
    }
}

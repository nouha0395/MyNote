package com.company.mynote;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NoteAdapter noteAdapter;
    private ArrayList<Note> noteList;
    private NotesDatabaseHelper dbHelper;
    private static final int ADD_NOTE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        FloatingActionButton addNoteBtn = findViewById(R.id.add_note_btn);
        ImageButton menuButton = findViewById(R.id.button_menu);

        dbHelper = new NotesDatabaseHelper(this);

        noteList = new ArrayList<>();
        noteAdapter = new NoteAdapter(noteList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(noteAdapter);

        // تحميل الملاحظات من قاعدة البيانات
        loadNotesFromDatabase();

        // زر إضافة ملاحظة
        addNoteBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
            startActivityForResult(intent, ADD_NOTE_REQUEST);
        });

        // زر القائمة (لاحقًا)
        menuButton.setOnClickListener(view -> {
            // إعدادات مستقبلية
        });

        // إعداد الاستماع لعمليات الملاحظة
        noteAdapter.setOnNoteClickListener(new NoteAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(int position) {
                // هنا يمكنك فتح الملاحظة أو التعديل عليها
                Note clickedNote = noteList.get(position);
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                intent.putExtra("note_id", clickedNote.getId());
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }

            @Override
            public void onNoteDelete(int position) {
                Note note = noteList.get(position);
                dbHelper.deleteNote(note.getId());
                noteList.remove(position);
                noteAdapter.notifyItemRemoved(position);
            }

            @Override
            public void onNoteArchive(int position) {
                Note note = noteList.get(position);
                note.setArchived(true);
                dbHelper.updateNote(note);
                noteAdapter.notifyItemChanged(position);
            }
        });
    }

    private void loadNotesFromDatabase() {
        noteList.clear();
        noteList.addAll(dbHelper.getAllNotes());
        noteAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            loadNotesFromDatabase(); // إعادة تحميل الملاحظات
        }
    }
}

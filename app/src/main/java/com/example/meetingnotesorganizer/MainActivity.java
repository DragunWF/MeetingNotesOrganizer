package com.example.meetingnotesorganizer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetingnotesorganizer.adapters.NoteAdapter;
import com.example.meetingnotesorganizer.data.Note;
import com.example.meetingnotesorganizer.helpers.DatabaseHelper;
import com.example.meetingnotesorganizer.helpers.Utils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button addBtn;
    private ImageView sortBtnUp, sortBtnDown;

    private RecyclerView notesRecycler;
    private NoteAdapter notesAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private SearchView searchBar;

    @Override
    protected void onResume() {
        super.onResume();
        notesAdapter.updateDataSet();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        try {
            DatabaseHelper.initialize(this);
            DatabaseHelper.addDummyData();

            notesRecycler = findViewById(R.id.notesRecycler);
            addBtn = findViewById(R.id.addBtn);
            searchBar = findViewById(R.id.searchBar);
            sortBtnUp = findViewById(R.id.sortImageBtnUp);
            sortBtnDown = findViewById(R.id.sortImageBtnDown);

            setButtons();
            setRecycler();
            setSearch();
        } catch (Exception err) {
            err.printStackTrace();
            Utils.longToast(err.getMessage(), this);
        }
    }

    private void setButtons() {
        addBtn.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, NoteEditorActivity.class));
        });
        sortBtnUp.setOnClickListener(v -> {
            reverseNoteList();
        });
        sortBtnDown.setOnClickListener(v -> {
            reverseNoteList();
        });
    }
    
    private void setRecycler() {
        notesRecycler.setHasFixedSize(false);

        notesAdapter = new NoteAdapter(DatabaseHelper.getNoteBank().getAll(), this, getSupportFragmentManager());
        notesRecycler.setAdapter(notesAdapter);

        layoutManager = new LinearLayoutManager(this);
        notesRecycler.setLayoutManager(layoutManager);
    }

    private void setSearch() {
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                update(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                update(s);
                return false;
            }

            public void update(String query) {
                List<Note> results = new ArrayList<>();

                query = query.toLowerCase();
                for (Note note : DatabaseHelper.getNoteBank().getAll()) {
                    String title = note.getTitle().toLowerCase();
                    if (title.contains(query)) {
                        results.add(note);
                    }
                }

                notesAdapter.updateDataSet(results);
            }
        });
    }

    private void reverseNoteList() {
        notesAdapter.reverseDataSet();
    }
}
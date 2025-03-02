package com.example.meetingnotesorganizer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetingnotesorganizer.helpers.Utils;

public class MainActivity extends AppCompatActivity {
    private Button addBtn;

    private RecyclerView notesRecycler;
    private RecyclerView.Adapter notesAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private SearchView searchBar;

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
            addBtn = findViewById(R.id.addBtn);
            searchBar = findViewById(R.id.searchBar);

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
            startActivity(new Intent(MainActivity.this, CreateNotes.class));
        });
    }
    
    private void setRecycler() {
        notesRecycler.setHasFixedSize(false);

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

            }
        });
    }
}
package com.example.meetingnotesorganizer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.meetingnotesorganizer.data.Note;
import com.example.meetingnotesorganizer.helpers.DatabaseHelper;
import com.example.meetingnotesorganizer.helpers.Utils;
import com.example.meetingnotesorganizer.services.NoteService;

public class ViewNoteActivity extends AppCompatActivity {
    private TextView titleText, dateText, timeText, descriptionText, meetingParticipantsText;
    private Button editBtn, deleteBtn;

    private int viewedNoteId;

    @Override
    protected void onResume() {
        super.onResume();
        setNoteDetails();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_notes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        try {
            DatabaseHelper.initialize(this);

            viewedNoteId = getIntent().getIntExtra("noteId", 0);

            bindElements();
            setNoteDetails();
            setButtons();
        } catch (Exception err) {
            err.printStackTrace();
            Utils.longToast(err.getMessage(), this);
        }
    }

    private void bindElements() {
        editBtn = findViewById(R.id.editBtn);
        deleteBtn = findViewById(R.id.deleteBtn);

        titleText = findViewById(R.id.titleText);
        dateText = findViewById(R.id.dateText);
        timeText = findViewById(R.id.timeText);
        descriptionText = findViewById(R.id.descriptionText);
        meetingParticipantsText = findViewById(R.id.meetingParticipantsText);
    }

    private void setNoteDetails() {
        Note note = DatabaseHelper.getNoteBank().get(viewedNoteId);

        titleText.setText(note.getTitle());
        dateText.setText(note.getDate());
        timeText.setText(note.getTime());
        descriptionText.setText(note.getDescription());
        meetingParticipantsText.setText(String.join(", ", note.getParticipants()));
    }

    private void setButtons() {
        editBtn.setOnClickListener(v -> {
            Intent intent = new Intent(ViewNoteActivity.this, NoteEditorActivity.class);
            intent.putExtra("noteId", viewedNoteId);
            startActivity(intent);
        });
        deleteBtn.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(ViewNoteActivity.this);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    NoteService.delete(viewedNoteId);
                    Utils.longToast("Note has been delete successfully!", ViewNoteActivity.this);
                    finish();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancels the dialog.
                }
            });
            builder.setMessage("Are you sure you want to delete this note?");
            builder.setTitle("Confirmation Dialog");

            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }
}
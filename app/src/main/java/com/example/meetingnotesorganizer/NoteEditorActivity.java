package com.example.meetingnotesorganizer;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import com.example.meetingnotesorganizer.data.Note;
import com.example.meetingnotesorganizer.fragments.DatePickerFragment;
import com.example.meetingnotesorganizer.fragments.TimePickerFragment;
import com.example.meetingnotesorganizer.helpers.DatabaseHelper;
import com.example.meetingnotesorganizer.helpers.Utils;
import com.example.meetingnotesorganizer.services.NoteService;

import java.util.ArrayList;

public class NoteEditorActivity extends AppCompatActivity {
    private EditText titleText, dateText, timeText, descriptionText, participantsText;
    private Button backBtn, saveBtn;

    private int currentNoteId; // -1 indicates that the form is a create form
    private boolean isEditForm;
    private FragmentManager supportFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_notes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        try {
            titleText = findViewById(R.id.titleText);
            dateText = findViewById(R.id.dateText);
            timeText = findViewById(R.id.timeText);
            descriptionText = findViewById(R.id.descriptionText);
            participantsText = findViewById(R.id.meetingParticipantsText);

            backBtn = findViewById(R.id.backBtn);
            saveBtn = findViewById(R.id.saveBtn);

            currentNoteId = getIntent().getIntExtra("noteId", -1);
            isEditForm = currentNoteId != -1;
            supportFragmentManager = getSupportFragmentManager();

            setClickListeners();
        } catch (Exception err) {
            err.printStackTrace();
            Utils.longToast(err.getMessage(), this);
        }
    }

    private void setClickListeners() {
        backBtn.setOnClickListener(v -> {
            finish();
        });
        saveBtn.setOnClickListener(v -> {
            String title = Utils.getText(titleText);
            String date = Utils.getText(dateText);
            String time = Utils.getText(timeText);
            String description = Utils.getText(descriptionText);
            String participants = Utils.getText(participantsText);

            if (title.isEmpty() || date.isEmpty() || time.isEmpty() || description.isEmpty()) {
                Utils.longToast("Please fill the required text fields!", NoteEditorActivity.this);
                return;
            }

            String[] currentParticipants = participants.split(",");
            ArrayList<String> participantsList = new ArrayList<>();
            for (String participant : currentParticipants) {
                participantsList.add(participant.trim());
            }

            if (isEditForm) {
                Note note = DatabaseHelper.getNoteBank().get(currentNoteId);
                note.setTitle(title);
                note.setDate(date);
                note.setTime(time);
                note.setDescription(description);
                note.setParticipants(participantsList);
                NoteService.edit(note);
                Utils.longToast("Note has successfully been edited!", NoteEditorActivity.this);
            } else {
                NoteService.add(new Note(title, date, time, description, participantsList));
                Utils.longToast("Note has successfully been added!", NoteEditorActivity.this);
            }
        });

        dateText.setOnClickListener(v -> {
            DatePickerFragment datePicker = new DatePickerFragment();
            datePicker.setDateText(dateText);
            datePicker.show(supportFragmentManager, "date picker");
        });
        timeText.setOnClickListener(v -> {
           TimePickerFragment timePicker = new TimePickerFragment();
           timePicker.setTimeText(timeText);
           timePicker.show(supportFragmentManager, "time picker");
        });
    }
}
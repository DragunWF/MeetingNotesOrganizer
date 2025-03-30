package com.example.meetingnotesorganizer.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.meetingnotesorganizer.data.Note;
import com.example.meetingnotesorganizer.services.NoteService;

import java.util.ArrayList;

public class DatabaseHelper {
    private static final String FILE_KEY = "db";

    private static SharedPreferences sharedPref;
    private static SharedPreferences.Editor editor;

    private static ModelBank<Note> noteBank;

    public static void initialize(Context context) {
        sharedPref = context.getSharedPreferences(FILE_KEY, Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        noteBank = new ModelBank<>(sharedPref, editor, "notes", Note.class);
    }

    public static ModelBank<Note> getNoteBank() {
        return noteBank;
    }

    public static void addDummyData() {
        if (noteBank.getAll().isEmpty()) {
            // Create some sample meeting notes

            // Meeting note 1
            ArrayList<String> participants1 = new ArrayList<>();
            participants1.add("John Smith");
            participants1.add("Mary Johnson");
            participants1.add("Robert Chen");
            NoteService.add(new Note("Project Kickoff Meeting", "2025-03-15", "09:00",
                    "Discussed project goals, timeline, and assigned initial tasks to team members.", participants1));

            // Meeting note 2
            ArrayList<String> participants2 = new ArrayList<>();
            participants2.add("Sarah Wilson");
            participants2.add("James Brown");
            participants2.add("Emily Davis");
            participants2.add("Michael Lee");
            NoteService.add(new Note("Weekly Status Update", "2025-03-22", "14:30",
                    "Reviewed progress on tasks, identified blockers, and updated project timeline.", participants2));

            // Meeting note 3
            ArrayList<String> participants3 = new ArrayList<>();
            participants3.add("Alex Taylor");
            participants3.add("Jennifer Garcia");
            participants3.add("David Kim");
            NoteService.add(new Note("Client Presentation Prep", "2025-03-28", "11:00",
                    "Finalized slides for client presentation, practiced delivery, and prepared for potential questions.", participants3));

            // Meeting note 4
            ArrayList<String> participants4 = new ArrayList<>();
            participants4.add("Lisa Wong");
            participants4.add("Chris Martinez");
            participants4.add("Jessica Adams");
            participants4.add("Thomas Wilson");
            participants4.add("Amanda Jackson");
            NoteService.add(new Note("Quarterly Planning", "2025-04-02", "10:00",
                    "Set objectives for Q2, reviewed budget allocations, and discussed hiring needs for upcoming projects.", participants4));

            // Meeting note 5
            ArrayList<String> participants5 = new ArrayList<>();
            participants5.add("Ryan Cooper");
            participants5.add("Sophia Miller");
            NoteService.add(new Note("One-on-One Review", "2025-04-05", "15:45",
                    "Discussed performance, career goals, and areas for improvement. Set development objectives for next quarter.", participants5));
        }
    }
}

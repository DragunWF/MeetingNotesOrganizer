package com.example.meetingnotesorganizer.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.meetingnotesorganizer.data.Note;

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

        }
    }
}

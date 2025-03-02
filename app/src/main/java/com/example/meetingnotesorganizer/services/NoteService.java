package com.example.meetingnotesorganizer.services;

import com.example.meetingnotesorganizer.data.Note;
import com.example.meetingnotesorganizer.helpers.DatabaseHelper;
import com.example.meetingnotesorganizer.helpers.ModelBank;

public class NoteService {
    private static ModelBank<Note> bank = DatabaseHelper.getNoteBank();

    public static void edit(Note note) {
        bank.update(note);
    }

    public static void add(Note note) {
        bank.add(note);
    }

    public static void delete(int id) {
        bank.delete(id);
    }
}

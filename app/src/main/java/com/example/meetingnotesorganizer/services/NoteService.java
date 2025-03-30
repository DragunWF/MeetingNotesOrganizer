package com.example.meetingnotesorganizer.services;

import com.example.meetingnotesorganizer.data.Note;
import com.example.meetingnotesorganizer.helpers.DatabaseHelper;
import com.example.meetingnotesorganizer.helpers.ModelBank;

public class NoteService {
    public static void edit(Note note) {
        ModelBank<Note> bank = DatabaseHelper.getNoteBank();
        bank.update(note);
    }

    public static void add(Note note) {
        ModelBank<Note> bank = DatabaseHelper.getNoteBank();
        bank.add(note);
    }

    public static void delete(int id) {
        ModelBank<Note> bank = DatabaseHelper.getNoteBank();
        bank.delete(id);
    }
}

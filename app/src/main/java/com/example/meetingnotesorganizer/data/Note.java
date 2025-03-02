package com.example.meetingnotesorganizer.data;

import java.util.ArrayList;

public class Note extends Model {
    private String title, date, time, description;
    private ArrayList<String> participants;

    public Note(String title, String date, String time, String description, ArrayList<String> meetingParticipants) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.description = description;
        this.participants = meetingParticipants;
    }

    @Override
    public String toString() {
        return "Note{" +
                "title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", description='" + description + '\'' +
                ", meetingParticipants=" + participants +
                ", id=" + id +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<String> meetingParticipants) {
        this.participants = meetingParticipants;
    }
}

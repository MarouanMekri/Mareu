package com.nucleon.maru.Model;

import java.util.List;

public class Meeting {
    public String date;
    public String subject;
    public String room;
    public List<String> participants;

    public Meeting(String date, String subject, String room, List<String> participants) {
        this.date = date;
        this.subject = subject;
        this.room = room;
        this.participants = participants;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }
}

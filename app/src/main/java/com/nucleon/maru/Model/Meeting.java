package com.nucleon.maru.Model;

import java.util.Date;
import java.util.List;

public class Meeting {
    public Date date;
    public String subject;
    public String room;
    public List<String> participants;

    public Meeting(Date date, String subject, String room, List<String> participants) {
        this.date = date;
        this.subject = subject;
        this.room = room;
        this.participants = participants;
    }

    public Date getDate() {
        return date;
    }

    public String getSubject() {
        return subject;
    }

    public String getRoom() {
        return room;
    }

    public List<String> getParticipants() {
        return participants;
    }
}

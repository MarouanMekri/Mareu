package com.nucleon.maru.Service;

import com.nucleon.maru.Model.Meeting;

import java.util.List;

public interface ApiService {
    List<Meeting> getMeetings();
    void createMeeting(Meeting meeting);
    void deleteMeeting(Meeting meeting);
}

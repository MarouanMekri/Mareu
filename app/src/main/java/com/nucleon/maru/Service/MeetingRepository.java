package com.nucleon.maru.Service;

import com.nucleon.maru.Model.Meeting;

import java.util.ArrayList;
import java.util.List;

public class MeetingRepository implements ApiService {

    private final List<Meeting> meetingsList = new ArrayList<>();

    @Override
    public List<Meeting> getMeetings() {
        return meetingsList;
    }

    @Override
    public void createMeeting(Meeting meeting) {
        meetingsList.add(meeting);
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        meetingsList.remove(meeting);
    }
}

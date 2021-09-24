package com.nucleon.maru.Service;

import androidx.lifecycle.MutableLiveData;

import com.nucleon.maru.Model.Meeting;

import java.util.ArrayList;
import java.util.List;

public class MeetingRepository {

    private static MeetingRepository instance;
    private final List<Meeting> meetingsList = new ArrayList<>();

    public static MeetingRepository getInstance() {
        if (instance == null) {
            instance = new MeetingRepository();
        }
        return instance;
    }

    public MutableLiveData<List<Meeting>> getMeetings() {
        MutableLiveData<List<Meeting>> data = new MutableLiveData<>();
        data.setValue(meetingsList);
        return data;
    }

    public void createMeeting(Meeting meeting) {
        meetingsList.add(meeting);
    }

    public void deleteMeeting(Meeting meeting) {
        meetingsList.remove(meeting);
    }
}

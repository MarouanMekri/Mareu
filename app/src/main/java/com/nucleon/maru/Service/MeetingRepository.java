package com.nucleon.maru.Service;

import androidx.lifecycle.MutableLiveData;

import com.nucleon.maru.Model.Meeting;

import java.util.ArrayList;
import java.util.List;

public class MeetingRepository implements ApiService {

    private final List<Meeting> meetingsList = new ArrayList<>();

    @Override
    public MutableLiveData<List<Meeting>> getMeetings() {
        MutableLiveData<List<Meeting>> data = new MutableLiveData<>();
        data.setValue(meetingsList);
        return data;
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

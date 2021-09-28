package com.nucleon.maru.Service;

import android.util.Log;

import com.nucleon.maru.Model.Meeting;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MeetingRepository implements ApiService {

    private final List<Meeting> meetingsList = new ArrayList<>();

    @Override
    public List<Meeting> getMeetings() {
        return meetingsList;
    }

    @Override
    public List<Meeting> getMeetingsFilteredByDate(Date date) {
        List<Meeting> result = new ArrayList<>();

        Calendar filterCal = Calendar.getInstance();
        filterCal.setTime(date);
        for (int i = 0; i < meetingsList.size(); i++) {
            Calendar meetingCal = Calendar.getInstance();
            meetingCal.setTime(meetingsList.get(i).getDate());
            boolean sameTime = filterCal.get(Calendar.HOUR_OF_DAY) == meetingCal.get(Calendar.HOUR_OF_DAY) && filterCal.get(Calendar.MINUTE) == meetingCal.get(Calendar.MINUTE);
            if (sameTime) result.add(meetingsList.get(i));
        }
        return result;
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

package com.nucleon.maru.Service;

import com.nucleon.maru.Model.Meeting;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
            boolean isMatched = filterCal.get(Calendar.HOUR_OF_DAY) == meetingCal.get(Calendar.HOUR_OF_DAY) && filterCal.get(Calendar.MINUTE) == meetingCal.get(Calendar.MINUTE);
            if (isMatched) result.add(meetingsList.get(i));
        }
        return result;
    }

    @Override
    public List<Meeting> getMeetingsFilteredByRoom(String room) {
        List<Meeting> result = new ArrayList<>();
        for (Meeting meeting : Objects.requireNonNull(meetingsList)) {
            if (meeting.getRoom().toLowerCase().contains(room.toLowerCase().trim())) {
                result.add(meeting);
            }
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
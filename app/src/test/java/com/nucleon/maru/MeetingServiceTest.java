package com.nucleon.maru;

import com.nucleon.maru.DI.DI;
import com.nucleon.maru.Model.Meeting;
import com.nucleon.maru.Service.ApiService;

import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MeetingServiceTest {

    private ApiService apiService;

    // Constants
    private final static Date date = new Date();
    private final static String subject = "Unit Test";
    private final static String room = "Réunion A";
    private final static List<String> participants = Arrays.asList("Mike@lamzone.com", "Albert@lamzone.com");

    @Before
    public void setup() {
        apiService = DI.getNewInstanceApiService();
    }

    @Test
    public void getMeetingsWithSuccess() {
        List<Meeting> meetings = apiService.getMeetings();
        Meeting meetingOne = new Meeting(date, subject, room, participants);
        Meeting meetingTwo = new Meeting(date, subject, room, participants);
        apiService.createMeeting(meetingOne);
        apiService.createMeeting(meetingTwo);
        assertTrue(meetings.size() == 2 && meetings.contains(meetingOne) && meetings.contains(meetingTwo));
    }

    @Test
    public void getMeetingsFilteredByDateWithSuccess() throws Exception {
        Date specificDate = new SimpleDateFormat("HH:mm").parse("08:00");
        Meeting meetingOne = new Meeting(specificDate, subject, room, participants);
        Meeting meetingTwo = new Meeting(date, subject, room, participants);
        apiService.createMeeting(meetingOne);
        apiService.createMeeting(meetingTwo);
        List<Meeting> meetingsFiltered = apiService.getMeetingsFilteredByDate(specificDate);
        assertTrue(meetingsFiltered.size() == 1 && meetingsFiltered.contains(meetingOne));
    }

    @Test
    public void getMeetingsFilteredByRoomWithSuccess() {
        String specificRoom = "C";
        Meeting meetingOne = new Meeting(date, subject, specificRoom, participants);
        Meeting meetingTwo = new Meeting(date, subject, room, participants);
        apiService.createMeeting(meetingOne);
        apiService.createMeeting(meetingTwo);
        List<Meeting> meetingsFiltered = apiService.getMeetingsFilteredByRoom(specificRoom);
        assertTrue(meetingsFiltered.size() == 1 && meetingsFiltered.contains(meetingOne));
    }

    @Test
    public void addMeetingWithSuccess() {
        Meeting meeting = new Meeting(date, subject, room, participants);
        apiService.createMeeting(meeting);
        assertTrue(apiService.getMeetings().contains(meeting));
    }

    @Test
    public void deleteMeetingWithSuccess() {
        Meeting meeting = new Meeting(date, subject, room, participants);
        apiService.createMeeting(meeting);
        apiService.deleteMeeting(meeting);
        assertFalse(apiService.getMeetings().contains(meeting));
    }
}
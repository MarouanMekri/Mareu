package com.nucleon.maru.ViewModel;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.nucleon.maru.DI.DI;
import com.nucleon.maru.Model.Meeting;
import com.nucleon.maru.Service.ApiService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class AddMeetingViewModel extends ViewModel {

    private final ApiService apiService = DI.getApiService();
    private Date date = new Date();

    public void createMeeting(Meeting meeting) {
        apiService.createMeeting(meeting);
    }

    // Parsing inputDate from String to Date
    public Date parsingDate(String inputDate) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        try {
            date = dateFormat.parse(inputDate);
        }catch (ParseException ex) {
            Log.d("Parsing inputDate", Objects.requireNonNull(ex.getLocalizedMessage()));
        }
        return date;
    }

    // Meeting form validation
    public boolean isFormValid(String subject, String room, String inputDate, List<String> participants) {
        boolean result;
        // Data checking
        if (subject.isEmpty() || participants.isEmpty() || room.isEmpty() || inputDate.isEmpty()){
            result = true;
        }else{
            // Create a meeting
            Meeting meeting = new Meeting(parsingDate(inputDate), subject, room, participants);
            createMeeting(meeting);
            result = false;
        }
        return result;
    }
}

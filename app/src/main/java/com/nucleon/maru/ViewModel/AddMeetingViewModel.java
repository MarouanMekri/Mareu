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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddMeetingViewModel extends ViewModel {

    private final ApiService apiService = DI.getApiService();
    private Date date = new Date();

    // REGEX
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

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

    // Checking email format
    public boolean isEmailFormatValid(final List<String> participants) {
        boolean status = true;
        for (String participant : participants) {
            Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(participant);
            if (!(status = matcher.lookingAt())) {
                break;
            }
        }
        return status;
    }

    // Meeting form validation
    public boolean isFormValid(String subject, String room, String inputDate, List<String> participants) {
        boolean result;
        // Data checking
        if ((!subject.isEmpty() || !participants.isEmpty() || !room.isEmpty() || !inputDate.isEmpty()) && isEmailFormatValid(participants)){
            // Create a meeting
            Meeting meeting = new Meeting(parsingDate(inputDate), subject, room, participants);
            createMeeting(meeting);
            result = true;
        } else {
            result = false;
        }
        return result;
    }
}

package com.nucleon.maru.ViewModel;

import androidx.lifecycle.ViewModel;

import com.nucleon.maru.DI.DI;
import com.nucleon.maru.Model.Meeting;
import com.nucleon.maru.Service.ApiService;
import com.nucleon.maru.Service.MeetingRepository;

public class AddMeetingViewModel extends ViewModel {

    private final ApiService apiService = DI.getApiService();

    public void createMeeting(Meeting meeting) {
        apiService.createMeeting(meeting);
    }
}

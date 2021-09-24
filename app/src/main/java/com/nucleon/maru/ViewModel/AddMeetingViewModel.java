package com.nucleon.maru.ViewModel;

import androidx.lifecycle.ViewModel;

import com.nucleon.maru.Model.Meeting;
import com.nucleon.maru.Service.MeetingRepository;

public class AddMeetingViewModel extends ViewModel {

    private final MeetingRepository repository = MeetingRepository.getInstance();

    public void createMeeting(Meeting meeting) {
        repository.createMeeting(meeting);
    }
}

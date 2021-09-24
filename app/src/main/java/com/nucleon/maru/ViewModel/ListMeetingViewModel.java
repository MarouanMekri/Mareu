package com.nucleon.maru.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nucleon.maru.Model.Meeting;
import com.nucleon.maru.Service.MeetingRepository;

import java.util.List;

public class ListMeetingViewModel extends ViewModel {

    private final MeetingRepository repository = MeetingRepository.getInstance();

    public LiveData<List<Meeting>> getMeetings() {
        MutableLiveData<List<Meeting>> mutableMeetingsList;
        return mutableMeetingsList = repository.getMeetings();
    }

    public void deleteMeeting(Meeting meeting) {
        repository.deleteMeeting(meeting);
    }
}

package com.nucleon.maru.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nucleon.maru.DI.DI;
import com.nucleon.maru.Model.Meeting;
import com.nucleon.maru.Service.ApiService;
import com.nucleon.maru.Service.MeetingRepository;

import java.util.List;

public class ListMeetingViewModel extends ViewModel {

    private final ApiService apiService = DI.getApiService();

    public LiveData<List<Meeting>> getMeetings() {
        MutableLiveData<List<Meeting>> mutableMeetingsList;
        return mutableMeetingsList = apiService.getMeetings();
    }

    public void deleteMeeting(Meeting meeting) {
        apiService.deleteMeeting(meeting);
    }
}

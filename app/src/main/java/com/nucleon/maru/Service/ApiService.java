package com.nucleon.maru.Service;

import androidx.lifecycle.MutableLiveData;

import com.nucleon.maru.Model.Meeting;

import java.util.List;

public interface ApiService {
    MutableLiveData<List<Meeting>> getMeetings();
    void createMeeting(Meeting meeting);
    void deleteMeeting(Meeting meeting);
}

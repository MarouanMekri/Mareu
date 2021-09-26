package com.nucleon.maru.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nucleon.maru.DI.DI;
import com.nucleon.maru.Model.Meeting;
import com.nucleon.maru.Service.ApiService;

import java.util.List;

public class ListMeetingViewModel extends ViewModel {

    private final ApiService apiService = DI.getApiService();
    public MutableLiveData<List<Meeting>> mutableMeetingsList = new MutableLiveData<>();

    // Return meetings list
    public LiveData<List<Meeting>> getMeetings() {
        mutableMeetingsList.setValue(apiService.getMeetings());
        return mutableMeetingsList;
    }
}

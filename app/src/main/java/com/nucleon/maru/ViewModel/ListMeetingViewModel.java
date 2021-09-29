package com.nucleon.maru.ViewModel;

import android.app.TimePickerDialog;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nucleon.maru.DI.DI;
import com.nucleon.maru.Model.Meeting;
import com.nucleon.maru.Service.ApiService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ListMeetingViewModel extends ViewModel {

    private ApiService apiService = DI.getApiService();
    public MutableLiveData<List<Meeting>> meetingsLiveData = new MutableLiveData<>();

    public final List<Meeting> meetingList = new ArrayList<>();

    // Return meetings list
    public LiveData<List<Meeting>> getMeetingsLiveData() {
        meetingsLiveData.setValue(apiService.getMeetings());
        return meetingsLiveData;
    }

    // Time Filter
    public void filterByTime(Context context) {
        // Default date
        int selectedYear = 1970;
        int selectedMonth = 1;
        int selectedDayOfMonth = 1;

        TimePickerDialog.OnTimeSetListener timeSetListener = (timePicker, hour, minute) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(selectedYear, selectedMonth, selectedDayOfMonth, hour, minute);
            meetingList.clear();
            meetingsLiveData.setValue(meetingList);
            meetingList.addAll(apiService.getMeetingsFilteredByDate(calendar.getTime()));
            meetingsLiveData.setValue(meetingList);
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, timeSetListener, 12, 0, true);
        timePickerDialog.show();
    }

    // Room Filter
    public void filterByRoom(Context context) {}

    // Reset filters
    public void resetFilters() {
        meetingList.clear();
        meetingsLiveData.setValue(meetingList);
        meetingList.addAll(apiService.getMeetings());
        meetingsLiveData.setValue(meetingList);
    }
}

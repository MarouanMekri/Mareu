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
import java.util.Objects;

public class ListMeetingViewModel extends ViewModel {

    private final ApiService apiService = DI.getApiService();
    public MutableLiveData<List<Meeting>> mutableMeetingsList = new MutableLiveData<>();

    public final List<Meeting> meetingList = new ArrayList<>();

    // Return meetings list
    public LiveData<List<Meeting>> getMeetings() {
        mutableMeetingsList.setValue(apiService.getMeetings());
        return mutableMeetingsList;
    }

    // Time Filter
    public void filterByTime(Context context) {
        int selectedYear = 1970;
        int selectedMonth = 1;
        int selectedDayOfMonth = 1;

        TimePickerDialog.OnTimeSetListener timeSetListener = (timePicker, hour, minute) -> {
            Calendar cal = Calendar.getInstance();
            cal.set(selectedYear, selectedMonth, selectedDayOfMonth, hour, minute);
            // notify adapter with empty list
            meetingList.clear();
            // notify adapter with new list
            meetingList.addAll(apiService.getMeetingsFilteredByDate(cal.getTime()));
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, timeSetListener, 12, 0, true);
        timePickerDialog.show();
    }

    // Room Filter
    public void filterByRoom(Context context) {}

    // Reset filters
    public void resetFilters() {
        meetingList.clear();
        meetingList.addAll(Objects.requireNonNull(getMeetings().getValue()));
    }
}

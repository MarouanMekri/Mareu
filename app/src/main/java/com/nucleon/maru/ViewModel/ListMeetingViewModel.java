package com.nucleon.maru.ViewModel;

import android.app.TimePickerDialog;
import android.content.Context;
import android.view.MenuItem;

import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nucleon.maru.DI.DI;
import com.nucleon.maru.Model.Meeting;
import com.nucleon.maru.Service.ApiService;

import java.util.Calendar;
import java.util.List;

public class ListMeetingViewModel extends ViewModel {

    private final ApiService apiService = DI.getApiService();

    private final MutableLiveData<List<Meeting>> meetingsLiveData = new MutableLiveData<>();

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
            meetingsLiveData.setValue(apiService.getMeetingsFilteredByDate(calendar.getTime()));
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, timeSetListener, 12, 0, true);
        timePickerDialog.show();
    }

    // Room Filter
    public void filterByRoom(MenuItem item) {
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                meetingsLiveData.setValue(apiService.getMeetingsFilteredByRoom(newText));
                return false;
            }
        });
    }

    // Reset filters
    public void resetFilters() {
        meetingsLiveData.setValue(apiService.getMeetings());
    }
}

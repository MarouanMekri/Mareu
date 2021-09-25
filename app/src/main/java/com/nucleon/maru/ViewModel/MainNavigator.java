package com.nucleon.maru.ViewModel;

import android.view.View;

import com.nucleon.maru.Model.Meeting;

public interface MainNavigator {
    void itemCreate(View view);
    void itemDelete(Meeting meeting);
}

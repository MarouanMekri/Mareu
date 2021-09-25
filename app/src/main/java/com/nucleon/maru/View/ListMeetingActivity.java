package com.nucleon.maru.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.nucleon.maru.Adapter.ListMeetingAdapter;
import com.nucleon.maru.R;
import com.nucleon.maru.ViewModel.ListMeetingViewModel;
import com.nucleon.maru.ViewModel.MainNavigator;
import com.nucleon.maru.databinding.ActivityListMeetingBinding;

public class ListMeetingActivity extends AppCompatActivity implements MainNavigator {

    private ListMeetingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.nucleon.maru.databinding.ActivityListMeetingBinding binding = ActivityListMeetingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ListMeetingViewModel listMeetingViewModel = new ViewModelProvider(this).get(ListMeetingViewModel.class);

        adapter = new ListMeetingAdapter(listMeetingViewModel.getMeetings().getValue());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        binding.listMeetings.setLayoutManager(layoutManager);
        binding.listMeetings.setAdapter(adapter);

        binding.fabAddMeeting.setOnClickListener(v -> {
            AddMeetingFragment addMeetingFragment = new AddMeetingFragment();
            addMeetingFragment.show(getSupportFragmentManager(), "AddMeetingFragment");
        });

    }

    @Override
    public void itemCreate(View view) {
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filter_menu, menu);
        return true;
    }
}
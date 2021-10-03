package com.nucleon.maru.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.nucleon.maru.Adapter.ListMeetingAdapter;
import com.nucleon.maru.R;
import com.nucleon.maru.ViewModel.ListMeetingViewModel;
import com.nucleon.maru.ViewModel.MainNavigator;
import com.nucleon.maru.databinding.ActivityListMeetingBinding;

public class ListMeetingActivity extends AppCompatActivity implements MainNavigator {

    private ActivityListMeetingBinding binding;
    private ListMeetingViewModel listMeetingViewModel;
    private ListMeetingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ViewBinding initialization
        binding = ActivityListMeetingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // ViewModel initialization
        listMeetingViewModel = new ViewModelProvider(this).get(ListMeetingViewModel.class);

        // RecyclerView initialization
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        DividerItemDecoration decoration = new DividerItemDecoration(binding.listMeetings.getContext(), layoutManager.getOrientation());
        binding.listMeetings.setLayoutManager(layoutManager);
        binding.listMeetings.addItemDecoration(decoration);
        adapter = new ListMeetingAdapter(this, listMeetingViewModel.getMeetingsLiveData().getValue());
        binding.listMeetings.setAdapter(adapter);

        // Observe data
        listMeetingViewModel.getMeetingsLiveData().observe(this, meetings -> {
            adapter.setData(meetings);
            binding.listMeetings.setAdapter(adapter);
        });

        // Add meeting button
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
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.filter_date:
                listMeetingViewModel.filterByTime(ListMeetingActivity.this);
                return true;
            case R.id.filter_room:
                listMeetingViewModel.filterByRoom(item);
                return true;
            case R.id.filters_reset:
                listMeetingViewModel.resetFilters(adapter);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
package com.nucleon.maru.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import com.nucleon.maru.Adapter.ListMeetingAdapter;
import com.nucleon.maru.R;
import com.nucleon.maru.ViewModel.ListMeetingViewModel;
import com.nucleon.maru.databinding.ActivityListMeetingBinding;

public class ListMeetingActivity extends AppCompatActivity implements AddMeetingFragment.OnItemClickedListener {

    private ActivityListMeetingBinding binding;
    private ListMeetingViewModel listMeetingViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListMeetingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        listMeetingViewModel = ViewModelProviders.of(this).get(ListMeetingViewModel.class);

        binding.fabAddMeeting.setOnClickListener(v -> {
            AddMeetingFragment addMeetingFragment = new AddMeetingFragment();
            addMeetingFragment.show(getSupportFragmentManager(), "AddMeetingFragment");
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filter_menu, menu);
        return true;
    }

    @Override
    public void onItemClicked(View view) {
        initRecycler();
    }

    void initRecycler() {
        ListMeetingAdapter adapter = new ListMeetingAdapter(listMeetingViewModel.getMeetings().getValue());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        binding.listMeetings.setLayoutManager(layoutManager);
        binding.listMeetings.setAdapter(adapter);
    }
}
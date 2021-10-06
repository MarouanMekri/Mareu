package com.nucleon.maru.View;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.nucleon.maru.R;
import com.nucleon.maru.ViewModel.AddMeetingViewModel;
import com.nucleon.maru.ViewModel.MainNavigator;
import com.nucleon.maru.databinding.FragmentAddMeetingBinding;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

public class AddMeetingFragment extends DialogFragment implements MainNavigator {

    private FragmentAddMeetingBinding binding;
    private AddMeetingViewModel addMeetingViewModel;

    private int lastSelectedHour;
    private int lastSelectedMinute;

    private MainNavigator navigator;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentAddMeetingBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        addMeetingViewModel = new ViewModelProvider(this).get(AddMeetingViewModel.class);

        // Setup spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.rooms, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner.setAdapter(adapter);

        binding.btnTime.setOnClickListener(v -> {
            // Time Set Listener.
            TimePickerDialog.OnTimeSetListener timeSetListener = (childView, hour, minute) -> {
                binding.edtDate.setText(MessageFormat.format("{0}:{1}", hour, minute));
                lastSelectedHour = hour;
                lastSelectedMinute = minute;
            };
            // Create and show TimePickerDialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), timeSetListener, lastSelectedHour, lastSelectedMinute, true);
            timePickerDialog.show();
        });

        // Callback button
        binding.btnAddMeeting.setOnClickListener(this::itemCreate);

        return view;
    }

    @Override
    public void itemCreate(View view) {
        // Get meeting info from the form
        String subject = binding.edtSubject.getText().toString();
        String room = binding.spinner.getSelectedItem().toString();
        String inputDate = binding.edtDate.getText().toString();
        List<String> participants = Arrays.asList(binding.edtParticipants.getText().toString().split("\n"));
        // User data checking
        if (!addMeetingViewModel.isFormValid(subject, room, inputDate, participants)) {
            // Updating recyclerView
            navigator.itemCreate(view);
            // Close dialog fragment
            dismiss();
        } else {
            // Showing error message
            Toast.makeText(getContext(), "Please complete the form...", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.createCallbackToParentActivity();
    }

    private void createCallbackToParentActivity(){
        try {
            // Parent activity will automatically subscribe to callback
            navigator = (MainNavigator) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString()+ " must implement OnButtonClickedListener");
        }
    }
}

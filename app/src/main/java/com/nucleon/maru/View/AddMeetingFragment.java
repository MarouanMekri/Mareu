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
import androidx.lifecycle.ViewModelProviders;

import com.nucleon.maru.Model.Meeting;
import com.nucleon.maru.R;
import com.nucleon.maru.ViewModel.AddMeetingViewModel;
import com.nucleon.maru.databinding.FragmentAddMeetingBinding;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

public class AddMeetingFragment extends DialogFragment implements View.OnClickListener {

    private FragmentAddMeetingBinding binding;
    private AddMeetingViewModel addMeetingViewModel;

    private int lastSelectedHour;
    private int lastSelectedMinute;

    private OnItemClickedListener myCallback;

    public interface OnItemClickedListener {
        void onItemClicked(View view);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentAddMeetingBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        addMeetingViewModel = ViewModelProviders.of(this).get(AddMeetingViewModel.class);

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
        binding.btnAddMeeting.setOnClickListener(this);

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.createCallbackToParentActivity();
    }

    @Override
    public void onClick(View v) {
        // Get meeting info from the form
        String subject = binding.edtSubject.getText().toString();
        List<String> participants = Arrays.asList(binding.edtParticipants.getText().toString().split("\n"));
        String room = binding.spinner.getSelectedItem().toString();
        String date = binding.edtDate.getText().toString();
        // Data checking
        if (subject.isEmpty() || participants.isEmpty() || room.isEmpty() || date.isEmpty()){
            Toast.makeText(getContext(), "Please complete the form...", Toast.LENGTH_SHORT).show();
        } else {
            // Create a meeting
            Meeting meeting = new Meeting(date, subject, room, participants);
            addMeetingViewModel.createMeeting(meeting);
            // Updating recyclerView
            myCallback.onItemClicked(v);
            // Close dialog fragment
            dismiss();
        }
    }

    private void createCallbackToParentActivity(){
        try {
            // Parent activity will automatically subscribe to callback
            myCallback = (OnItemClickedListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString()+ " must implement OnButtonClickedListener");
        }
    }
}

package com.nucleon.maru.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nucleon.maru.Model.Meeting;
import com.nucleon.maru.databinding.FragmentMeetingRowBinding;

import java.text.MessageFormat;
import java.util.List;

public class ListMeetingAdapter extends RecyclerView.Adapter<ListMeetingAdapter.ViewHolder> {

    private final List<Meeting> meetingList;

    public ListMeetingAdapter(List<Meeting> meetingList) {
        this.meetingList = meetingList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentMeetingRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListMeetingAdapter.ViewHolder holder, int position) {
        // Getting date from view
        String subject = meetingList.get(position).getSubject();
        String room = meetingList.get(position).getRoom();
        String date = meetingList.get(position).getDate();
        // Change date format
        date = date.replace(":", "h");
        // Build title
        String title = MessageFormat.format("{0} - {1} - {2}", room, date, subject);
        // Split participantList
        List<String> participantList = meetingList.get(position).getParticipants();
        String participants = "";
        for (int i = 0 ; i < participantList.size() ; i++) {
            if ( i == 0 ){
                participants = participantList.get(i);
            }else{
                participants = MessageFormat.format("{0}, {1}", participants, participantList.get(i));
            }
        }
        // Show the row in recyclerview
        holder.item_list_title.setText(title);
        holder.item_list_subtitle.setText(participants);
    }

    @Override
    public int getItemCount() {
        return meetingList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView item_list_title;
        private final TextView item_list_subtitle;

        public ViewHolder(FragmentMeetingRowBinding itemBinding) {
            super(itemBinding.getRoot());
            item_list_title = itemBinding.itemListTitle;
            item_list_subtitle = itemBinding.itemListSubtitle;
        }
    }
}

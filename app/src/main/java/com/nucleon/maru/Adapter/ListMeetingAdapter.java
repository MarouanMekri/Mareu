package com.nucleon.maru.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nucleon.maru.DI.DI;
import com.nucleon.maru.Model.Meeting;
import com.nucleon.maru.Service.ApiService;
import com.nucleon.maru.databinding.FragmentMeetingRowBinding;

import java.text.MessageFormat;
import java.util.List;

public class ListMeetingAdapter extends RecyclerView.Adapter<ListMeetingAdapter.ViewHolder> {

    private final List<Meeting> meetingList;
    private final ApiService apiService = DI.getApiService();

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
        // Show recyclerview row
        holder.item_list_title.setText(title);
        holder.item_list_subtitle.setText(participants);
        // Delete a row
        holder.item_list_delete.setOnClickListener(v -> {
            apiService.deleteMeeting(meetingList.get(position));
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return meetingList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView item_list_title;
        private final TextView item_list_subtitle;
        private final ImageView item_list_delete;

        public ViewHolder(FragmentMeetingRowBinding itemBinding) {
            super(itemBinding.getRoot());
            item_list_title = itemBinding.itemListTitle;
            item_list_subtitle = itemBinding.itemListSubtitle;
            item_list_delete = itemBinding.itemListDelete;
        }
    }
}

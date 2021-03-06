package com.nucleon.maru.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.nucleon.maru.DI.DI;
import com.nucleon.maru.Model.Meeting;
import com.nucleon.maru.R;
import com.nucleon.maru.Service.ApiService;
import com.nucleon.maru.databinding.FragmentMeetingRowBinding;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class ListMeetingAdapter extends RecyclerView.Adapter<ListMeetingAdapter.ViewHolder> {

    private final ApiService apiService = DI.getApiService();
    private final Context context;

    private List<Meeting> meetingList;

    public ListMeetingAdapter(Context context, List<Meeting> meetingList) {
        this.context = context;
        this.meetingList = meetingList;
    }

    // Updating UI
    public void setData(List<Meeting> meetingList) {
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
        Date date = meetingList.get(position).getDate();
        // Change date format
        StringBuilder outputDate = new StringBuilder(date.toString());
        // Format : EEE MMM dd hh:mm:ss z yyyy ==> hh:mm:ss z yyyy
        outputDate.delete(0,11);
        // Format : hh:mm:ss z yyyy ==> hh:mm
        outputDate.delete(5,23);
        outputDate.replace(2,3,"h");
        // Build title
        String title = MessageFormat.format("{0} - {1} - {2}", room, outputDate, subject);
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
        // Delete recyclerview row
        holder.item_list_delete.setOnClickListener(v -> {
            apiService.deleteMeeting(meetingList.get(position));
            notifyDataSetChanged();
        });
        // Select randomly avatar color
        List<Integer> colors = Arrays.asList(R.color.avatar_cyan, R.color.avatar_green, R.color.avatar_red);
        Random random = new Random();
        int randomColor = colors.get(random.nextInt(colors.size()));
        holder.item_list_avatar.setBackgroundTintList(ContextCompat.getColorStateList(context, randomColor));
    }

    @Override
    public int getItemCount() {
        return meetingList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // UI components
        private final TextView item_list_title;
        private final TextView item_list_subtitle;
        private final ImageView item_list_delete;
        private final ImageView item_list_avatar;

        public ViewHolder(FragmentMeetingRowBinding itemBinding) {
            super(itemBinding.getRoot());
            item_list_title = itemBinding.itemListTitle;
            item_list_subtitle = itemBinding.itemListSubtitle;
            item_list_delete = itemBinding.itemListDelete;
            item_list_avatar = itemBinding.itemListAvatar;
        }
    }
}

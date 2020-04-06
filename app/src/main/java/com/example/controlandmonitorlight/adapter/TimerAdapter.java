package com.example.controlandmonitorlight.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controlandmonitorlight.R;
import com.example.controlandmonitorlight.model.Timer;
import com.example.controlandmonitorlight.repositories.RealtimeFirebaeRepository;


import java.util.List;

public class TimerAdapter extends RecyclerView.Adapter<TimerAdapter.ViewHolder> {
    private final String TAG = "TIMER_ADAPTER";
    private OnItemClickListener listener;

    private List<Timer> mTimers;
//
//    private static final DiffUtil.ItemCallback<Timer> DIFF_CALLBACK = new DiffUtil.ItemCallback<Timer>() {
//        @Override
//        public boolean areItemsTheSame(@NonNull Timer oldItem, @NonNull Timer newItem) {
//            return oldItem.getId().equals(newItem.getId());
//        }
//
//        @Override
//        public boolean areContentsTheSame(@NonNull Timer oldItem, @NonNull Timer newItem) {
//            return oldItem.getStatus() == newItem.getStatus() &&
//                    oldItem.getType().equals(newItem.getType()) &&
//                    oldItem.getHour() == newItem.getHour() &&
//                    oldItem.getMinute() == newItem.getMinute() &&
//                    oldItem.getLabel().equals(newItem.getLabel()) &&
//                    oldItem.getRepeat().equals(newItem.getRepeat());
//        }
//    };

    public TimerAdapter(List<Timer> mList) {
        this.mTimers = mList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Timer currentTimer = mTimers.get(position);

        holder.txtLabel.setText(currentTimer.getLabel());
        holder.txtTime.setText(currentTimer.getTime());
        if(currentTimer.getStatus() == Timer.STATUS_ON) {
            holder.txtStatus.setText(Timer.STRING_ON);
            holder.switchCompat.setChecked(true);
        } else {
            holder.txtStatus.setText(Timer.STRING_OFF);
            holder.switchCompat.setChecked(false);
        }

        holder.switchCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RealtimeFirebaeRepository.getInstance()
                        .toggleStatusTimer(1,
                                mTimers.get(position).getId(),
                                holder.switchCompat.isChecked() ? Timer.STATUS_ON : Timer.STATUS_OFF);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTimers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTime;
        TextView txtLabel;
        SwitchCompat switchCompat;
        TextView txtStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTime = itemView.findViewById(R.id.txt_time_display);
            txtLabel = itemView.findViewById(R.id.txt_label_display);
            txtStatus = itemView.findViewById(R.id.txt_status);
            switchCompat = itemView.findViewById(R.id.switch_compat);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(mTimers.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Timer timer);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}

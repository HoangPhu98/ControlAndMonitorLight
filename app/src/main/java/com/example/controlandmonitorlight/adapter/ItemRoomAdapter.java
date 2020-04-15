package com.example.controlandmonitorlight.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.controlandmonitorlight.R;

import java.util.ArrayList;
import java.util.List;

public class ItemRoomAdapter extends RecyclerView.Adapter<ItemRoomAdapter.ViewHolder> {

    private List<String> Name ;
    private Context context ;
    private List<String> Devices;

    public ItemRoomAdapter(List<String> name, Context context) {
        this.Name = name;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameRoom.setText(Name.get(position));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL,false);
        holder.recyclerView.setLayoutManager(layoutManager);
        holder.recyclerView.setHasFixedSize(true);
        Devices = new ArrayList<>();
        Devices.clear();
        Devices.add("Light 1") ;
        Devices.add("Light 2") ;
        SubItemDevicesAdapter subItemDevicesAdapter = new SubItemDevicesAdapter(Devices) ;
        holder.recyclerView.setAdapter(subItemDevicesAdapter);
    }

    @Override
    public int getItemCount() {
        return Name.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameRoom , numberTotalWalt ;
        RecyclerView recyclerView ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameRoom = itemView.findViewById(R.id.room);
            numberTotalWalt = itemView.findViewById(R.id.total_walt);
            recyclerView = itemView.findViewById(R.id.child_item_devices);
        }
    }
}

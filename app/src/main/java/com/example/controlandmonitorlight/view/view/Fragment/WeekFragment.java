package com.example.controlandmonitorlight.view.view.Fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.controlandmonitorlight.R;
import com.example.controlandmonitorlight.Repository.DeviceInterface;
import com.example.controlandmonitorlight.model.DeviceStatic;
import com.example.controlandmonitorlight.viewmodel.DeviceStaticViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeekFragment extends Fragment {

    public static final String TITLE = "Week";
    public static WeekFragment newInstance() {
        return new WeekFragment();
    }
    public TextView mTotal ;
    public TextView mTimeOn ;
    public TextView mTimeNow ;
    public WeekFragment() {
        // Required empty public constructor
    }
    DeviceStaticViewModel deviceStaticViewModel ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view = inflater.inflate(R.layout.fragment_week, container, false);
        mTotal = view.findViewById(R.id.totalw);
        mTimeOn = view.findViewById(R.id.electricw);
        mTimeNow = view.findViewById(R.id.txt_metadataw);
        deviceStaticViewModel = ViewModelProviders.of(this).get(DeviceStaticViewModel.class);
        int id1 = 1;
        deviceStaticViewModel.getData(id1, DeviceInterface.WEEK,getContext());
        deviceStaticViewModel.deviceData.observe(this, new Observer<DeviceStatic>() {
            @Override
            public void onChanged(DeviceStatic deviceStatic) {
                mTotal.setText("TotalWatt: "+deviceStatic.getTotalWatt());
                mTimeOn.setText("TimeOn: "+deviceStatic.getTimeOn());
                String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                mTimeNow.setText("Time is now: "+currentTime);
            }
        });


        return view ;
    }

}

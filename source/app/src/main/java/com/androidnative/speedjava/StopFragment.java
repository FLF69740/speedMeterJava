package com.androidnative.speedjava;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class StopFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";

    private short mAverage;

    public StopFragment() {}

    static StopFragment newInstance(short param1) {
        StopFragment fragment = new StopFragment();
        Bundle args = new Bundle(1);
        args.putShort(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stop, container, false);

        if (getArguments() != null) {
            mAverage = getArguments().getShort(ARG_PARAM1, (short) 0);
        }

        TextView resultSpeedAverage = view.findViewById(R.id.result_speed);
        TextView subTitle = view.findViewById(R.id.title_stop_result);

        resultSpeedAverage.setText(String.valueOf(mAverage));

        if (mAverage == 0){
            subTitle.setVisibility(View.INVISIBLE);
        }

        return view;
    }
}

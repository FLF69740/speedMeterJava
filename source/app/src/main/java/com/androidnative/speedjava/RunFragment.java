package com.androidnative.speedjava;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidnative.speedjava.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RunFragment extends Fragment {

    private TextView mSpeed, mAverage;

    static Fragment newInstance(){
        return new RunFragment();
    }

    public RunFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_run, container, false);

        mSpeed = view.findViewById(R.id.counter_speed);
        mAverage = view.findViewById(R.id.average);

        return view;
    }

    public void updateUI(String speed, String average){
        mSpeed.setText(speed);
        mAverage.setText(average);
    }
}

package com.androidnative.speedjava;

import android.widget.TextView;
import com.androidnative.speedjava.business.CAverage;

public class MainActivity extends BaseActivity {

    private TextView mSpeed, average;
    private CAverage mCalculator;

    @Override
    public int getLayout() { return R.layout.activity_main; }

    @Override
    public void bodyActivity() {
        mSpeed = findViewById(R.id.counter_speed);
        average = findViewById(R.id.average);

        mCalculator = new CAverage();
    }

    /**
     *  RX JAVA
     */

    @Override
    public void subscriberAction(short speedResult) {
        mSpeed.setText(String.valueOf(speedResult));
        mCalculator.setValue(speedResult);
        average.setText(mCalculator.getResult());
    }

}

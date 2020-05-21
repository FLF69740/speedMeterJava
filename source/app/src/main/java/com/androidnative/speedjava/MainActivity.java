package com.androidnative.speedjava;

import androidx.fragment.app.Fragment;

import com.androidnative.speedjava.business.CAverage;

public class MainActivity extends BaseActivity {

    private static final String RUN_FRAGMENT = "RUN_FRAGMENT";
    private static final String STOP_FRAGMENT = "STOP_FRAGMENT";

    private CAverage mCalculator;

    @Override
    public int getLayout() { return R.layout.activity_main; }

    @Override
    public int getFragmentLayout() { return R.id.main_activity_fragmentlayout; }

    @Override
    public Fragment getFragment() { return RunFragment.newInstance(); }

    @Override
    public String getFragmentTag() {
        return RUN_FRAGMENT;
    }

    @Override
    public void bodyActivity() {
        mCalculator = new CAverage();
    }

    /**
     *  RX JAVA
     */

    @Override
    public void subscriberAction(short speedResult) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(RUN_FRAGMENT);

        if (speedResult != 0) {

            if (fragment != null) {
                mCalculator.setValue(speedResult);
                ((RunFragment) fragment).updateUI(String.valueOf(speedResult), mCalculator.getResult());
            } else {
                getSupportFragmentManager().beginTransaction().replace(getFragmentLayout(), getFragment(), getFragmentTag()).commit();
            }
        } else {
            // speedResult = 0
            if (fragment != null){
                getSupportFragmentManager().beginTransaction().replace(getFragmentLayout(), StopFragment.newInstance((short) mCalculator.getAverage()), STOP_FRAGMENT).commit();
            }
        }
    }



}

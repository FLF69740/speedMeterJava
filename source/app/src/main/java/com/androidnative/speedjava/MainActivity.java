package com.androidnative.speedjava;

import android.os.CountDownTimer;
import android.widget.TextView;
import com.androidnative.speedjava.business.CAverage;
import java.util.Calendar;
import io.reactivex.observers.DisposableObserver;

public class MainActivity extends BaseActivity {

    private TextView mSpeed, hour, average;
    private CountDownTimer mTimer;
    private CAverage mCalculator;

    @Override
    public int getLayout() { return R.layout.activity_main; }

    @Override
    public void bodyActivity() {
        mSpeed = findViewById(R.id.counter_speed);
        hour = findViewById(R.id.hour);
        average = findViewById(R.id.average);

        mCalculator = new CAverage();

        mTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long l) {
                mDisposable = getObservable().subscribeWith(getSubscriber());
            }

            @Override
            public void onFinish() {
                start();
            }
        }.start();
    }

    @Override
    public void speedAction(short result) {
        mSpeed.setText(String.valueOf(result));
    }


    /**
     *  RX JAVA
     */

    private DisposableObserver<Calendar> getSubscriber(){
        return new DisposableObserver<Calendar>() {
            @Override
            public void onNext(Calendar calendar) {
                int sec = calendar.get(Calendar.SECOND);
                hour.setText(String.valueOf(sec));
                mCalculator.setValue(Short.parseShort(mSpeed.getText().toString()));
                average.setText(mCalculator.getResult());
            }

            @Override
            public void onError(Throwable e) {}

            @Override
            public void onComplete() {}
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTimer.cancel();
    }
}

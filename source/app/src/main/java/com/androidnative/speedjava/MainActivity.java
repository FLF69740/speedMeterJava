package com.androidnative.speedjava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnative.speedjava.business.CAverage;
import com.androidnative.speedjava.business.CLocation;

import java.util.Calendar;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;


public class MainActivity extends AppCompatActivity implements LocationListener {

    private TextView mSpeed, hour, average;
    private static final int RC_FINE_LOCATION = 101;

    private Disposable mDisposable;

    private CountDownTimer mTimer;
    private CAverage mCalculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSpeed = findViewById(R.id.counter_speed);
        hour = findViewById(R.id.hour);
        average = findViewById(R.id.average);

        mCalculator = new CAverage();

        doStuff();

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

    private void doStuff() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, RC_FINE_LOCATION);
                return;

            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
        Toast.makeText(this, "Waiting for GPS connection", Toast.LENGTH_SHORT).show();
    }

    /**
     *  PERMISSION
     */

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == RC_FINE_LOCATION){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                doStuff();
            } else {
                finish();
            }
        }
    }

    /**
     *  LOCATION LISTENER METHODS
     */

    @Override
    public void onLocationChanged(Location location) {
        if (location != null){
            CLocation myLocation = new CLocation(location);

            short nCurrentSpeed = (short) myLocation.getSpeed();

            mSpeed.setText(String.valueOf(nCurrentSpeed));
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {}

    @Override
    public void onProviderEnabled(String s) {}

    @Override
    public void onProviderDisabled(String s) {}

    /**
     *  RX JAVA
     */

    private Observable<Calendar> getObservable(){
        return Observable.just(Calendar.getInstance());
    }

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
        if (this.mDisposable != null && !this.mDisposable.isDisposed()) this.mDisposable.dispose();
        mTimer.cancel();
    }
}

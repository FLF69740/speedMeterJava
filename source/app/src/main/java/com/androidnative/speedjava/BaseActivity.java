package com.androidnative.speedjava;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.location.LocationListener;
import android.os.CountDownTimer;
import android.widget.Toast;
import com.androidnative.speedjava.business.CLocation;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public abstract class BaseActivity extends AppCompatActivity implements LocationListener {

    private static final int RC_FINE_LOCATION = 101;
    protected Disposable mDisposable;
    protected short mSpeedResult = 0;
    protected CountDownTimer mTimer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        doLocate();
        bodyActivity();

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

    /**
     *  ABSTRACT METHOD
     */

    public abstract int getLayout();
    public abstract void bodyActivity();
    public abstract void subscriberAction(short speedResult);

    /**
     *  LOCATION MANAGER
     */

    private void doLocate() {
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
                doLocate();
            } else {
                finish();
            }
        }
    }

    /**
     *  RX JAVA
     */

    protected Observable<Short> getObservable(){
        return Observable.just(mSpeedResult);
    }

    protected DisposableObserver<Short> getSubscriber(){
        return new DisposableObserver<Short>() {
            @Override
            public void onNext(Short result) {
                subscriberAction(result);
            }

            @Override
            public void onError(Throwable e) {}

            @Override
            public void onComplete() {}
        };
    }

    /**
     *  LIFECYCLE
     */

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTimer.cancel();
        if (this.mDisposable != null && !this.mDisposable.isDisposed()) this.mDisposable.dispose();
    }

    /**
     *  LOCATION LISTENER METHODS
     */

    @Override
    public void onLocationChanged(Location location) {
        if (location != null){
            CLocation myLocation = new CLocation(location);
            mSpeedResult = (short) myLocation.getSpeed();
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {}

    @Override
    public void onProviderEnabled(String s) {}

    @Override
    public void onProviderDisabled(String s) {}
}

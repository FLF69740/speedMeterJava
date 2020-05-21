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
import android.widget.TextView;
import android.widget.Toast;

import com.androidnative.speedjava.business.CLocation;


public class MainActivity extends AppCompatActivity implements LocationListener {

    private TextView mSpeed;
    private static final int RC_FINE_LOCATION = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSpeed = findViewById(R.id.counter_speed);

        doStuff();

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

            float nCurrentSpeed = myLocation.getSpeed();;

            String result = String.format("%.2f",nCurrentSpeed);
            mSpeed.setText(result);
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}

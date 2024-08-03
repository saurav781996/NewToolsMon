package com.example.newtoolsmon.gps;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.an.deviceinfo.device.DeviceInfo;
import com.an.deviceinfo.location.DeviceLocation;
import com.an.deviceinfo.location.LocationInfo;
import com.example.newtoolsmon.R;
import com.example.newtoolsmon.dashboard.MainActivity;
import com.github.nikartm.button.FitButton;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class GpsLocation extends AppCompatActivity {

    private static final int ACCESS_FINE_LOCATION = 105;
    FusedLocationProviderClient mFusedLocationClient;
    TextView latitudeTextView, longitTextView, address_line, city, state, country_code, postal_code;
    int PERMISSION_ID = 44;
    FitButton openMaps, share_location;
    String data_gps_test;
    double latitude;
    double longitude;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "save_report";
    MapView map = null;

    RelativeLayout rel_map;
    TextView tv_info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.black));
        setContentView(R.layout.gps_activity);
        inits();





        // enabling gps
        if (!isLocationEnabled()) {
            new AlertDialog.Builder(this).setTitle("GPS permission")
                    .setMessage("For getting accurate location. Please turn on GPS")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (!confirmFineLocationEnabled()){
                                checkAndRequestPermissions(android.Manifest.permission.ACCESS_FINE_LOCATION, ACCESS_FINE_LOCATION);
                            }

                        }
                    })
                    .show();
        }

        openMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGoogleMaps();
            }
        });

        share_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareLocationMethod();
            }
        });

        checkAndRequestPermissions(android.Manifest.permission.ACCESS_FINE_LOCATION, ACCESS_FINE_LOCATION);

        setLocationOnOSM();

        if (!isLocationEnabled() && !confirmFineLocationEnabled()){

            tv_info.setText("Please Provide Location Permission");
            rel_map.setVisibility(View.GONE);
            tv_info.setVisibility(View.VISIBLE);

        }else {

            rel_map.setVisibility(View.VISIBLE);
            tv_info.setVisibility(View.GONE);

        }


    }


    private void inits(){


        sharedpreferences = GpsLocation.this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        openMaps = (FitButton) findViewById(R.id.open_maps);
        share_location = (FitButton) findViewById(R.id.share_location);

        latitudeTextView = findViewById(R.id.latTextView);
        longitTextView = findViewById(R.id.lonTextView);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);



        address_line = findViewById(R.id.address_line);
        city = findViewById(R.id.city);
        state = findViewById(R.id.state);
        country_code = findViewById(R.id.country_code);
        postal_code = findViewById(R.id.postal_code);

        rel_map = (RelativeLayout) findViewById(R.id.rel_map);
        tv_info = (TextView) findViewById(R.id.tv_info);

    }

    public boolean confirmFineLocationEnabled() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = getApplicationContext().checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION);
            return result == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    public void checkAndRequestPermissions(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(GpsLocation.this, permission) == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(GpsLocation.this, new String[]{permission}, requestCode);
        } else {


            	 try {
                     getLastLocation();
                     DeviceLocation location;
                     LocationInfo locationInfo = new LocationInfo(this);
                     location = locationInfo.getLocation();
                     latitude = location.getLatitude();
                     longitude = location.getLongitude();
                     address_line.setText(location.getAddressLine1());
                     city.setText(location.getCity());
                     state.setText(location.getState());
                     country_code.setText(location.getCountryCode());
                     postal_code.setText(location.getPostalCode());

            	         }
            	 		catch (Exception e) {
            	             e.printStackTrace();
            	         }

        }
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given


            // check if location is enabled
            if (isLocationEnabled()) {

                data_gps_test = "yes";

                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
                            latitudeTextView.setText(location.getLatitude() + "");
                            longitTextView.setText(location.getLongitude() + "");
                        }
                    }
                });

            } else {

                data_gps_test = "No";

                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);


            }

    }


    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }


    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            latitudeTextView.setText("Latitude: " + mLastLocation.getLatitude() + "");
            longitTextView.setText("Longitude: " + mLastLocation.getLongitude() + "");

            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();

        }
    };



    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }



    // If everything is alright then
    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            }
        }
    }


    public void shareLocationMethod() {

        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.putExtra("android.intent.extra.TEXT", "Hey there! My current location"
                + "\n Latitude: " + this.latitude + "\n Longitude: " + this.longitude);
        startActivity(Intent.createChooser(intent, "Location"));
    }


    public void openGoogleMaps() {

        String uri = String.format(Locale.ENGLISH, "geo:%f,%f", this.latitude, this.longitude);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);

    }


    public void setLocationOnOSM() {


        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setMultiTouchControls(true);
        map.setBuiltInZoomControls(true);

        IMapController mapController = map.getController();
        mapController.setZoom(16);
        GeoPoint startPoint = new GeoPoint(this.latitude, this.longitude);
        mapController.setCenter(startPoint);


    }

    @Override
    public void onResume() {
        super.onResume();

        map.onResume();

    }


    public void onPause() {
        super.onPause();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        map.onPause();  //needed for compass, my location overlays, v6.0.0 and up
    }


}

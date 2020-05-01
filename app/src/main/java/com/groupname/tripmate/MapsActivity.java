package com.groupname.tripmate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.geo.BackendlessGeoQuery;
import com.backendless.geo.GeoPoint;
import com.backendless.persistence.local.UserIdStorageFactory;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    String Driver;
    private LocationManager locationManager;
    private String provider;
    double lat = 23.259933, log = 77.412613;
    ImageButton ibShare;
    boolean isExistingPoint = false;
    GeoPoint existingPoint;
    List<GeoPoint> Geo_list;

    private ImageButton btnShareLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);




        btnShareLocation = findViewById(R.id.btnShareLocation);
        String userObjectID = UserIdStorageFactory.instance().getStorage().get();
        if (FirstClass.user.getProperty("isDriver").equals("0")) {

            btnShareLocation.setVisibility(View.GONE);
            BackendlessGeoQuery geoQuery = new BackendlessGeoQuery();
            geoQuery.addCategory("RunningBuses");
            geoQuery.setIncludeMeta(true);

            Backendless.Geo.getPoints(geoQuery, new AsyncCallback<List<GeoPoint>>() {
                @Override
                public void handleResponse(List<GeoPoint> response) {
                    Geo_list = response;
                    if(Geo_list.size()!=0)
                    {
                        for(int i=0;i<Geo_list.size();i++)
                        {
                            LatLng positionMarker = new LatLng(Geo_list.get(i).getLatitude(),Geo_list.get(i).getLatitude());

                            mMap.addMarker(new MarkerOptions()
                                               .position(positionMarker)
                                                .snippet(Geo_list.get(i).getMetadata("updated").toString())
                                                .title((Geo_list.get(i).getMetadata("DriverName").toString())));
                        }
                    }
                    else
                    {
                        Toast.makeText(MapsActivity.this, "woww", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    Toast.makeText(MapsActivity.this, "Error"+fault.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
        {
            btnShareLocation.setVisibility(View.GONE);
            BackendlessGeoQuery geoQuery = new BackendlessGeoQuery();
            geoQuery.addCategory("RunningBuses");
            geoQuery.setIncludeMeta(true);

            Backendless.Geo.getPoints(geoQuery, new AsyncCallback<List<GeoPoint>>() {
                @Override
                public void handleResponse(List<GeoPoint> response) {
                    Geo_list = response;
                    if(Geo_list.size()!=0)
                    {
                        for(int i=0;i<Geo_list.size();i++)
                        {
                            if(Geo_list.get(i).getMetadata("DriverName").toString().equals(FirstClass.user.getEmail()))
                            {
                                isExistingPoint = true;
                                existingPoint = Geo_list.get(i);
                                break;
                            }
                        }
                    }
                    btnShareLocation.setVisibility(View.VISIBLE);

                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    Toast.makeText(MapsActivity.this,"Error"+fault.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        }


        btnShareLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

               if(!isExistingPoint)
               {
                   List<String> categories = new ArrayList<String>();
                   categories.add("RunningBuses");

                   Map<String,Object> meta = new HashMap<String,Object>();
                 //  String userObjectID = UserIdStorageFactory.instance().getStorage().get();
                   Driver= FirstClass.user.getEmail();

                   meta.put("DriverName",Driver);
                   meta.put("updated",new Date().toString());
                   Backendless.Geo.savePoint(lat, log, categories, meta, new AsyncCallback<GeoPoint>() {
                       @Override
                       public void handleResponse(GeoPoint response) {
                           Toast.makeText(MapsActivity.this, "Succesfully Saved Location", Toast.LENGTH_SHORT).show();
                           isExistingPoint = true;
                           existingPoint = response;
                       }

                       @Override
                       public void handleFault(BackendlessFault fault) {
                           Toast.makeText(MapsActivity.this, "Error"+fault.getMessage(), Toast.LENGTH_SHORT).show();
                       }
                   });
               }
               else
               {
                    Backendless.Geo.removePoint(existingPoint, new AsyncCallback<Void>() {
                        @Override
                        public void handleResponse(Void response) {
                            List<String> categories = new ArrayList<String>();
                            categories.add("RunningBuses");

                            Map<String,Object> meta = new HashMap<String,Object>();
                           // String userObjectID = UserIdStorageFactory.instance().getStorage().get();

                             Driver= FirstClass.user.getEmail();
                            meta.put("DriverName",Driver);
                            meta.put("updated",new Date().toString());
                            Backendless.Geo.savePoint(lat, log, categories, meta, new AsyncCallback<GeoPoint>() {
                                @Override
                                public void handleResponse(GeoPoint response) {
                                    Toast.makeText(MapsActivity.this, "Succesfully Saved Location", Toast.LENGTH_SHORT).show();
                                    isExistingPoint = true;
                                    existingPoint = response;
                                }

                                @Override
                                public void handleFault(BackendlessFault fault) {
                                    Toast.makeText(MapsActivity.this, "Error"+fault.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Toast.makeText(MapsActivity.this, "Error"+fault.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
               }

            }
        });


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria,false);
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED||
                ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MapsActivity.this,new String[] {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},0);
        }
        else
        {
           // startActivity(new Intent(TrackLocation.this,MapsActivity.class));
            Location location = locationManager.getLastKnownLocation(provider);
            if(location!=null)
            {
                onLocationChanged(location);
            }
        }

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        LatLng position = new LatLng(lat, log);
         mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position,10));
         mMap.animateCamera( CameraUpdateFactory.zoomTo(10),2000,null);
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED||
                ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MapsActivity.this,new String[] {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},0);
        }
        else
        {
            // startActivity(new Intent(TrackLocation.this,MapsActivity.class));
            mMap.setMyLocationEnabled(true);
        }

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

    }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        log = location.getLongitude();
        if(mMap!=null)
        {
            LatLng position = new LatLng(lat,log);
            mMap.addMarker(new MarkerOptions()
            .icon(BitmapDescriptorFactory.defaultMarker())
            .anchor(0.0f,1.0f)
            .title("Your Current Location")
            .position(position));

            mMap.moveCamera(CameraUpdateFactory.newLatLng(position));

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED||
                ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MapsActivity.this,new String[] {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},0);
        }
        else
        {
            // startActivity(new Intent(TrackLocation.this,MapsActivity.class));
            locationManager.removeUpdates(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED||
                ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MapsActivity.this,new String[] {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},0);
        }
        else
        {
            // startActivity(new Intent(TrackLocation.this,MapsActivity.class));
            locationManager.requestLocationUpdates(provider,100000,20,this);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

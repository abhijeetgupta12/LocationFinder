package com.example.abhi.googleproject;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }

            if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
            {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {

                        Toast.makeText(MapsActivity.this,"Network",Toast.LENGTH_SHORT).show();

                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        LatLng latLng = new LatLng(latitude,longitude);

                        Geocoder geocoder = new Geocoder(getApplicationContext());
                        try {
                           List<Address> addresses = geocoder.getFromLocation(latitude,longitude,1);
                           String add = addresses.get(0).getLocality()+" ";
                           add += addresses.get(0).getCountryName();
                            mMap.addMarker(new MarkerOptions().position(latLng).title(add));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10f));



                        } catch (IOException e) {
                            e.printStackTrace();
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
                });
            }
            else if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        Toast.makeText(MapsActivity.this,"GPS",Toast.LENGTH_SHORT).show();

                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        LatLng latLng = new LatLng(latitude,longitude);

                        Geocoder geocoder = new Geocoder(getApplicationContext());
                        try {
                            List<Address> addresses = geocoder.getFromLocation(latitude,longitude,1);
                            String add = addresses.get(0).getCountryCode();
                            add += addresses.get(0).getCountryName();
                            mMap.addMarker(new MarkerOptions().position(latLng).title(add));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10f));


                        } catch (IOException e) {
                            e.printStackTrace();
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
                });
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

        // Add a marker in Sydney and move the camera
       // LatLng mnit = new LatLng(26.863017, 75.810477);
       // mMap.addMarker(new MarkerOptions().position(mnit).title("Marker in MNIT"));
       // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mnit,10f));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.maptypes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Change the map type based on the user's selection.
        switch (item.getItemId()) {
            case R.id.normal_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                return true;
            case R.id.hybrid_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                return true;
            case R.id.satellite_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                return true;
            case R.id.terrain_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

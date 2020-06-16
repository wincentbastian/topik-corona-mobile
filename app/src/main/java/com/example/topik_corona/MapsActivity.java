package com.example.topik_corona;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.data.kml.KmlContainer;
import com.google.maps.android.data.kml.KmlLayer;
import com.google.maps.android.data.kml.KmlPlacemark;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private int RC = 1;
    private String[] perms = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    private GoogleMap gMap;
    KmlLayer layer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        onMapOpen();

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    private boolean hasLocationPermission(){
        return  EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION);
    }

    private boolean hasCoarseLocation(){
        return EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_COARSE_LOCATION);
    }

    private void onMapOpen(){
        if(hasLocationPermission() && hasCoarseLocation()){
            Toast.makeText(this, "TODO: Maps things", Toast.LENGTH_LONG).show();
        }
        else{
            EasyPermissions.requestPermissions(
                    new PermissionRequest.Builder(this, RC, perms)
                            .setRationale("This App need to access your location")
                            .setPositiveButtonText("OK")
                            .setNegativeButtonText("Cancel")
                            .build());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
//
//        Circle circle = gMap.addCircle(new CircleOptions()
//                .center(new LatLng(-8.622371,115.1770584))
//                .radius(100)
//                .strokeColor(Color.RED)
//                .fillColor(0x30ff0000));
//        LatLng loc = new LatLng(-8.6171387,115.1854269);
//        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16));
//
//        double[][] multi = new double[3][2];
//        multi[0][0] = -8.6211723;
//        multi[0][1] = 115.1773373;
//        multi[1][0] = -8.618165;
//        multi[1][1] = 115.181704;
//        multi[2][0] = -8.6171387;
//        multi[2][1] = 115.1854269;
//
//
//        for(int i = 0; i< multi.length; i++ ){
//            LatLng sydney = new LatLng(multi[i][0],multi[i][1]);
//            gMap.addMarker(new MarkerOptions().position(sydney)
//                    .title("Marker in Sydney"));
//            gMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//        }


        try {
            layer = new KmlLayer(gMap, R.raw.bali_kelurahan, getApplicationContext());
            layer.addLayerToMap();
            for (KmlContainer container : layer.getContainers()) {
                if (container.hasProperty("NAME_4")) {
                    System.out.println(container.getProperty("NAME_4"));
                }
            }




        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }




    }
}

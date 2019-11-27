package com.example.taewoo.mapexam;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int REQUEST_CODE_PERMISSIONS = 1000;
    private GoogleMap mMap;
    //private GoogleApiClient mGoogleApiClient;
    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        /*if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) this)
                    .addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) this)
                    .addApi(LocationServices.API)
                    .build();
        }*/

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

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
        LatLng kgu = new LatLng(37.300690, 127.035887);       //위도 경도를 표현한 객체
        mMap.addMarker(new MarkerOptions().position(kgu).title("경기대학교 태우킴")); //해당위치에 마커표시를 해주고 Maker in Sydney를 띄어줌.
        mMap.moveCamera(CameraUpdateFactory.newLatLng(kgu));    //시드니의 위치로 카메라를 옮기는 코드
        //이 셋째줄까지만 하면 지도가 굉장히 떨어져서 있음. 따라서 밑에 코드를 추가해주자
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));   // 2~21사이의 값을 줄 수 있음. 카메라 줌 기능!!

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {  //클릭했을 때 마커의 정보
            @Override
            public void onInfoWindowClick(Marker marker) {  //클릭했을 때 마커의 정보
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:031-407-4138"));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

    }

    public void onLastLocationButtonClicked(View view) { //버튼 클릭했을 때
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) { //권한 체크 하는 부분. 권한이 있으면 이 부분 실행 안됨.
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_CODE_PERMISSIONS);  //권한이 없으면 권한 요청.
            return;
        }
        mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {  //mFusedLocationClient에서 마지막 위치정보를 가져오고 거기에 성공하면 OnSuccess리스너가 동작. 즉, 여기가 권한이 있을 경우 실행 되는 부분.
            @Override
            public void onSuccess(Location location) {
                if (location != null) //예외처리 location이 널이면
                {
                    LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude()); //위도와 경도를 설정함.
                    mMap.addMarker(new MarkerOptions()
                            .position(myLocation)
                            .title("현재위치"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));
                }
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {  //리퀘스트 코드가 여러개일때를 대비해 switch문 사용
            case REQUEST_CODE_PERMISSIONS :
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager
                        .PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) { //권한 체크 하는 부분. 위에 코드와 똑같음.
                    Toast.makeText(this, "권한 체크 거부 됨", Toast.LENGTH_SHORT).show();//권한이 없으면 여기서는 Toast를 호출.
                }
        }
    }
}

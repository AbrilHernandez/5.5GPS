package www.ittepic.edu.gps;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements LocationListener {
    private LocationManager locationManager;
    TextView txt_long, txt_lat, txt_org;
    String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt_lat = (TextView) findViewById(R.id.txtLat);
        txt_long = (TextView) findViewById(R.id.txtLong);
        txt_org = (TextView) findViewById(R.id.txtSource);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            txt_org.setText("Origen =" + provider);
            onLocationChanged(location);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(provider, 500, 1, this);
    }

    @Override
    public void onPause(){
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {

        double lat=location.getLatitude();
        double lng=location.getLongitude();
        txt_lat.setText(""+lat);
        txt_long.setText(""+lng);
        txt_org.setText(provider);


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        txt_org.setText(provider);
    }

    @Override
    public void onProviderEnabled(String provider) {
        txt_org.setText(provider);
    }

    @Override
    public void onProviderDisabled(String provider) {
        txt_org.setText(provider);
    }
}
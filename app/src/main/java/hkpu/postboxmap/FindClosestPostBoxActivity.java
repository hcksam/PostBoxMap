package hkpu.postboxmap;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class FindClosestPostBoxActivity extends AppCompatActivity implements LocationListener {
    Context context;
    TextView gpsProvider;
    TextView introduction;
    Button processButton;
    TextView loading;
    LinearLayout yourLocationArea;
    LinearLayout postboxLocationArea;
    TextView yourCoordinate;
    TextView yourAddress;
    TextView postboxCoordinate;
    TextView postboxAddress;

    LocationManager locationManager;
    Location location;
    Location nearestLocation;
    PostBoxLocationDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_closest_post_box);
        context = this;

        gpsProvider = (TextView) findViewById(R.id.gpsProvider);
        processButton = (Button) findViewById(R.id.process);
        introduction = (TextView) findViewById(R.id.introduction);
        loading = (TextView) findViewById(R.id.loading);
        yourLocationArea = (LinearLayout) findViewById(R.id.yourLocation);
        postboxLocationArea = (LinearLayout) findViewById(R.id.postboxLocation);
        yourCoordinate = (TextView) findViewById(R.id.yourLocation_coordinate);
        yourAddress = (TextView) findViewById(R.id.yourLocation_address);
        postboxCoordinate = (TextView) findViewById(R.id.postboxLocation_coordinate);
        postboxAddress = (TextView) findViewById(R.id.postboxLocation_address);

        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Criteria myCriteria = new Criteria();
            myCriteria.setAccuracy(Criteria.ACCURACY_FINE);
            myCriteria.setPowerRequirement(Criteria.NO_REQUIREMENT);
            String myBestProvider = locationManager.getBestProvider(myCriteria, true);
            gpsProvider.setText(myBestProvider);
        }catch (Exception e){
            Toast.makeText(context, "GPS error\n"+e.toString(),Toast.LENGTH_SHORT).show();
        }

        dao = new PostBoxLocationDAO(context, Environment.getExternalStorageDirectory().getAbsolutePath());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_find_closest_post_box, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(Location location) {

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

    public void process(View view){
        introduction.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
        processButton.setEnabled(false);
        try{
            location = locationManager.getLastKnownLocation(gpsProvider.getText().toString());
            if (location != null) {
                yourLocationArea.setVisibility(View.VISIBLE);
                yourCoordinate.setText(getCoordinate(location));

                List<PostBoxLocationBean> allLocations = dao.listAll();
                PostBoxLocationBean nearestBean = new PostBoxLocationBean();
                Float nearest = null;
                for (int i=0;i<allLocations.size();i++){
                    PostBoxLocationBean bean = allLocations.get(i);
                    Location dbLocation = getLocation(bean.getLatitude(), bean.getLongitude());
                    float distance = location.distanceTo(dbLocation);

                    if (nearest == null){
                        nearest = distance;
                        nearestBean = bean;
                    }else{
                        if  (nearest > distance){
                            nearest = distance;
                            nearestBean = bean;
                        }
                    }
                }
                nearestLocation = getLocation(nearestBean.getLatitude(), nearestBean.getLongitude());

                if (nearestLocation != null){
                    postboxLocationArea.setVisibility(View.VISIBLE);
                    postboxCoordinate.setText(getCoordinate(nearestLocation));

                    processButton.setEnabled(true);
                    loading.setVisibility(View.INVISIBLE);
                }else{
                    Toast.makeText(context, R.string.message_postBoxNotFind, Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(context, R.string.message_gpsNotFind, Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            Toast.makeText(context, "GPS error\n"+e.toString(),Toast.LENGTH_SHORT).show();
            processButton.setEnabled(true);
            loading.setVisibility(View.INVISIBLE);
        }
    }

    public void showYourMap(View view){
        if (location != null) {
            Uri geo = getGeoURI(location);
            Intent geoMap = new Intent(Intent.ACTION_VIEW, geo);
            startActivity(geoMap);
        }
    }

    public void showPostBoxMap(View view){
        if (nearestLocation != null) {
            Uri geo = getGeoURI(nearestLocation);
            Intent geoMap = new Intent(Intent.ACTION_VIEW, geo);
            startActivity(geoMap);
        }
    }

    public Uri getGeoURI(Location location){
        String geoURI = String.format("geo:0,0?q=%f,%f", location.getLatitude(), location.getLongitude());
        Uri geo = Uri.parse(geoURI);
        return geo;
    }

    public String getCoordinate(Location location){
        String coordinate = "( " + location.getLatitude() + ", " + location.getLongitude()+")";
        return coordinate;
    }

    public Location getLocation(double lan, double lon){
        Location location = new Location(gpsProvider.getText().toString());
        location.setLatitude(lan);
        location.setLongitude(lon);
        return location;
    }
}

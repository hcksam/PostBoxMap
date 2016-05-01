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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class FindClosestLocationActivity extends AppCompatActivity implements LocationListener {
    Context context;
    Spinner locationType;
    TextView gpsProvider;
    TextView introduction;
    Button processButton;
    TextView loading;
    LinearLayout yourLocationArea;
    LinearLayout closestLocationArea;
    TextView yourCoordinate;
    TextView yourAddress;
    TextView closestCoordinate;
    TextView closestAddress;

    File dbFile;
    LocationManager locationManager;
    Location location;
    Location nearestLocation;
    String nearestAddress;
    boolean GPSError = false;
    LocationDataDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_closest_location);
        context = this;

        locationType = (Spinner) findViewById(R.id.locationType);
        gpsProvider = (TextView) findViewById(R.id.gpsProvider);
        processButton = (Button) findViewById(R.id.process);
        introduction = (TextView) findViewById(R.id.introduction);
        loading = (TextView) findViewById(R.id.loading);
        yourLocationArea = (LinearLayout) findViewById(R.id.yourLocation);
        closestLocationArea = (LinearLayout) findViewById(R.id.closestLocation);
        yourCoordinate = (TextView) findViewById(R.id.yourLocation_coordinate);
        yourAddress = (TextView) findViewById(R.id.yourLocation_address);
        closestCoordinate = (TextView) findViewById(R.id.closestLocation_coordinate);
        closestAddress = (TextView) findViewById(R.id.closestLocation_address);

        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            setBestProvider();

        } catch (Exception e) {
            Toast.makeText(context, "GPS error\n" + e.toString(), Toast.LENGTH_LONG).show();
        }

        dbFile = new File(getFilesDir(), FixData.DBName);
        dbFile.delete();
        if (! dbFile.exists()) {
            Toast.makeText(context, R.string.message_initDatabase, Toast.LENGTH_SHORT).show();;
            initDB();
        }

        try {
            dao = new LocationDataDAO(context, dbFile);
            setLocationType();
        } catch (Exception e) {
            Toast.makeText(context, "Database error\n" + e.toString(), Toast.LENGTH_LONG).show();
        }
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
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        setBestProvider();
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        super.onBackPressed();
    }

    @Override
    public void onLocationChanged(Location location) {
        if (loading.getVisibility() == View.VISIBLE && yourLocationArea.getVisibility() != View.VISIBLE) {
            process(null);
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

    public void setLocationType(){
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, dao.listAllType());
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        locationType.setAdapter(adapter);
    }

    public void initDB(){
        try {
            InputStream inputStream = getAssets().open(FixData.DBName);
            OutputStream outputStream = new FileOutputStream(dbFile);

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }

        }catch (Exception e){
            Toast.makeText(context, R.string.message_databaseNotReady, Toast.LENGTH_LONG).show();
        }
    }

    public void process(View view) {
        String nowType = String.valueOf(locationType.getSelectedItem());

        introduction.setVisibility(View.GONE);
        yourLocationArea.setVisibility(View.GONE);
        closestLocationArea.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
        processButton.setEnabled(false);
        try {
            location = locationManager.getLastKnownLocation(gpsProvider.getText().toString());
            if (location != null) {
                yourLocationArea.setVisibility(View.VISIBLE);
                yourCoordinate.setText(getCoordinate(location));

                List allLocations = dao.listByType(nowType);
                CommonBean nearestBean = new CommonBean();
                Float nearest = null;
                for (int i=0;i<allLocations.size();i++){
                    CommonBean bean = (CommonBean) allLocations.get(i);
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
                nearestAddress = nearestBean.getAddress();
                String address = nearestBean.getArea()+"\n";
                address += nearestAddress;
                closestAddress.setText(address);

                if (nearestLocation != null){
                    closestLocationArea.setVisibility(View.VISIBLE);
                    closestCoordinate.setText(getCoordinate(nearestLocation));

                    processButton.setEnabled(true);
                    loading.setVisibility(View.INVISIBLE);
                }else{
                    Toast.makeText(context, R.string.message_databaseNotReady, Toast.LENGTH_LONG).show();
                    processButton.setEnabled(true);
                    loading.setVisibility(View.INVISIBLE);
                }

                GPSError = false;
            }else{
                processButton.setEnabled(true);
//                loading.setVisibility(View.INVISIBLE);

                if (gpsProvider.getText().toString().equals(LocationManager.GPS_PROVIDER)){
                    Toast.makeText(context, R.string.message_gpsNotReady, Toast.LENGTH_LONG).show();
                    GPSError = true;
                    setProvider(LocationManager.PASSIVE_PROVIDER);
                }
            }
        }catch (Exception e){
            Toast.makeText(context, "Error\n"+e.toString(),Toast.LENGTH_LONG).show();
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

    public void showClosestMap(View view){
        if (nearestLocation != null) {
//            Uri geo = getGeoURI(nearestLocation);
            Uri geo = getGeoURI(nearestAddress);
            Intent geoMap = new Intent(Intent.ACTION_VIEW, geo);
            startActivity(geoMap);
        }
    }

    public void setBestProvider(){
        Criteria myCriteria = new Criteria();
        myCriteria.setAccuracy(Criteria.ACCURACY_FINE);
        myCriteria.setPowerRequirement(Criteria.NO_REQUIREMENT);
        String myBestProvider = locationManager.getBestProvider(myCriteria, true);

        if (!GPSError || !myBestProvider.equals(LocationManager.GPS_PROVIDER)) {
            setProvider(myBestProvider);
        }
    }

    public void setProvider(String provider){
        gpsProvider.setText(provider);

        try {
            locationManager.removeUpdates(this);
            locationManager.requestLocationUpdates(gpsProvider.getText().toString(), 3000, 0, this);
        }catch (Exception e){
            Toast.makeText(context, "Error\n"+e.toString(),Toast.LENGTH_LONG).show();
        }
    }

    public Uri getGeoURI(Location location){
        String geoURI = String.format("geo:0,0?q=%f,%f", location.getLatitude(), location.getLongitude());
        Uri geo = Uri.parse(geoURI);
        return geo;
    }

    public Uri getGeoURI(String address){
        String geoURI = String.format("geo:0,0?q=%s", address);
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

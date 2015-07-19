package coffee.chris.gopherstudios.dontpanic;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Chris on 7/18/2015.
 */
public class LocationBackend implements LocationListener {
    private double mLatitude;
    private double mLongitute;
    private String mAddress;
    private String mProvider;
    private LocationManager mLocationManager;
    private Location mLocation;

    public LocationBackend(Context context) {

        // Get the location manager
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the location provider -> use
        // default
        Criteria criteria = new Criteria();
        mProvider = mLocationManager.getBestProvider(criteria, false);
        mLocation = mLocationManager.getLastKnownLocation(mProvider);

        if (mLocation != null) {
            updateLocationFromGps(context);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }

    public void updateLocationFromGps(Context context) {
        mLatitude = mLocation.getLatitude();
        mLongitute = mLocation.getLongitude();
        mAddress = "";

        Geocoder geocoder = new Geocoder(context, Locale.getDefault());

        List<Address> address;

        try {
            address = geocoder.getFromLocation(mLatitude, mLongitute, 1);
        } catch (IOException ioException) {
            address = null;
        }

        if(address != null && !address.isEmpty() && address.get(0).getMaxAddressLineIndex() > 2) {
            for (int i = 0; i < address.get(0).getMaxAddressLineIndex(); i++) {
                mAddress += address.get(0).getAddressLine(i);
                mAddress += "\n";
            }
        }
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onLocationChanged(Location location)
    {
    }

    public String getAddress()
    {
        return mAddress;
    }

    public String getLatitude()
    {
        return String.valueOf(mLatitude);
    }

    public String getLongitude()
    {
        return String.valueOf(mLongitute);
    }
}

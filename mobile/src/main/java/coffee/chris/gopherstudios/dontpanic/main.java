package coffee.chris.gopherstudios.dontpanic;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;


public class main extends Activity {
    ActionBar.Tab tab1, tab2, tab3;
    Fragment fragmentTab1 = new title();
    Fragment fragmentTab2 = new settings();
    Fragment fragmentTab3 = new panic_settings();

    ContactSettings contactSettings;

    LocationBackend locationBackend;

    private GoogleApiClient mGoogleApiClient;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        tab1 = actionBar.newTab().setText("Main");
        tab2 = actionBar.newTab().setText("Settings");
        tab3 = actionBar.newTab().setText("Panic Settings");

        tab1.setTabListener(new MyTabListener(fragmentTab1));
        tab2.setTabListener(new MyTabListener(fragmentTab2));
        tab3.setTabListener(new MyTabListener(fragmentTab3));

        actionBar.addTab(tab1);
        actionBar.addTab(tab2);
        actionBar.addTab(tab3);

        buildGoogleApiClient();
        locationBackend = new LocationBackend(this);

        contactSettings = new ContactSettings();
    }

    ContactSettings getContactSettings(){
        return contactSettings;
    }

    void sendText()
    {
        if(contactSettings.getContact().getPhone() != null && contactSettings.getContact().getMessage() != null) {
            PendingIntent pi = PendingIntent.getActivity(this, 0,
                    new Intent(this, main.class), 0);
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(contactSettings.getContact().getPhone(), null, contactSettings.getContact().getMessage(), pi, null);
        }else{
            Toast.makeText(this, "Make sure you set up your emergency contact information",
                    Toast.LENGTH_LONG).show();
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .build();
    }

    public LocationBackend getLocationBackend()
    {
        return locationBackend;
    }
}
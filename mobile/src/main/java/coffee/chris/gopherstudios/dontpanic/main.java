package coffee.chris.gopherstudios.dontpanic;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;


public class main extends Activity {
    private ActionBar.Tab tab1, tab2, tab3;

    private Fragment fragmentTab1, fragmentTab2, fragmentTab3;

    private ContactSettings contactSettings;

    private PanicConfig mPanicConfig;

    private LocationBackend locationBackend;

    //TODO: Possibly move to LocationBackend/remove?
    private GoogleApiClient mGoogleApiClient;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        tab1 = actionBar.newTab().setText("Main");
        tab2 = actionBar.newTab().setText("Manage Contacts");
        tab3 = actionBar.newTab().setText("Panic Settings");

        //Load settings
        //TODO: Load Panic Button Settings
        contactSettings = new ContactSettings();

        //Initialize Settings
        fragmentTab2 = ContactSettingsFragment.newInstance( contactSettings );
        fragmentTab3 = panic_settings.newInstance(  ); //TODO: Add Panic settings to arg list

        //Initialize Button
        fragmentTab1 = title.newInstance();

        tab1.setTabListener(new MyTabListener(fragmentTab1));
        tab2.setTabListener(new MyTabListener(fragmentTab2));
        tab3.setTabListener(new MyTabListener(fragmentTab3));

        actionBar.addTab(tab1);
        actionBar.addTab(tab2);
        actionBar.addTab(tab3);

        buildGoogleApiClient();

        locationBackend = new LocationBackend(this);

        mPanicConfig = new PressPanicConfig();
    }

    ContactSettings getContactSettings(){
        return contactSettings;
    }

    void sendText()
    {
        if(contactSettings.getContactListSize() != 0) {
            for (int i = 0; i < contactSettings.getContactListSize(); i++) {
                if (contactSettings.getContact(i).getPhone() != null && contactSettings.getContact(i).getMessage() != null) {
                    PendingIntent pi = PendingIntent.getActivity(this, 0,
                            new Intent(this, main.class), 0);
                    SmsManager sms = SmsManager.getDefault();
                    sms.sendTextMessage(contactSettings.getContact(i).getPhone(), null, contactSettings.getContact(i).getMessage(), pi, null);
                } else {
                    Toast.makeText(this, "Make sure you set up your emergency contact information",
                            Toast.LENGTH_LONG).show();
                }
            }
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
package coffee.chris.gopherstudios.dontpanic;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;


public class main extends Activity {
    ActionBar.Tab tab1, tab2, tab3;
    Fragment fragmentTab1 = new title();
    Fragment fragmentTab2 = new settings();
    Fragment fragmentTab3 = new panic_settings();

    String name = null;
    String phone = null;
    String message = null;

    LocationBackend locationBackend;
    SpeechBackend speechBackend;

    private final int REQ_CODE_SPEECH_INPUT = 100;

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
    }

    void setName(String n){
        name = n;
    }

    String getName(){
        return name;
    }

    void setPhone(String p){
        phone = p;
    }

    String getPhone(){
        return phone;
    }

    void setMessage(String m){
        message = m;
    }

    String getMessage(){
        return message;
    }

    void sendText(String phoneNumber, String message)
    {
        PendingIntent pi = PendingIntent.getActivity(this, 0,
                new Intent(this, main.class), 0);
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, pi, null);
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

    public SpeechBackend getSpeechBackend()
    {
        return speechBackend;
    }

    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    speechBackend.analyzeSpeech(data);
                }
                break;
            }

        }
    }
}


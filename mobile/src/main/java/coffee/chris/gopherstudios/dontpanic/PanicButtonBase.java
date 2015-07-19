package coffee.chris.gopherstudios.dontpanic;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

/**
 * Created by Chris on 7/19/2015.
 */
public abstract class PanicButtonBase {

    static boolean m_Valid;
    static View m_View;
    static ImageButton m_ImageButton;
    static GoogleApiClient m_GoogleApiClient;
    static ContactSettings m_ContactSettings;
    static LocationBackend m_locationBackend;

    public PanicButtonBase()
    {
        m_Valid = false;
    }

    public static PanicButtonBase Factory(String a_PanicButtonType, View a_View, GoogleApiClient a_GoogleApiClient, ContactSettings a_ContactSettings, LocationBackend a_LocationBackend)
    {
        PanicButtonBase returnButton;

        m_GoogleApiClient = a_GoogleApiClient;

        switch( a_PanicButtonType )
        {
            //TODO: add additional buttons
        case "Click":
            returnButton = new ClickPanicButton( );
            break;
//        case "Hold":
//            break;
//        case "Deadman":
//            break;
        default:
            returnButton = new ClickPanicButton();
            returnButton.m_Valid = false;
        }

        returnButton.m_locationBackend = a_LocationBackend;

        returnButton.m_ContactSettings = a_ContactSettings;

        returnButton.m_View = a_View;

        returnButton.m_ImageButton = (ImageButton) a_View.findViewById(R.id.panicButton);

        return returnButton;
    }

    public boolean IsValid()
    {
        return m_Valid;
    }

    public void panic(Context activity)
    {
        TextView textView = (TextView)m_View.findViewById(R.id.mTextView);
        textView.setText("Triggered");
        sendText(activity);
    }

    static void sendText(Context activity)
    {
        if(m_ContactSettings.getContactListSize() != 0) {
            m_locationBackend.updateLocationFromGps(activity);


                    PendingIntent pi = PendingIntent.getActivity(activity, 0,
                            new Intent(activity, main.class), 0);
                    SmsManager sms = SmsManager.getDefault();
                    sms.sendTextMessage("+013198998150", null,
                            "I am in trouble"
                            , pi, null);


        }
    }

    //insert panic logic here
    //return true if panic() was called
    public abstract boolean analyzePanic( MotionEvent event , Context activity);

}

package coffee.chris.gopherstudios.dontpanic;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;


public class title extends Fragment {

    //private ImageButton panicButton;
    private PanicButtonBase m_PanicButton;
    private View.OnTouchListener m_Listener;
    private GoogleApiClient m_GoogleApiClient;

    private String EVENTS_PANIC = "/Events/PanicFired";
    private String PANIC_KEY = "coffee.chris.gopherstudios.panic";
    private GoogleApiClient mGoogleApiClient;
    private ContactSettings m_ContactSettings;

    TextView timeText;

    public static final title newInstance( main a_main, GoogleApiClient a_GoogleApiClient)
    {
        title f = new title();
        f.setGoogleApi(a_GoogleApiClient);
        f.setContactSettings( a_main.getContactSettings());

        return f;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_title, container, false);

        //TODO: Replace "Click" with setting
        m_PanicButton = PanicButtonBase.Factory("Click", view, m_GoogleApiClient, m_ContactSettings,  ((main)getActivity()).getLocationBackend());

        //panicButton = (ImageButton) view.findViewById(R.id.panicButton);

        m_Listener = new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                return m_PanicButton.analyzePanic(event, getActivity());
            }
        };

        view.findViewById(R.id.panicButton).setOnTouchListener(m_Listener);

        timeText = ((TextView) view.findViewById(R.id.mTextView));
        timeText.setText("");

//        uiUpdateThread = new Thread(new Runnable(){
//            public void run()
//            {
//                while(true) {
//                    try
//                    {
//                        Thread.sleep(500);
//                    }
//                    catch(Exception e)
//                    {
//                    }
//                }
//            }
//        });
//
//        uiUpdateThread.start();

        return view;
    }

    public void setGoogleApi(GoogleApiClient a_GoogleApiClient)
    {
        m_GoogleApiClient = a_GoogleApiClient;
    }

    public void setContactSettings(ContactSettings a_ContactSettings)
    {
        m_ContactSettings = a_ContactSettings;
    }

}
/*
    @Override
    public void onResume() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    protected void animateGui() {
        String time = "";
        timeText.setText( time + System.currentTimeMillis() );

//        PutDataMapRequest putDataMapReq = PutDataMapRequest.create(EVENTS_PANIC);
//        putDataMapReq.getDataMap().putLong(PANIC_KEY, panicTime);
//        PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
//        PendingResult<DataApi.DataItemResult> pendingResult =
//                Wearable.DataApi.putDataItem(mGoogleApiClient, putDataReq);
//        runOnUiThread(new Runnable() {
//        @Override
//        public void run() {
//            //((ImageButton)findViewById(R.id.panicButton));
//        }
//    });
    }

    @Override
    public void onPause() {
        super.onPause();
        mGoogleApiClient.disconnect();
    }
}*/
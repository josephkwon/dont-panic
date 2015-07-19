package coffee.chris.gopherstudios.dontpanic;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.PutDataMapRequest;

public class main extends Activity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private String EVENTS_PANIC = "/Events/PanicFired";
    private String PANIC_KEY = "coffee.chris.gopherstudios.panic";
    private GoogleApiClient mGoogleApiClient;
    private long panicTime, startTime = 0;
    private boolean held = false;
    private View.OnTouchListener toucher;
    private Thread uiUpdateThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        uiUpdateThread = new Thread(new Runnable(){
            public void run()
            {
                while(true) {
                    try
                    {
                        Thread.sleep(500);
                    }
                    catch(Exception e)
                    {
                         System.out.println(e);
                    }
                    if (held) {
                        panicHeld();
                    }
                }
            }
        });

        uiUpdateThread.start();

        PutDataMapRequest putDataMapReq = PutDataMapRequest.create(EVENTS_PANIC);
        panicTime = putDataMapReq.getDataMap().getLong(PANIC_KEY);
        ((Button)findViewById(R.id.shitButton)).setText("" + panicTime);

        toucher = new View.OnTouchListener(){

            public boolean onTouch(View v, MotionEvent event)
            {
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    //Start ticker
                    held = true;
                    panicTime = 0;
                    startTime = System.currentTimeMillis();
                    animateGui();
                    return true;
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Stop ticker
                    held = false;
                    panicTime = 0;
                    animateGui();
                    return true;
                }

                return false;
            }
        };

        findViewById(R.id.shitButton).setOnTouchListener(toucher);
    }

    protected void animateGui() {
        PutDataMapRequest putDataMapReq = PutDataMapRequest.create(EVENTS_PANIC);
        putDataMapReq.getDataMap().putLong(PANIC_KEY, panicTime);
        PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
        PendingResult<DataApi.DataItemResult> pendingResult =
                Wearable.DataApi.putDataItem(mGoogleApiClient, putDataReq);
        runOnUiThread(new Runnable() {
        @Override
        public void run() {
            ((Button)findViewById(R.id.shitButton)).setText("" + panicTime);
        }
    });
    }


    @Override
    protected void onResume() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle b) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        held = false;
        mGoogleApiClient.disconnect();
    }

    public void onConnectionSuspended(int i) {

    }

    public void onConnectionFailed(ConnectionResult r) {

    }

    private void panicHeld()
    {
        panicTime = System.currentTimeMillis() - startTime;
        animateGui();
    }

}

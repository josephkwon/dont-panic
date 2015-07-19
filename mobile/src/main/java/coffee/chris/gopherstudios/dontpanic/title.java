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

    private ImageButton panicButton;
    private View.OnTouchListener toucher;
    private Thread uiUpdateThread;
    private boolean held = false;

    private String EVENTS_PANIC = "/Events/PanicFired";
    private String PANIC_KEY = "coffee.chris.gopherstudios.panic";
    private GoogleApiClient mGoogleApiClient;
    private long panicTime, startTime = 0;
    TextView timeText;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_title, container, false);

        panicButton = (ImageButton) view.findViewById(R.id.panicButton);
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

        view.findViewById(R.id.panicButton).setOnTouchListener(toucher);

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Wearable.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle b) {
                        Wearable.DataApi.addListener(mGoogleApiClient, new DataApi.DataListener() {
                            public void onDataChanged(DataEventBuffer d) {
                                for (DataEvent event : d) {
                                    if (event.getType() == DataEvent.TYPE_CHANGED) {
                                        // DataItem changed
                                        DataItem item = event.getDataItem();
                                        if (item.getUri().getPath().compareTo(EVENTS_PANIC) == 0) {
                                            DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                                            updateStatus(dataMap.getLong(PANIC_KEY));
                                        }
                                    } else if (event.getType() == DataEvent.TYPE_DELETED) {
                                        // DataItem deleted
                                    }
                                }
                            }

                        });
                        PutDataMapRequest pdmr = PutDataMapRequest.create(EVENTS_PANIC);
                        PutDataRequest pdr = pdmr.asPutDataRequest();
                        PendingResult<DataApi.DataItemResult> pr = Wearable.DataApi.putDataItem(mGoogleApiClient, pdr);
                        panicTime = pdmr.getDataMap().getLong(PANIC_KEY);
                    }

                    public void onConnectionSuspended(int i) {

                    }

                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {

                    public void onConnectionFailed(ConnectionResult r) {

                    }
                })
                .build();
        timeText = ((TextView)view.findViewById(R.id.mTextView));
        timeText.setText("" + panicTime);

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
                    }
                    if (held) {
                        panicHeld();
                    }
                }
            }
        });

        uiUpdateThread.start();

        return view;
    }

    private void sendText()
    {
        ((main) getActivity()).sendText();
    }

    private void updateStatus(final long i) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                timeText.setText("" + i);
                if (((main) getActivity()).mPanicC.update(i, false)) {
                    sendText();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    protected void animateGui() {
        updateStatus(panicTime);
        if (held) {

        }
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

    private void panicHeld()
    {
        panicTime = System.currentTimeMillis() - startTime;
        animateGui();
    }
}
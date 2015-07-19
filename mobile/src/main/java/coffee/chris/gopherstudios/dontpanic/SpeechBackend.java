package coffee.chris.gopherstudios.dontpanic;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Chris on 7/18/2015.
 */
public class SpeechBackend {
    ArrayList<String> mSpeechText;

    /**
     * Showing google speech input dialog
     * */
    public void promptSpeechInput(Activity activity, int requestCode) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        intent.putExtra
                (
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                );
        intent.putExtra
                (
                    RecognizerIntent.EXTRA_LANGUAGE,
                    Locale.getDefault()
                );
        intent.putExtra
                (
                    RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS,
                    30000 //TODO: Replace with setting
                );

        try {
            activity.startActivityForResult(intent, requestCode);
        } catch (ActivityNotFoundException a) {
        }
    }

    public void analyzeSpeech(Intent data)
    {
        mSpeechText  = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
    }

    public ArrayList<String> getSpeechText()
    {
        return mSpeechText;
    }


}

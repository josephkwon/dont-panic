package coffee.chris.gopherstudios.dontpanic;

import android.widget.ImageButton;

/**
 * Created by Joseph on 7/19/2015.
 */
public class PanicConfig {
    private ImageButton img;

    private Thread uiUpdateThread;

    public boolean update(long time) {
        return false;
    }

}
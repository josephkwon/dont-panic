package coffee.chris.gopherstudios.dontpanic;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by Chris on 7/19/2015.
 */
public class ClickPanicButton extends PanicButtonBase{

    //private long m_panicTime, m_startTime;

    public ClickPanicButton()
    {
        //m_startTime = System.currentTimeMillis();
        m_Valid = true;
    }


    @Override
    public boolean analyzePanic(View v, MotionEvent event)
    {
        panic();
        return true;
    }

}

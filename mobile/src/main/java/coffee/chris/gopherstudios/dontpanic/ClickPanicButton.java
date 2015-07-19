package coffee.chris.gopherstudios.dontpanic;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Chris on 7/19/2015.
 */
public class ClickPanicButton extends PanicButtonBase{

    //private long m_panicTime, m_startTime;

    public ClickPanicButton( )
    {
        //m_startTime = System.currentTimeMillis();
        m_Valid = true;
    }




    public boolean analyzePanic( MotionEvent event, Context activity)
    {
        if(event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_UP) {
            panic(activity);
            return true;
        }

        return false;
    }

}

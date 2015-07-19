package coffee.chris.gopherstudios.dontpanic;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by Chris on 7/19/2015.
 */
public abstract class PanicButtonBase {

    static boolean m_Valid;
    //static View m_View;
    static ImageButton m_ImageButton;


    public PanicButtonBase()
    {
        m_Valid = false;
    }

    public static PanicButtonBase Factory(String a_PanicButtonType, View a_View)
    {
        PanicButtonBase returnButton;

        switch( a_PanicButtonType )
        {
            //TODO: add additional buttons
        case "Click":
            returnButton = new ClickPanicButton();
            break;
//        case "Hold":
//            break;
//        case "Deadman":
//            break;
        default:
            returnButton = new ClickPanicButton();
            returnButton.m_Valid = false;
        }

        //returnButton.m_View = a_View;

        returnButton.m_ImageButton = (ImageButton) a_View.findViewById(R.id.panicButton);

        return returnButton;
    }

    public boolean IsValid()
    {
        return m_Valid;
    }

    public void panic()
    {
        //TODO: Panic
    }

    //insert panic logic here
    //return true if panic() was called
    public abstract boolean analyzePanic( View v, MotionEvent event );

}

package coffee.chris.gopherstudios.dontpanic;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class main extends Activity {
    ActionBar.Tab tab1, tab2, tab3;
    Fragment fragmentTab1 = new title();
    Fragment fragmentTab2 = new settings();
    Fragment fragmentTab3 = new panic_settings();

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
    }
}
package coffee.chris.gopherstudios.dontpanic;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Fragment;
import android.app.FragmentTransaction;

/**
 * Created by jpsteffe on 7/18/15.
 */
public class MyTabListener implements ActionBar.TabListener {
    Fragment fragment;

    public MyTabListener(Fragment fragment) {
        this.fragment = fragment;
    }

    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        ft.replace(R.id.fragment_container, fragment);
    }

    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        ft.remove(fragment);
    }

    public void onTabReselected(Tab tab, FragmentTransaction ft) {
        // nothing done here
    }
}
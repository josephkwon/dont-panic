package coffee.chris.gopherstudios.dontpanic;

/**
 * Created by Joseph on 7/19/2015.
 */
public class TapPanicConfig extends PanicConfig {

    private int tapCounter;
    public long timeout;
    private long endTime;
    private long timer;

    public TapPanicConfig() {
        timeout =  3000;
        timer = 0;
        endTime = 0;
    }

    public boolean update(long time) {

        boolean tap = time > 0;

        if (tap) {
            tapCounter++;
            timer = 0;
            endTime = System.currentTimeMillis() + timeout;
        } else if (tapCounter > 0 && timer < timeout) {
            timer = endTime - System.currentTimeMillis();
        } else if (tapCounter > 0 && timer >= timeout) {
            tapCounter = 0;
        }

        if (tapCounter > 2){
            return true;
        } else {
            return false;
        }
    }

}

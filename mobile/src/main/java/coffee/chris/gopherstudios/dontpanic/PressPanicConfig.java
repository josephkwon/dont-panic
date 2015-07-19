package coffee.chris.gopherstudios.dontpanic;

/**
 * Created by Joseph on 7/19/2015.
 */
public class PressPanicConfig extends PanicConfig {

    private long heldTime;
    public long maxTime;
    private boolean lift;

    public PressPanicConfig() {
        heldTime = 0;
        maxTime = 3000;
        lift = false;
    }

    @Override
    public boolean update(long time) {
        heldTime = time;

        if(!lift && heldTime > maxTime) {
            lift = true;
            return true;
        }

        if(lift && time == 0) {
            lift = false;
        }

        return false;
    }

}
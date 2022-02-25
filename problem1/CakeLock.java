import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class CakeLock{
    private boolean isCakeEaten = false;
    private int numCakesReplaced = 0;

    public synchronized boolean eatCake()
            throws InterruptedException{
        if(isCakeEaten == false){
            isCakeEaten = true;
            return true;
        }else return false;
    }

    public synchronized void requestNewCake(){
        //request a new cake
        isCakeEaten = false;
        numCakesReplaced++;
    }

    public boolean isCakeEaten() {
        return isCakeEaten;
    }

    public int getNumCakesReplaced() {
        return numCakesReplaced;
    }
}

public class Guest implements Runnable{//Callable<Boolean> {

    private boolean isLeader;
    private boolean hasEatenCake;


    //private CakeLock cakeLock = new CakeLock();
    private static final CakeLock cakeLock = new CakeLock();

    public Guest(boolean isLeader, boolean hasEatenCake) {
        this.isLeader = isLeader;
        this.hasEatenCake = hasEatenCake;
    }

    @Override
    public void run() {
        //System.out.println("Thread is running!");
        //enter labyrinth
        //if leader, request new cake - leader doesn't have to eat because he will be in labyrinth no matter what to reset
        //only leader can call unlock on the locked cake

        if(isLeader == true)
        {
            if(cakeLock.isCakeEaten()){
                cakeLock.requestNewCake();  //request new cake for next person
                //System.out.println("num cakes eaten: " + cakeLock.getNumCakesReplaced());
            }

            int numCakesReplaced = cakeLock.getNumCakesReplaced();
            System.out.println("    The lead guest thinks to himself: there have been " + numCakesReplaced + " cakes eaten.");
            if(numCakesReplaced == 9){
                System.out.println("        This means we can end the party now, I'll tell the minotaur.");
                System.out.println("        'Mr. Minotaur, all of your guests have entered labyrinth at least once, the party can end now if you'd like.'");
                System.exit(1);
            }
            //System.out.println("cake isnt eaten?");
            return; //leave room
        }
        else{
            //if you've already eaten cake, leave.
            if(hasEatenCake == true){
                System.out.println("    The guest thinks to himself: I've already eaten the cake before, i'm leaving.");
                return;
            }
            //if cake is eaten, leave.
            if(cakeLock.isCakeEaten()){
                System.out.println("    The guest thinks to himself: This cake is already eaten, i'm leaving.");
                return;
            }
            //if cake is NOT eaten and you haven't eaten cake, eat cake and leave.
            if(hasEatenCake == false && cakeLock.isCakeEaten() != true){

                boolean couldEatCake = false;
                try {
                    couldEatCake = cakeLock.eatCake();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(couldEatCake){
                    hasEatenCake = true;
                    System.out.println("    The guest thinks to himself: I haven't eaten before and the cake is available, cake eaten!");
                }
                return;
            }
        }

        return;
    }

    public boolean hasEatenCake() {
        return hasEatenCake;
    }
}

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class GuestControllerQ2 {

    private static final int numberOfGuests = 10;
    private static Queue<GuestQ2> guestQueue;


    public static void main(String[] args) {

        //start the party, guests arrive:
        ArrayList<GuestQ2> guestList = getGuestList();

        //guests get into the queue
        guestQueue = new LinkedList<>();
        addGuestsToInitialQueue(guestList);


        //send each guest into showroom one at a time, once one guest has gone in they tell the person behind them
        //that they can go in.
        GuestQ2 currentGuest = guestQueue.remove();

        Random random = new Random();
        while(!guestQueue.isEmpty()){
            Thread currentGuestThread = new Thread(currentGuest);   //each guest is assigned one thread.
            currentGuestThread.start();
            try {
                currentGuestThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //the guest can decide if they want to re-enter the queue, we'll make this random.
            random.nextInt(100);
            if(random.nextInt() % 2 != 0){
                //guest has decided to re-enter queue
                System.out.println("...Guest decides to get back in line");
                guestQueue.add(currentGuest);
            }
            currentGuest = guestQueue.remove();
            int sizeOfQueue = guestQueue.size();
            System.out.println("Remaining size of queue: " + guestQueue.size());
        }


    }

    private static ArrayList<Thread> spawnGuestThreads(ArrayList<GuestQ2> guests) {
        ArrayList<Thread> guestThreads = new ArrayList<>();
        for (int i = 0; i < numberOfGuests; i++) {
            guestThreads.add(new Thread(guests.get(i)));
        }
        return guestThreads;
    }

    private static ArrayList<GuestQ2> getGuestList() {
        ArrayList<GuestQ2> guests = new ArrayList<>();
        for (int i = 0; i < numberOfGuests; i++) {
            guests.add(new GuestQ2());
        }
        return guests;
    }

    private static void addGuestsToInitialQueue(ArrayList<GuestQ2> guestList) {
        for(int i = 0; i < numberOfGuests; i++){
            guestQueue.add(guestList.get(i));
        }
    }

}
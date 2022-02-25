import java.util.ArrayList;
import java.util.Random;

public class GuestController {


    private static final int leaderThreadNumber = 7;
    private static final int numberOfGuests = 10;


    public static void main(String[] args) {
        //because the guests can form a strategy at the beginning, they'll use a leader to count how many people have gone
        //only the leader has permission to turn off the lights AKA to reset the cake
        //each person will go into the room and eat the cake - they will only eat the cake ONE time. no more.
        //if the cake is gone, the person leaves unless they're the leader (leaderThreadNumber) - who will reset the cake
        //once the leader has reset the cake 9 times he can be sure everybody has eaten the cake AKA everybody has entered the labyrinth

        //start the party, guests arrive:
        ArrayList<Guest> guests = getGuestList();


        ArrayList<Thread> guestThreads = spawnGuestThreads(guests);
        Random random = new Random();
        while (true) {
            int guestToEnter = random.nextInt(numberOfGuests);
            System.out.println("guest entering is guest # " + guestToEnter);
            if (guestToEnter == leaderThreadNumber) {
                try {
                    guestThreads.get(leaderThreadNumber).start();
                } catch (IllegalThreadStateException illegalThreadStateException) {
                    tellLeaderToReEnterLabyrinth(guests);
                }
                try {
                    guestThreads.get(leaderThreadNumber).join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    guestThreads.get(guestToEnter).start();
                } catch (IllegalThreadStateException illegalThreadStateException) {
                    tellGuestToReEnterLabyrinth(guests, guestToEnter);
                }
                try {
                    guestThreads.get(guestToEnter).join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static ArrayList<Thread> spawnGuestThreads(ArrayList<Guest> guests) {
        ArrayList<Thread> guestThreads = new ArrayList<>();
        for (int i = 0; i < numberOfGuests; i++) {
            guestThreads.add(new Thread(guests.get(i)));
        }
        return guestThreads;
    }

    private static ArrayList<Guest> getGuestList() {
        ArrayList<Guest> guests = new ArrayList<>();
        for (int i = 0; i < numberOfGuests; i++) {
            if (i == leaderThreadNumber) {
                guests.add(new Guest(true, false));
            } else guests.add(new Guest(false, false));
        }
        return guests;
    }

    private static void tellLeaderToReEnterLabyrinth(ArrayList<Guest> guests) {
        //guests and threads are not the same, because guests can be picked to enter the labyrinth more than
        //once, we'd have to "restart the thread", this is not possible in java. Therefore, we create a new thread
        //to represent the same guest who's already entered the labyrinth.
        //NOTE that guests are still "represented by one running thread" as only one thread is running per guest at a time.
        // i.e. one guest will not have 2 threads of itself running at once. it's limited to 1 to meet the requirements.
        Thread leaderThread = new Thread(guests.get(leaderThreadNumber));
        leaderThread.start();
        try {
            leaderThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void tellGuestToReEnterLabyrinth(ArrayList<Guest> guests, int guestToEnter) {
        //guests and threads are not the same, because guests can be picked to enter the labyrinth more than
        //once, we'd have to "restart the thread", this is not possible in java. Therefore, we create a new thread
        //to represent the same guest who's already entered the labyrinth.
        //NOTE that guests are still "represented by one running thread" as only one thread is running per guest at a time.
        // i.e. one guest will not have 2 threads of itself running at once. it's limited to 1 to meet the requirements.
        Thread guestToReEnterLabyrinth = new Thread(guests.get(guestToEnter));
        guestToReEnterLabyrinth.start();

        try {
            guestToReEnterLabyrinth.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
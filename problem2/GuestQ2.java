public class GuestQ2 implements Runnable{

    public GuestQ2() {
    }

    private void enterShowRoom(){
        System.out.println("...Guest enters showroom.");
    }

    private void viewVase(){
        System.out.println("...Guest Views Crystal Vase.");
    }

    private void exitShowRoom(){
        System.out.println("...Guest exits showroom.");
    }

    private void tellPersonBehindThem(){
        System.out.println("...Guest tells the person at the front they're good to go in.");
    }
    @Override
    public void run() {
        //enter showroom, view vase, exit showroom.
        enterShowRoom();
        viewVase();
        exitShowRoom();
        tellPersonBehindThem();
        return;
    }
}

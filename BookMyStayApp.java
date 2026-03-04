abstract class Room {
    protected int numberOFBeds;
    protected int squareFeet;
    protected double pricePerNight;
    public Room(int numberOFBeds, int squareFeet, double pricePerNight) {
        this.numberOFBeds = numberOFBeds;
        this.squareFeet = squareFeet;
        this.pricePerNight = pricePerNight;
    }
    public void displayRoomDetails() {
        System.out.println("Number of Beds: " + numberOFBeds);
        System.out.println("Square Feet: " + squareFeet);
        System.out.println("Price per Night: " + pricePerNight);
    }
}
class SingleRoom extends Room {
    public SingleRoom() {
        super(1, 250, 1500.0);
    }
}
class DoubleRoom extends Room{
    public DoubleRoom(){
        super(2, 400, 2500.0);
    }
}
class SuiteRoom extends Room{
    public SuiteRoom(){
        super(3, 750 , 5000.0);
    }
}
public class BookMyStayApp {
    public static void main(String[] args) {
        SingleRoom sR = new SingleRoom();
        DoubleRoom dR = new DoubleRoom();
        SuiteRoom suiteR = new SuiteRoom();

        System.out.println("Hotel Room Initialization");
        System.out.println("Single Room Details:");
        sR.displayRoomDetails();
        System.out.println("\nDouble Room Details:");
        dR.displayRoomDetails();
        System.out.println("\nSuite Room Details:");
        suiteR.displayRoomDetails();
    }
}
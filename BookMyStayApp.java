
import java.util.HashMap;
import java.util.Map;

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


class RoomInventory {

    private Map<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new HashMap<>();
        initializeInventory();
    }

    private void initializeInventory() {
        roomAvailability.put("Single", 5);
        roomAvailability.put("Double", 3);
        roomAvailability.put("Suite", 2);
    }

    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }

    public void updateAvailability(String roomType, int count) {
        roomAvailability.put(roomType, count);
    }
}

class RoomSearchService {

    public void searchAvailableRooms(
            RoomInventory inventory,
            SingleRoom singleRoom,
            DoubleRoom doubleRoom,
            SuiteRoom suiteRoom) {

        System.out.println("Room Search\n");

        Map<String, Integer> availability = inventory.getRoomAvailability();

        if (availability.getOrDefault("Single", 0) > 0) {
            System.out.println("Single Room:");
            singleRoom.displayRoomDetails();
            System.out.println("Available: " + availability.get("Single"));
            System.out.println();
        }

        if (availability.getOrDefault("Double", 0) > 0) {
            System.out.println("Double Room:");
            doubleRoom.displayRoomDetails();
            System.out.println("Available: " + availability.get("Double"));
            System.out.println();
        }

        if (availability.getOrDefault("Suite", 0) > 0) {
            System.out.println("Suite Room:");
            suiteRoom.displayRoomDetails();
            System.out.println("Available: " + availability.get("Suite"));
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        SingleRoom single = new SingleRoom();
        DoubleRoom doubleRoom = new DoubleRoom();
        SuiteRoom suite = new SuiteRoom();

        RoomInventory inventory = new RoomInventory();

        RoomSearchService searchService = new RoomSearchService();

        searchService.searchAvailableRooms(inventory, single, doubleRoom, suite);
    }
}
import java.io.*;
import java.util.*;

public class BookMyStayApp {

    public static void main(String[] args) {

        String fileName = "system_state.dat";

        RoomInventory inventory;
        BookingHistory history;

        // Try loading saved data
        PersistenceService persistence = new PersistenceService();

        SystemState state = persistence.loadState(fileName);

        if (state == null) {

            System.out.println("No saved data found. Starting fresh system.");

            inventory = new RoomInventory();
            history = new BookingHistory();

        } else {

            inventory = state.inventory;
            history = state.history;

            System.out.println("System state restored successfully.");
        }

        // Simulate bookings
        Reservation r1 = new Reservation("Alice", "Single", "SI-1");
        Reservation r2 = new Reservation("Bob", "Double", "DO-1");

        history.addReservation(r1);
        history.addReservation(r2);

        inventory.bookRoom("Single");
        inventory.bookRoom("Double");

        System.out.println("\nCurrent Inventory:");
        inventory.displayInventory();

        // Save system state before shutdown
        SystemState newState = new SystemState(inventory, history);
        persistence.saveState(fileName, newState);

        System.out.println("\nSystem state saved successfully.");
    }
}

class PersistenceService {

    public void saveState(String fileName, SystemState state) {

        try {

            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream(fileName));

            out.writeObject(state);
            out.close();

        } catch (IOException e) {

            System.out.println("Error saving system state.");
        }
    }

    public SystemState loadState(String fileName) {

        try {

            ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream(fileName));

            SystemState state = (SystemState) in.readObject();
            in.close();

            return state;

        } catch (Exception e) {

            return null;
        }
    }
}

class SystemState implements Serializable {

    RoomInventory inventory;
    BookingHistory history;

    public SystemState(RoomInventory inventory, BookingHistory history) {
        this.inventory = inventory;
        this.history = history;
    }
}

class RoomInventory implements Serializable {

    private Map<String, Integer> inventory;

    public RoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Single", 2);
        inventory.put("Double", 2);
        inventory.put("Suite", 1);
    }

    public void bookRoom(String roomType) {

        if (inventory.getOrDefault(roomType, 0) > 0) {
            inventory.put(roomType, inventory.get(roomType) - 1);
        }
    }

    public void displayInventory() {

        for (String type : inventory.keySet()) {
            System.out.println(type + " : " + inventory.get(type));
        }
    }
}

class BookingHistory implements Serializable {

    private List<Reservation> reservations;

    public BookingHistory() {
        reservations = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public List<Reservation> getReservations() {
        return reservations;
    }
}

class Reservation implements Serializable {

    private String guestName;
    private String roomType;
    private String roomId;

    public Reservation(String guestName, String roomType, String roomId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }
}
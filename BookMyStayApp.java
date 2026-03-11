import java.util.*;

public class BookMyStay {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        RoomAllocationService allocator = new RoomAllocationService();

        Queue<Reservation> reservationQueue = new LinkedList<>();

        reservationQueue.add(new Reservation("Alice", "Single"));
        reservationQueue.add(new Reservation("Bob", "Double"));
        reservationQueue.add(new Reservation("Charlie", "Single"));
        reservationQueue.add(new Reservation("David", "Suite"));

        inventory.displayInventory();

        while (!reservationQueue.isEmpty()) {
            Reservation reservation = reservationQueue.poll();
            allocator.allocateRoom(reservation, inventory);
        }

        inventory.displayInventory();
    }
}

class RoomAllocationService {

    private Set<String> allocatedRoomIds;
    private Map<String, Set<String>> assignedRoomsByType;

    public RoomAllocationService() {
        allocatedRoomIds = new HashSet<>();
        assignedRoomsByType = new HashMap<>();
    }

    public void allocateRoom(Reservation reservation, RoomInventory inventory) {

        String roomType = reservation.getRoomType();

        if (!inventory.isAvailable(roomType)) {
            System.out.println("No rooms available for type: " + roomType);
            return;
        }

        String roomId = generateRoomId(roomType);

        allocatedRoomIds.add(roomId);

        assignedRoomsByType
                .computeIfAbsent(roomType, k -> new HashSet<>())
                .add(roomId);

        inventory.bookRoom(roomType);

        reservation.setRoomId(roomId);

        System.out.println("Room allocated successfully!");
        System.out.println("Guest: " + reservation.getGuestName());
        System.out.println("Room Type: " + roomType);
        System.out.println("Room ID: " + roomId);
        System.out.println();
    }

    private String generateRoomId(String roomType) {

        int number = 1;
        String roomId;

        do {
            roomId = roomType.substring(0,2).toUpperCase() + "-" + number;
            number++;
        } while (allocatedRoomIds.contains(roomId));

        return roomId;
    }
}

class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Single", 2);
        inventory.put("Double", 2);
        inventory.put("Suite", 1);
    }

    public boolean isAvailable(String roomType) {
        return inventory.getOrDefault(roomType, 0) > 0;
    }

    public void bookRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void displayInventory() {

        System.out.println("Current Room Inventory:");

        for (String type : inventory.keySet()) {
            System.out.println(type + " : " + inventory.get(type));
        }

        System.out.println();
    }
}

class Reservation {

    private String guestName;
    private String roomType;
    private String roomId;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
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

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}

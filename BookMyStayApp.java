
import java.util.*;

public class BookMyStay {

    public static void main(String[] args) {

        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        RoomInventory inventory = new RoomInventory();
        RoomAllocationService allocationService = new RoomAllocationService();

        bookingQueue.addRequest(new Reservation("Abhi", "Single"));
        bookingQueue.addRequest(new Reservation("Vamathi", "Double"));
        bookingQueue.addRequest(new Reservation("Kural", "Suite"));
        bookingQueue.addRequest(new Reservation("Subha", "Single"));

        Thread t1 = new Thread(new ConcurrentBookingProcessor(bookingQueue, inventory, allocationService));
        Thread t2 = new Thread(new ConcurrentBookingProcessor(bookingQueue, inventory, allocationService));

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println("Thread execution interrupted.");
        }

        System.out.println();
        System.out.println("Remaining Inventory:");
        System.out.println("Single: " + inventory.getAvailableRooms("Single"));
        System.out.println("Double: " + inventory.getAvailableRooms("Double"));
        System.out.println("Suite: " + inventory.getAvailableRooms("Suite"));
    }
}

class ConcurrentBookingProcessor implements Runnable {

    private BookingRequestQueue bookingQueue;
    private RoomInventory inventory;
    private RoomAllocationService allocationService;

    public ConcurrentBookingProcessor(
            BookingRequestQueue bookingQueue,
            RoomInventory inventory,
            RoomAllocationService allocationService) {

        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
        this.allocationService = allocationService;
    }

    public void run() {

        while (true) {

            Reservation reservation;

            synchronized (bookingQueue) {
                reservation = bookingQueue.getNextRequest();
            }

            if (reservation == null) {
                break;
            }

            synchronized (inventory) {
                allocationService.allocateRoom(reservation, inventory);
            }
        }
    }
}

class BookingRequestQueue {

    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        queue.add(reservation);
    }

    public Reservation getNextRequest() {
        return queue.poll();
    }
}

class RoomAllocationService {

    private Map<String, Integer> roomCounters;

    public RoomAllocationService() {
        roomCounters = new HashMap<>();
    }

    public void allocateRoom(Reservation reservation, RoomInventory inventory) {

        String roomType = reservation.getRoomType();

        if (!inventory.isAvailable(roomType)) {
            return;
        }

        int number = roomCounters.getOrDefault(roomType, 0) + 1;
        roomCounters.put(roomType, number);

        String roomId = roomType + "-" + number;

        inventory.bookRoom(roomType);

        System.out.println("Booking confirmed for Guest: " + reservation.getGuestName() + ", Room ID: " + roomId);
    }
}

class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single", 5);
        inventory.put("Double", 3);
        inventory.put("Suite", 2);
    }

    public boolean isAvailable(String roomType) {
        return inventory.getOrDefault(roomType, 0) > 0;
    }

    public void bookRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public int getAvailableRooms(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}

class Reservation {

    private String guestName;
    private String roomType;

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
}
import java.util.*;

public class BookMyStay {

    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();

        Reservation r1 = new Reservation("Alice", "Single");
        r1.setRoomId("SI-1");

        Reservation r2 = new Reservation("Bob", "Double");
        r2.setRoomId("DO-1");

        Reservation r3 = new Reservation("Charlie", "Suite");
        r3.setRoomId("SU-1");

        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);

        BookingReportService reportService = new BookingReportService();
        reportService.generateReport(history);
    }
}

class BookingHistory {

    private List<Reservation> confirmedReservations;

    public BookingHistory() {
        confirmedReservations = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        confirmedReservations.add(reservation);
    }

    public List<Reservation> getConfirmedReservations() {
        return confirmedReservations;
    }
}

class BookingReportService {

    public void generateReport(BookingHistory history) {

        List<Reservation> reservations = history.getConfirmedReservations();

        System.out.println("Booking Report");
        System.out.println("----------------");

        for (Reservation r : reservations) {
            System.out.println("Guest: " + r.getGuestName());
            System.out.println("Room Type: " + r.getRoomType());
            System.out.println("Room ID: " + r.getRoomId());
            System.out.println();
        }

        System.out.println("Total Reservations: " + reservations.size());
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
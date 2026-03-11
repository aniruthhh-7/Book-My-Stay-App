import java.util.*;

public class BookMyStay {

    public static void main(String[] args) {

        AddOnService breakfast = new AddOnService("Breakfast", 15.0);
        AddOnService spa = new AddOnService("Spa", 50.0);
        AddOnService airportPickup = new AddOnService("Airport Pickup", 30.0);

        AddOnServiceManager manager = new AddOnServiceManager();

        String reservationId = "RES101";

        manager.addService(reservationId, breakfast);
        manager.addService(reservationId, spa);
        manager.addService(reservationId, airportPickup);

        double totalCost = manager.calculateTotalServiceCost(reservationId);

        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Total Add-On Cost: $" + totalCost);
    }
}

class AddOnService {

    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }
}

class AddOnServiceManager {

    private Map<String, List<AddOnService>> servicesByReservation;

    public AddOnServiceManager() {
        servicesByReservation = new HashMap<>();
    }

    public void addService(String reservationId, AddOnService service) {

        servicesByReservation
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);
    }

    public double calculateTotalServiceCost(String reservationId) {

        List<AddOnService> services = servicesByReservation.get(reservationId);

        if (services == null) {
            return 0;
        }

        double total = 0;

        for (AddOnService service : services) {
            total += service.getCost();
        }

        return total;
    }
}

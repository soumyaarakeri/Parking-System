import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The "brain" of the project. It owns all the slots, remembers which
 * vehicles are parked right now, and keeps a history of finished parkings.
 */
public class ParkingLot {

    private final String name;
    private final List<ParkingSlot> slots = new ArrayList<>();
    private final Map<Integer, Ticket> activeTickets = new HashMap<>();   // ticket number -> ticket
    private final List<Ticket> history = new ArrayList<>();               // finished parkings
    private final FeeCalculator feeCalculator;
    private int totalRevenue = 0;

    public ParkingLot(String name, int bikeSlots, int carSlots, int truckSlots,
                      FeeCalculator feeCalculator) {
        this.name = name;
        this.feeCalculator = feeCalculator;
        createSlots(VehicleType.BIKE, "B", bikeSlots);    // B1, B2, B3 ...
        createSlots(VehicleType.CAR, "C", carSlots);      // C1, C2, C3 ...
        createSlots(VehicleType.TRUCK, "T", truckSlots);  // T1, T2 ...
    }

    public String getName() {
        return name;
    }

    private void createSlots(VehicleType type, String prefix, int count) {
        for (int i = 1; i <= count; i++) {
            slots.add(new ParkingSlot(prefix + i, type));
        }
    }

    // ---------- Park a vehicle ----------
    public Ticket parkVehicle(Vehicle vehicle) throws ParkingException {
        if (findActiveTicketByPlate(vehicle.getPlateNumber()) != null) {
            throw new ParkingException("Vehicle " + vehicle.getPlateNumber() + " is already parked.");
        }

        ParkingSlot slot = findFreeSlot(vehicle.getType());
        if (slot == null) {
            throw new ParkingException("Sorry, no free " + vehicle.getType() + " slot available.");
        }

        slot.park(vehicle);
        Ticket ticket = new Ticket(vehicle, slot);
        activeTickets.put(ticket.getTicketId(), ticket);
        return ticket;
    }

    // ---------- Vehicle leaves ----------
    public Ticket exitVehicle(int ticketId) throws ParkingException {
        return exitVehicle(ticketId, LocalDateTime.now());
    }

    // Same as above but lets us choose the exit time (useful for testing fees).
    public Ticket exitVehicle(int ticketId, LocalDateTime exitTime) throws ParkingException {
        Ticket ticket = activeTickets.get(ticketId);
        if (ticket == null) {
            throw new ParkingException("Invalid ticket number: " + ticketId);
        }

        int fee = feeCalculator.calculateFee(ticket.getVehicle().getType(),
                ticket.getEntryTime(), exitTime);
        ticket.closeTicket(exitTime, fee);

        ticket.getSlot().removeVehicle();     // slot becomes free again
        activeTickets.remove(ticketId);
        history.add(ticket);
        totalRevenue += fee;
        return ticket;
    }

    // ---------- Searching ----------
    private ParkingSlot findFreeSlot(VehicleType type) {
        for (ParkingSlot slot : slots) {
            if (slot.getType() == type && slot.isFree()) {
                return slot;                  // first free slot = nearest slot
            }
        }
        return null;
    }

    public Ticket findActiveTicketByPlate(String plate) {
        String cleaned = plate.replaceAll("[\\s-]", "").toUpperCase();
        for (Ticket ticket : activeTickets.values()) {
            if (ticket.getVehicle().getPlateNumber().equals(cleaned)) {
                return ticket;
            }
        }
        return null;
    }

    // ---------- Screens ----------
    public void showAvailability() {
        System.out.println();
        System.out.println("--- Available slots in " + name + " ---");
        for (VehicleType type : VehicleType.values()) {
            int total = 0;
            int free = 0;
            for (ParkingSlot slot : slots) {
                if (slot.getType() == type) {
                    total++;
                    if (slot.isFree()) {
                        free++;
                    }
                }
            }
            System.out.println(type + " : " + free + " free out of " + total);
        }
    }

    public void showStatus() {
        System.out.println();
        System.out.println("--- Parking lot status ---");
        for (ParkingSlot slot : slots) {
            if (slot.isFree()) {
                System.out.println(slot.getId() + " [" + slot.getType() + "]  -> FREE");
            } else {
                System.out.println(slot.getId() + " [" + slot.getType() + "]  -> "
                        + slot.getParkedVehicle().getPlateNumber());
            }
        }
    }

    public void showRevenueReport() {
        System.out.println();
        System.out.println("--- Revenue report ---");
        System.out.println("Vehicles currently parked : " + activeTickets.size());
        System.out.println("Vehicles served so far    : " + history.size());
        System.out.println("Total money collected     : Rs. " + totalRevenue);
        if (!history.isEmpty()) {
            System.out.println();
            System.out.println("Completed parkings:");
            for (Ticket ticket : history) {
                System.out.println("  " + ticket.toSummary()
                        + " | Stayed: " + ticket.getDurationText()
                        + " | Paid: Rs. " + ticket.getFee());
            }
        }
    }
}

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A ticket is given when a vehicle enters.
 * It remembers who parked, where, and at what time.
 * When the vehicle leaves, the exit time and fee are stored in it too.
 */
public class Ticket {

    private static final DateTimeFormatter FORMAT =
            DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm");

    private static int nextId = 1001;   // shared counter, so every ticket gets a new number

    private final int ticketId;
    private final Vehicle vehicle;
    private final ParkingSlot slot;
    private final LocalDateTime entryTime;
    private LocalDateTime exitTime;     // null until the vehicle leaves
    private int fee;

    public Ticket(Vehicle vehicle, ParkingSlot slot) {
        this.ticketId = nextId++;
        this.vehicle = vehicle;
        this.slot = slot;
        this.entryTime = LocalDateTime.now();
    }

    public int getTicketId() {
        return ticketId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ParkingSlot getSlot() {
        return slot;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public int getFee() {
        return fee;
    }

    // Called when the vehicle leaves.
    public void closeTicket(LocalDateTime exitTime, int fee) {
        this.exitTime = exitTime;
        this.fee = fee;
    }

    // How long the vehicle has stayed (or stayed in total, if it already left).
    public String getDurationText() {
        LocalDateTime end = (exitTime != null) ? exitTime : LocalDateTime.now();
        long minutes = Duration.between(entryTime, end).toMinutes();
        return (minutes / 60) + " hr " + (minutes % 60) + " min";
    }

    public void printEntryTicket() {
        System.out.println();
        System.out.println("========== PARKING TICKET ==========");
        System.out.println("Ticket No : " + ticketId);
        System.out.println("Vehicle   : " + vehicle.getPlateNumber() + " (" + vehicle.getType() + ")");
        System.out.println("Slot      : " + slot.getId());
        System.out.println("Entry     : " + entryTime.format(FORMAT));
        System.out.println("====================================");
        System.out.println("Please keep this ticket number safe!");
    }

    public void printExitReceipt() {
        System.out.println();
        System.out.println("========== PARKING RECEIPT =========");
        System.out.println("Ticket No : " + ticketId);
        System.out.println("Vehicle   : " + vehicle.getPlateNumber() + " (" + vehicle.getType() + ")");
        System.out.println("Slot      : " + slot.getId());
        System.out.println("Entry     : " + entryTime.format(FORMAT));
        System.out.println("Exit      : " + exitTime.format(FORMAT));
        System.out.println("Duration  : " + getDurationText());
        System.out.println("Fee to pay: Rs. " + fee);
        System.out.println("====================================");
        System.out.println("Thank you! Drive safe.");
    }

    // One-line summary, used in the "search" and "report" screens.
    public String toSummary() {
        return "Ticket " + ticketId + " | " + vehicle.getPlateNumber()
                + " (" + vehicle.getType() + ") | Slot " + slot.getId()
                + " | In: " + entryTime.format(FORMAT);
    }
}

/**
 * One parking space. A slot has an id (like "C1"), a type (BIKE/CAR/TRUCK)
 * and either holds a vehicle or is empty.
 */
public class ParkingSlot {

    private final String id;
    private final VehicleType type;
    private Vehicle parkedVehicle;   // null means the slot is free

    public ParkingSlot(String id, VehicleType type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public VehicleType getType() {
        return type;
    }

    public Vehicle getParkedVehicle() {
        return parkedVehicle;
    }

    public boolean isFree() {
        return parkedVehicle == null;
    }

    public void park(Vehicle vehicle) {
        this.parkedVehicle = vehicle;
    }

    public void removeVehicle() {
        this.parkedVehicle = null;
    }
}

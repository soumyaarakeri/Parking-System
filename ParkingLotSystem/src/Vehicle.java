/**
 * Vehicle is an ABSTRACT class: we never create a plain "Vehicle".
 * We only create a Bike, a Car or a Truck (they extend this class).
 */
public abstract class Vehicle {

    private final String plateNumber;

    public Vehicle(String plateNumber) {
        // Clean the plate: remove spaces/hyphens and make it UPPERCASE,
        // so "ka 28 ab 1234" and "KA28AB1234" are treated as the same vehicle.
        this.plateNumber = plateNumber.replaceAll("[\\s-]", "").toUpperCase();
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    // Every child class must say what type it is.
    public abstract VehicleType getType();
}

public class Bike extends Vehicle {

    public Bike(String plateNumber) {
        super(plateNumber);
    }

    @Override
    public VehicleType getType() {
        return VehicleType.BIKE;
    }
}

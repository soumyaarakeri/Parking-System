public class Truck extends Vehicle {

    public Truck(String plateNumber) {
        super(plateNumber);
    }

    @Override
    public VehicleType getType() {
        return VehicleType.TRUCK;
    }
}

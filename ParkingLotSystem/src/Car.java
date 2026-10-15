public class Car extends Vehicle {

    public Car(String plateNumber) {
        super(plateNumber);
    }

    @Override
    public VehicleType getType() {
        return VehicleType.CAR;
    }
}

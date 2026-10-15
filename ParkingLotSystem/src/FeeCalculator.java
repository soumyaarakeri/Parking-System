import java.time.LocalDateTime;

/**
 * An interface is a "contract": any class that implements it
 * must provide the calculateFee method.
 *
 * Why use it? Tomorrow you can write a DailyFeeCalculator or a WeekendFeeCalculator
 * and plug it into the ParkingLot without changing the ParkingLot code.
 */
public interface FeeCalculator {

    int calculateFee(VehicleType type, LocalDateTime entryTime, LocalDateTime exitTime);
}

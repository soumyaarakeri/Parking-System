import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Charges per hour. Rules:
 *  - Every started hour is charged (1 hr 5 min = 2 hours).
 *  - Minimum charge is 1 hour.
 *  - Rates:  BIKE Rs.10/hr,  CAR Rs.20/hr,  TRUCK Rs.40/hr
 */
public class HourlyFeeCalculator implements FeeCalculator {

    @Override
    public int calculateFee(VehicleType type, LocalDateTime entryTime, LocalDateTime exitTime) {
        long minutes = Duration.between(entryTime, exitTime).toMinutes();

        long hours = (minutes + 59) / 60;   // rounds UP to the next full hour
        if (hours < 1) {
            hours = 1;                       // minimum 1 hour
        }

        int ratePerHour;
        switch (type) {
            case BIKE:
                ratePerHour = 10;
                break;
            case CAR:
                ratePerHour = 20;
                break;
            default:                         // TRUCK
                ratePerHour = 40;
                break;
        }

        return (int) (hours * ratePerHour);
    }
}

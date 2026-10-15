/**
 * Our own exception class.
 * We throw this whenever something goes wrong in the parking lot,
 * for example: lot is full, ticket number is wrong, vehicle already parked.
 */
public class ParkingException extends Exception {

    private static final long serialVersionUID = 1L;

    public ParkingException(String message) {
        super(message);
    }
}

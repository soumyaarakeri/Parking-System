import java.util.Scanner;

/**
 * Entry point of the program: shows a menu and talks to the user.
 * All the real work is done by the ParkingLot class.
 */
public class Main {

    public static void main(String[] args) {
        // A small lot: 3 bike slots, 5 car slots, 2 truck slots.
        ParkingLot lot = new ParkingLot("City Center Parking", 3, 5, 2, new HourlyFeeCalculator());
        Scanner scanner = new Scanner(System.in);

        System.out.println("=====================================");
        System.out.println("  Welcome to " + lot.getName());
        System.out.println("=====================================");

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt(scanner, "Enter your choice: ");

            switch (choice) {
                case 1:
                    handlePark(lot, scanner);
                    break;
                case 2:
                    handleExit(lot, scanner);
                    break;
                case 3:
                    lot.showAvailability();
                    break;
                case 4:
                    handleSearch(lot, scanner);
                    break;
                case 5:
                    lot.showStatus();
                    break;
                case 6:
                    lot.showRevenueReport();
                    break;
                case 7:
                    running = false;
                    System.out.println("Goodbye! Thanks for using the Parking Lot System.");
                    break;
                default:
                    System.out.println("Invalid choice. Please pick a number from 1 to 7.");
            }
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("-------------- MENU --------------");
        System.out.println("1. Park a vehicle");
        System.out.println("2. Exit a vehicle (pay and leave)");
        System.out.println("3. Show available slots");
        System.out.println("4. Search vehicle by number plate");
        System.out.println("5. View parking lot status");
        System.out.println("6. Revenue report");
        System.out.println("7. Quit");
        System.out.println("----------------------------------");
    }

    // ---------- Menu actions ----------

    private static void handlePark(ParkingLot lot, Scanner scanner) {
        String plate = readPlate(scanner);

        int typeChoice = 0;
        while (typeChoice < 1 || typeChoice > 3) {
            System.out.println("Vehicle type:  1. Bike   2. Car   3. Truck");
            typeChoice = readInt(scanner, "Choose type (1-3): ");
            if (typeChoice < 1 || typeChoice > 3) {
                System.out.println("Please choose 1, 2 or 3.");
            }
        }

        Vehicle vehicle;
        if (typeChoice == 1) {
            vehicle = new Bike(plate);
        } else if (typeChoice == 2) {
            vehicle = new Car(plate);
        } else {
            vehicle = new Truck(plate);
        }

        try {
            Ticket ticket = lot.parkVehicle(vehicle);
            ticket.printEntryTicket();
        } catch (ParkingException e) {
            System.out.println("Could not park: " + e.getMessage());
        }
    }

    private static void handleExit(ParkingLot lot, Scanner scanner) {
        int ticketId = readInt(scanner, "Enter ticket number: ");
        try {
            Ticket ticket = lot.exitVehicle(ticketId);
            ticket.printExitReceipt();
        } catch (ParkingException e) {
            System.out.println("Could not exit: " + e.getMessage());
        }
    }

    private static void handleSearch(ParkingLot lot, Scanner scanner) {
        String plate = readPlate(scanner);
        Ticket ticket = lot.findActiveTicketByPlate(plate);
        if (ticket == null) {
            System.out.println("That vehicle is not parked here right now.");
        } else {
            System.out.println("Found: " + ticket.toSummary());
            System.out.println("Parked for: " + ticket.getDurationText());
        }
    }

    // ---------- Input helpers (keep asking until the input is valid) ----------

    private static int readInt(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static String readPlate(Scanner scanner) {
        while (true) {
            System.out.print("Enter vehicle number plate (e.g. KA28AB1234): ");
            String plate = scanner.nextLine().trim();
            // 4 to 15 characters: letters, digits, spaces or hyphens
            if (plate.matches("[A-Za-z0-9 -]{4,15}")) {
                return plate;
            }
            System.out.println("Invalid plate. Use 4-15 letters/digits only.");
        }
    }
}

# Parking Lot System (Console Based Core Java)

## What is this project? (in easy words)

This is a small program that works like the parking area of a mall.

- A car, bike or truck **comes in** → the program gives it a **parking slot** and a **ticket**.
- When the vehicle **leaves** → you type the ticket number, and the program **calculates the fee** based on how long it stayed.
- You can also check **free slots**, **search a vehicle** by number plate, see the **whole lot status**, and view the **money collected**.

Everything runs in the console (black screen with text). No database, no GUI, only Core Java.

## What you will learn from it

| Concept | Where you see it |
|---|---|
| Classes and objects | `ParkingSlot`, `Ticket`, `ParkingLot` |
| Abstract class + inheritance | `Vehicle` -> `Bike`, `Car`, `Truck` |
| Enum | `VehicleType` (BIKE, CAR, TRUCK) |
| Interface + polymorphism | `FeeCalculator` -> `HourlyFeeCalculator` |
| Collections | `ArrayList` (slots, history), `HashMap` (active tickets) |
| Custom exception | `ParkingException` (lot full, wrong ticket, already parked) |
| Date and time | `LocalDateTime`, `Duration` (entry time, exit time, fee) |
| Console input | `Scanner` with input checking in `Main` |

## Parking rules and fees

- Lot size: **3 bike slots, 5 car slots, 2 truck slots** (change it in `Main.java`).
- Every vehicle gets the first free slot of its own type.
- Fee per hour: **Bike Rs.10, Car Rs.20, Truck Rs.40**.
- Every started hour is charged (1 hr 5 min counts as 2 hours). Minimum charge is 1 hour.

## Files in the project

```
ParkingLotSystem/
├── README.md
└── src/
    ├── Main.java                 (menu + user input)
    ├── ParkingLot.java           (main logic: park, exit, search, reports)
    ├── ParkingSlot.java          (one parking space)
    ├── Ticket.java               (entry time, exit time, fee)
    ├── Vehicle.java              (abstract parent class)
    ├── Bike.java / Car.java / Truck.java
    ├── VehicleType.java          (enum)
    ├── FeeCalculator.java        (interface)
    ├── HourlyFeeCalculator.java  (fee rules)
    └── ParkingException.java     (our own error type)
```

## How to run in VS Code (step by step)

### One-time setup
1. Install **JDK 17 or newer** (for example Temurin/Adoptium or Oracle JDK).
2. Install **VS Code**.
3. In VS Code, open the **Extensions** tab (Ctrl+Shift+X), search for **"Extension Pack for Java"** (by Microsoft) and click **Install**.
4. Restart VS Code.

### Run the project
1. Unzip `ParkingLotSystem.zip`.
2. In VS Code: **File -> Open Folder...** and choose the `ParkingLotSystem` folder.
3. Open `src/Main.java`.
4. Click the **Run** button (small triangle at the top-right), or click **Run** just above `public static void main`.
5. A **Terminal** panel opens at the bottom. Click inside it and type your menu choice, then press Enter.

### Alternative: run from the VS Code terminal
Open the terminal (Ctrl+`) inside the `ParkingLotSystem` folder and run:

```
javac -d bin src/*.java
java -cp bin Main
```

(On Windows PowerShell, if `src/*.java` does not work, use: `javac -d bin (Get-ChildItem src\*.java)`)

## Sample run

```
-------------- MENU --------------
1. Park a vehicle
...
Enter your choice: 1
Enter vehicle number plate (e.g. KA28AB1234): KA28AB1234
Vehicle type:  1. Bike   2. Car   3. Truck
Choose type (1-3): 2

========== PARKING TICKET ==========
Ticket No : 1001
Vehicle   : KA28AB1234 (CAR)
Slot      : C1
...
```

## Common problems

- **"java is not recognized"** -> JDK is not installed or not on PATH. Install it, then restart VS Code.
- **No Run button** -> The Java extension is not installed yet, or is still loading (look at the bottom-right of VS Code).
- **Typing does nothing** -> Click inside the Terminal panel first, then type.

## Ideas to extend it later

- Multiple floors
- A `DailyFeeCalculator` (just implement `FeeCalculator`)
- Save and load parking history from a file
- Reserved or EV charging slots

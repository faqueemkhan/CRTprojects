import java.util.*;

// Fixed: Capitalized "Vehicle" to maintain consistency
abstract class Vehicle {
    String number;

    Vehicle(String number) {
        this.number = number;
    }

    // Fixed: CamelCase naming convention (calculateFee)
    abstract int calculateFee(int hours);
}

class Car extends Vehicle {
    Car(String number) {
        super(number);
    }

    @Override
    int calculateFee(int hours) {
        return hours * 50;
    }
}

class Bike extends Vehicle {
    Bike(String number) {
        super(number);
    }

    @Override
    int calculateFee(int hours) {
        return hours * 20;
    }
}

class ParkingSlot {
    int slotNumber;
    Vehicle vehicle; // Fixed: Changed 'vehicle' type to 'Vehicle'

    ParkingSlot(int slotNumber) {
        this.slotNumber = slotNumber;
    }

    boolean isEmpty() {
        return vehicle == null;
    }
}

class ParkingLot {
    ArrayList<ParkingSlot> slots = new ArrayList<>();

    ParkingLot(int size) {
        for (int i = 1; i <= size; i++) {
            slots.add(new ParkingSlot(i));
        }
    }

    void parkVehicle(Vehicle vehicle) {
        for (ParkingSlot slot : slots) {
            if (slot.isEmpty()) {
                slot.vehicle = vehicle;
                System.out.println(vehicle.number + " parked at slot " + slot.slotNumber);
                return;
            }
        }
        System.out.println("Parking Full");
    }

    void removeVehicle(String number, int hours) {
        for (ParkingSlot slot : slots) {
            if (!slot.isEmpty() && slot.vehicle.number.equals(number)) {
                int fee = slot.vehicle.calculateFee(hours);
                slot.vehicle = null;
                // Fixed: Cleaned up the broken Rupee symbol encoding (₹)
                System.out.println(number + " removed. Fee = ₹" + fee);
                return;
            }
        }
        System.out.println("Vehicle not found.");
    }

    void display() {
        for (ParkingSlot slot : slots) {
            if (slot.isEmpty()) {
                System.out.println("Slot " + slot.slotNumber + " Empty");
            } else {
                System.out.println("Slot " + slot.slotNumber + " Occupied by " + slot.vehicle.number);
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        ParkingLot p = new ParkingLot(3);

        Vehicle car = new Car("MH12AB1234");
        Vehicle bike = new Bike("MH14XY5678");

        p.parkVehicle(car);
        p.parkVehicle(bike);

        System.out.println("\n--- Current Parking Status ---");
        p.display();

        System.out.println("\n--- Processing Exit ---");
        p.removeVehicle("MH12AB1234", 2);

        System.out.println("\n--- Updated Parking Status ---");
        p.display();
    }
}
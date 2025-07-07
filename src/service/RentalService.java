package service;

import model.*;

import java.time.LocalDate;
import java.util.*;

public class RentalService {
    private final List<Rental> rentals = new ArrayList<>();
    private final CarService carService;
    private final String RENTAL_FILE = "data/rentals.csv";

    public RentalService(CarService carService) {
        this.carService = carService;
        CSVService.ensureFileExists(RENTAL_FILE);
        loadRentalsFromCSV();
    }

    public void rentCarToCustomer(Car car, Customer customer, LocalDate rentDate) {
        if (car.isAvailable()) {
            car.rentOut();
            Rental rental = new Rental(car, customer, rentDate);
            rentals.add(rental);
            carService.updateCar(car);
            saveRentalsToCSV();
        } else {
            throw new IllegalStateException("Car is not available for renting.");
        }
    }

    public void returnCar(int carId, LocalDate returnDate) {
        for (Rental rental : rentals) {
            if (rental.getCar().getId() == carId && rental.getReturnDate() == null) {
                rental.setReturnDate(returnDate);
                rental.getCar().returnCar();
                carService.updateCar(rental.getCar());
                saveRentalsToCSV();
                return;
            }
        }
        throw new IllegalArgumentException("No active rental found for car ID " + carId);
    }

    private void loadRentalsFromCSV() {
        rentals.clear();
        List<String> lines = CSVService.readLines(RENTAL_FILE);
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length < 3) {
                System.out.println("Invalid line: " + line);
                continue;
            }

            int carId = Integer.parseInt(parts[0]);
            String customerName = parts[1];
            LocalDate rentDate = LocalDate.parse(parts[2]);
            LocalDate returnDate = (parts.length > 3 && !parts[3].isEmpty()) ? LocalDate.parse(parts[3]) : null;

            Car car = carService.findById(carId);
            if (car != null) {
                Customer customer = new Customer(customerName);
                Rental rental = new Rental(car, customer, rentDate);
                rental.setReturnDate(returnDate);

                rentals.add(rental);
            }
        }
    }

    private void saveRentalsToCSV() {
        List<String> lines = new ArrayList<>();
        for (Rental rental : rentals) {
            lines.add(rental.toCSV());
        }
        CSVService.writeLines(RENTAL_FILE, lines);
    }

    public List<Rental> getActiveRentals() {
        List<Rental> active = new ArrayList<>();
        for (Rental r : rentals) {
            if (r.getReturnDate() == null && r.getCar().isInOperation()) {
                active.add(r);
            }
        }
        return active;
    }

    public Rental getActiveRentalByCarId(int carId) {
        for (Rental rental : rentals) {
            if (rental.getCar().getId() == carId && rental.getReturnDate() == null) {
                return rental;
            }
        }
        return null;
    }

}

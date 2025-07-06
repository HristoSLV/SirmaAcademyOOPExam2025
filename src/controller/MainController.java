package controller;

import model.*;
import service.*;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class MainController {
    private final CarService carService;
    private final RentalService rentalService;
    private final Scanner scanner;

    public MainController() {
        carService = new CarService();
        rentalService = new RentalService(carService);
        scanner = new Scanner(System.in);
    }

    public void start() {
        boolean running = true;
        while (running) {
            printMenu();
            String input = scanner.nextLine().trim();
            switch (input) {
                case "1":
                    addCar();
                    break;
                case "2":
                    editCar();
                    break;
                case "3":
                    rentCar();
                    break;
                case "4":
                    returnCar();
                    break;
                case "5":
                    removeCar();
                    break;
                case "6":
                    listAvailableCars();
                    break;
                case "7":
                    running = false;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
        scanner.close();
    }

    private void printMenu() {
        System.out.println("\nCar Rental System Menu:");
        System.out.println("1. Add Car");
        System.out.println("2. Edit Car");
        System.out.println("3. Rent Car");
        System.out.println("4. Return Car");
        System.out.println("5. Remove Car");
        System.out.println("6. List Available Cars");
        System.out.println("7. Exit");
        System.out.print("Enter choice: ");
    }

    private void addCar() {
        try {
            System.out.print("Enter car type (Saloon, SUV, Coupe): ");
            String type = scanner.nextLine().trim();

            System.out.print("Enter id (int): ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Enter brand: ");
            String brand = scanner.nextLine().trim();

            System.out.print("Enter model: ");
            String model = scanner.nextLine().trim();

            System.out.print("Enter year (int): ");
            int year = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Enter price per day (int): ");
            int pricePerDay = Integer.parseInt(scanner.nextLine().trim());

            Car car;
            switch (type) {
                case "Saloon":
                    car = new Saloon(id, brand, model, year, pricePerDay);
                    break;
                case "SUV":
                    car = new SUV(id, brand, model, year, pricePerDay);
                    break;
                case "Coupe":
                    car = new Coupe(id, brand, model, year, pricePerDay);
                    break;
                default:
                    System.out.println("Unknown car type. Car not added.");
                    return;
            }

            carService.addCar(car);
            System.out.println("Car added successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter numeric values for id, year, and price.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error adding car: " + e.getMessage());
        }
    }

    private void editCar() {
        try {
            System.out.print("Enter car id to edit: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            Car car = carService.findById(id);
            if (car == null) {
                System.out.println("Car with id " + id + " not found.");
                return;
            }

            System.out.print("Enter new brand (" + car.getBrand() + "): ");
            String brand = scanner.nextLine().trim();
            if (!brand.isEmpty()) car.setBrand(brand);

            System.out.print("Enter new model (" + car.getModel() + "): ");
            String model = scanner.nextLine().trim();
            if (!model.isEmpty()) car.setModel(model);

            System.out.print("Enter new year (" + car.getYear() + "): ");
            String yearStr = scanner.nextLine().trim();
            if (!yearStr.isEmpty()) car.setYear(Integer.parseInt(yearStr));

            System.out.print("Enter new price per day (" + car.getPricePerDay() + "): ");
            String priceStr = scanner.nextLine().trim();
            if (!priceStr.isEmpty()) car.setPricePerDay(Integer.parseInt(priceStr));

            carService.updateCar(car);
            System.out.println("Car updated successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid numeric input.");
        }
    }

    private void rentCar() {
        try {
            System.out.print("Enter car id to rent: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            Car car = carService.findById(id);
            if (car == null) {
                System.out.println("Car with id " + id + " not found.");
                return;
            }

            if (!car.isAvailable()) {
                System.out.println("Car is not available.");
                return;
            }

            System.out.print("Enter customer name: ");
            String name = scanner.nextLine().trim();
            Customer customer = new Customer(name);

            rentalService.rentCarToCustomer(car, customer, LocalDate.now());
            System.out.println("Car rented successfully to " + name + ".");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void returnCar() {
        List<Rental> activeRentals = rentalService.getActiveRentals();
        if (activeRentals.isEmpty()) {
            System.out.println("No cars currently rented out.");
            return;
        }

        System.out.println("Currently rented cars:");
        for (Rental r : activeRentals) {
            System.out.println("Car ID: " + r.getCar().getId() + ", Model: " + r.getCar().getModel() + ", Customer: " + r.getCustomer().getName() + ", Rent Date: " + r.getRentDate());
        }

        try {
            System.out.print("Enter car id to return: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            rentalService.returnCar(id, LocalDate.now());
            System.out.println("Car returned successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        } catch (NoSuchElementException e) {
            System.out.println("Rental not found for the given car id.");
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void removeCar() {
        try {
            System.out.print("Enter car id to remove from fleet: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            Car car = carService.findById(id);
            if (car == null) {
                System.out.println("Car with id " + id + " not found.");
                return;
            }

            car.setInOperation(false);
            carService.updateCar(car);
            System.out.println("Car removed from fleet (marked not in operation).");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }

    private void listAvailableCars() {
        List<Car> availableCars = carService.getAllAvailableCars();
        if (availableCars.isEmpty()) {
            System.out.println("No available cars.");
            return;
        }
        System.out.println("Available cars:");
        for (Car car : availableCars) {
            System.out.println(car);
        }
    }
}

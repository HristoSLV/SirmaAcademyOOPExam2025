package service;

import model.*;

import java.util.*;

public class CarService {
    private final Map<Integer, Car> cars = new HashMap<>();
    private final String CAR_FILE = "data/cars.csv";

    public CarService() {
        loadCarsFromCSV();
    }

    public void addCar(Car car) {
        cars.put(car.getId(), car);
        saveCarsToCSV();
    }

    public void updateCar(Car car) {
        cars.put(car.getId(), car);
        saveCarsToCSV();
    }

    public void removeCar(int carId) {
        Car car = cars.get(carId);
        if (car != null) {
            car.setInOperation(false);
            car.returnCar();
            updateCar(car);
        }
    }

    public Car findById(int id) {
        return cars.get(id);
    }

    public List<Car> searchByAvailability(boolean available) {
        List<Car> result = new ArrayList<>();
        for (Car c : cars.values()) {
            if (c.isAvailable() == available) {
                result.add(c);
            }
        }
        return result;
    }

    public List<Car> getAllAvailableCars() {
        List<Car> availableCars = new ArrayList<>();
        for (Car c : cars.values()) {
            if (c.isAvailable()) {
                availableCars.add(c);
            }
        }
        return availableCars;
    }

    private void loadCarsFromCSV() {

        List<String> lines = CSVService.readLines(CAR_FILE);
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length < 8) continue;
            String type = parts[0];
            int id = Integer.parseInt(parts[1]);
            String brand = parts[2];
            String model = parts[3];
            int year = Integer.parseInt(parts[4]);
            int pricePerDay = Integer.parseInt(parts[5]);
            boolean available = Boolean.parseBoolean(parts[6]);
            boolean inOperation = Boolean.parseBoolean(parts[7]);

            Car car = switch (type) {
                case "Saloon" -> new Saloon(brand, model, year, pricePerDay);
                case "SUV" -> new SUV(brand, model, year, pricePerDay);
                case "Coupe" -> new Coupe(brand, model, year, pricePerDay);
                default -> throw new IllegalArgumentException("Invalid car type: " + type);
            };

            car.setId(id);
            car.setInOperation(inOperation);
            car.setAvailable(available);

            cars.put(id, car);
        }
    }

    private void saveCarsToCSV() {
        List<String> lines = new ArrayList<>();
        for (Car car : cars.values()) {
            lines.add(car.toCSV());
        }
        CSVService.writeLines(CAR_FILE, lines);
    }

}

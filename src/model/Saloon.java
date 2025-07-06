package model;

public class Saloon extends Car {
    public Saloon(int id, String brand, String model, int year, int pricePerDay) {
        super(id, brand, model, year, pricePerDay);
    }

    @Override
    public String getType() {
        return "Saloon";
    }

}
package model;

public class Saloon extends Car {
    public Saloon(String brand, String model, int year, int pricePerDay) {
        super(brand, model, year, pricePerDay);
    }

    @Override
    public String getType() {
        return "Saloon";
    }

}
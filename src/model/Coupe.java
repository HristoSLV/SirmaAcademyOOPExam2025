package model;

public class Coupe extends Car {
    public Coupe(String brand, String model, int year, int pricePerDay) {
        super(brand, model, year, pricePerDay);
    }

    @Override
    public String getType() {
        return "Coupe";
    }

}
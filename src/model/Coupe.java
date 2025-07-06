package model;

public class Coupe extends Car {
    public Coupe(int id, String brand, String model, int year, int pricePerDay) {
        super(id, brand, model, year, pricePerDay);
    }

    @Override
    public String getType() {
        return "Coupe";
    }

}
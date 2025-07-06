package model;

public class SUV extends Car {
    public SUV(String brand, String model, int year, int pricePerDay) {
        super(brand, model, year, pricePerDay);
    }

    @Override
    public String getType() {
        return "SUV";
    }

}
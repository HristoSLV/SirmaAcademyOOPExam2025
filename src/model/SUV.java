package model;

public class SUV extends Car {
    public SUV(int id, String brand, String model, int year, int pricePerDay) {
        super(id, brand, model, year, pricePerDay);
    }

    @Override
    public String getType() {
        return "SUV";
    }

}
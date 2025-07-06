package model;

public interface Rentable {
    void rentOut();

    void returnCar();

    boolean isAvailable();
}
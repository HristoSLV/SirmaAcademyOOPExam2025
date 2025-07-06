package model;

import java.time.LocalDate;

public class Rental {
    private Car car;
    private Customer customer;
    private LocalDate rentDate;
    private LocalDate returnDate;

    public Rental(Car car, Customer customer, LocalDate rentDate) {
        this.car = car;
        this.customer = customer;
        this.rentDate = rentDate;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public LocalDate getRentDate() {
        return rentDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public String toCSV() {
        return car.getId() + "," + customer.getName() + "," + rentDate + "," + (returnDate != null ? returnDate : "");
    }
}
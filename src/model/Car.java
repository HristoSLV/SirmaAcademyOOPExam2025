package model;

public abstract class Car implements Rentable {
    private static int nextIdCounter = 1;


    protected int id;
    protected String brand;
    protected String model;
    protected int year;
    protected int pricePerDay;
    protected boolean available;
    protected boolean inOperation;

    public Car(String brand, String model, int year, int pricePerDay) {
        this.id = nextIdCounter;
        nextIdCounter++;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.pricePerDay = pricePerDay;
        this.available = true;
        this.inOperation = true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(int pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isInOperation() {
        return inOperation;
    }

    public void setInOperation(boolean inOperation) {
        this.inOperation = inOperation;
    }

    @Override
    public void rentOut() {
        if (available && inOperation) {
            available = false;
        } else {
            throw new IllegalStateException("Car is not available.");
        }
    }

    @Override
    public void returnCar() {
        if (inOperation) {
            available = true;
        } else {
            throw new IllegalStateException("Car is not in operation.");
        }
    }

    @Override
    public boolean isAvailable() {
        return available && inOperation;
    }

    public abstract String getType();

    public String toCSV() {
        return getType() + "," + id + "," + brand + "," + model + "," + year + "," + pricePerDay + "," + available + "," + inOperation;
    }

    @Override
    public String toString() {
        return getType() + "{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", pricePerDay=" + pricePerDay +
                ", available=" + isAvailable() +
                ", inOperation=" + inOperation +
                '}';
    }
}
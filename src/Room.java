public class Room {
    private String location;
    private int squareMeters;
    private double rentalFee;

    public Room(String location, int squareMeters, double rentalFee) {
        this.location = location;
        this.squareMeters = squareMeters;
        this.rentalFee = rentalFee;
    }

    public String getLocation() {
        return location;
    }

    public int getSquareMeters() {
        return squareMeters;
    }

    public double getRentalFee() {
        return rentalFee;
    }

    @Override
    public String toString() {
        return "User{" +
                "location='" + location + '\'' +
                ", squareMeters='" + squareMeters + '\'' +
                ", rentalFee='" + rentalFee + '\'' +
                '}';
    }
}

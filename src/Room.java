public class Room {

    private String location;
    private int squareMeters;
    private double rentalFee;
    private String landlord;

    public Room(String location, int squareMeters, double rentalFee, String landlord) {
        this.location = location;
        this.squareMeters = squareMeters;
        this.rentalFee = rentalFee;
        this.landlord = landlord;
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
        return "Room{" +
                "location='" + location + '\'' +
                ", squareMeters=" + squareMeters +
                ", rentalFee=" + rentalFee +
                ", landlord='" + landlord + '\'' +
                '}';
    }

    public String getLandlord() {
        return landlord;
    }
}

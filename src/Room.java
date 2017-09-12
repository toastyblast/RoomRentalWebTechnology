public class Room {

    private String location;
    private int squareMeters;
    private double rentalFee;
    private String landlord;
    private String renter;
    private boolean available;
    private int id;

    @Override
    public String toString() {
        return "Room{" +
                "location='" + location + '\'' +
                ", squareMeters=" + squareMeters +
                ", rentalFee=" + rentalFee +
                ", landlord='" + landlord + '\'' +
                ", renter='" + renter + '\'' +
                ", available=" + available +
                ", id=" + id +
                '}';
    }

    public Room(String location, int squareMeters, double rentalFee, String landlord, int id) {
        this.location = location;
        this.squareMeters = squareMeters;
        this.rentalFee = rentalFee;
        this.landlord = landlord;
        this.available = true;
        this.id = id;
    }

    public boolean isAvailable() {
        return available;
    }

    public int getId() {
        return id;
    }

    public void setAvailable(boolean available) {
        this.available = available;
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

    public String getLandlord() {
        return landlord;
    }

    public void setRenter(String renter) {
        this.renter = renter;
    }
}

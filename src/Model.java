import java.util.ArrayList;

public class Model {

    private ArrayList<User> registeredUsers = new ArrayList<>();
    private ArrayList<Room> addedRooms = new ArrayList<>();
    private int id = 3;

    public Model(){
        registeredUsers.add(new User("Martin", "123456", "tenant"));
        registeredUsers.add(new User("Yoran", "123456", "landlord"));

        addedRooms.add(new Room("Enschede", 15, 255, "Yoran", 0));
        addedRooms.add(new Room("Deventer", 20, 175, "Yoran",1));
    }

    public ArrayList<User> getRegisteredUsers() {
        return registeredUsers;
    }

    public void addRoom(String location, int squareMeter, double rentalFee, String landlord){
        getAddedRooms().add(new Room(location, squareMeter, rentalFee, landlord, id));
        id++;
    }

    public ArrayList<Room> getAddedRooms() {
        return addedRooms;
    }

    public boolean checkUser(User user){
        for (int i = 0 ; i < registeredUsers.size() ; i++){
            if (user.getName().equals(registeredUsers.get(i).getName()) &&
            user.getPass().equals(registeredUsers.get(i).getPass())){
                user.setOccupation(registeredUsers.get(i).getOccupation());
                return true;
            }
        }
        return false;
    }

    public ArrayList getRooms(User user){
        ArrayList<Room> userRooms = new ArrayList<>();
        if (user.getOccupation().equals("landlord")){
            for (int i = 0 ; i < addedRooms.size() ; i++){
                if (user.getName().equals(addedRooms.get(i).getLandlord())){
                    userRooms.add(addedRooms.get(i));
                }
            }
        }
        return userRooms;
    }

    public void bookRoom(int roomID, User user){
        for (int i = 0 ; i < addedRooms.size() ; i++){
            if (roomID == addedRooms.get(i).getId()){
                if (checkRoomAvailable(roomID)) {
                    addedRooms.get(i).setAvailable(false);
                    addedRooms.get(i).setRenter(user.getName());
                }
            }
        }
    }

    public Boolean checkRoomAvailable(int string){
        for (int i = 0 ; i < addedRooms.size() ; i++){
            if (string == addedRooms.get(i).getId()){
                return addedRooms.get(i).isAvailable();
            }
        }
        return false;
    }
}

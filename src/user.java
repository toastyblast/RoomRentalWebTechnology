public class user {

    String name;
    String pass;
    String occupation;

    public  user(String name, String pass, String occupation){
        this.name = name;
        this.pass = pass;
        this.occupation = occupation;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "user{" +
                "name='" + name + '\'' +
                ", pass='" + pass + '\'' +
                ", occupation='" + occupation + '\'' +
                '}';
    }
}

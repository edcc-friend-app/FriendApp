package edcc.friendfinder;

import java.util.ArrayList;

public class DataHandler {

    private ArrayList<User> list = new ArrayList<>();

    public DataHandler() {
        ArrayList<User> friends = new ArrayList<>();
        list.add(new User("Estefano", "Felipa", "e.felipa8865@edmail.edcc.edu",
                "2065551234", "CS", "Soccer", friends, "07/08/2000", 955123123, 18));
        list.add(new User("Anthony", "Luong", "a.luong1997@edmail.edcc.edu",
                "4259995512", "CS", "Memes", friends, "08/22/1999", 955111321, 21));
        list.add(new User("Jonathan", "Young", "j.young0214@edmail.edcc.edu",
                "4257771234", "CS", "Football", friends, "02/14/1982", 955008130, 37));
    }

    public ArrayList<User> getUsers() {
        return list;
    }

    public boolean addUsers(ArrayList<User> Users) {
        list = Users;
        return true;
    }
}

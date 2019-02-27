package edcc.friendfinder;

import java.util.ArrayList;

public class DataHandler {

    private ArrayList<User> list = new ArrayList<>();
    private ArrayList<Course> classes = new ArrayList<>();

    public DataHandler() {
        ArrayList<User> friends = new ArrayList<>();
        list.add(new User("Estefano", "Felipa","CS", "Soccer", friends, 955123123, 18));
        list.add(new User("Anthony", "Luong", "CS", "Memes", friends, 955111321, 21));
        list.add(new User("Jonathan", "Young", "CS", "Football", friends, 955008130, 37));
    }

    public ArrayList<User> getUsers() {
        return list;
    }

    public boolean addUsers(ArrayList<User> Users) {
        list = Users;
        return true;
    }
}

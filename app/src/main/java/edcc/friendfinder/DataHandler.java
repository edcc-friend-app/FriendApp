package edcc.friendfinder;

import java.util.ArrayList;

public class DataHandler {

    private ArrayList<User> list = new ArrayList<>();
    private ArrayList<Course> classes = new ArrayList<>();
    Course math = new Course("MATH 151", "Richard D");
    Course cs = new Course("CS 141", "Linda Z");
    Course english = new Course("ENG 101", "Pornhub");

    public DataHandler() {
        ArrayList<User> friends = new ArrayList<>();
        classes.add(math);
        classes.add(cs);
        classes.add(english);

        list.add(new User("Estefano", "Felipa","CS", "Soccer", friends, classes, 955123123, 18));
        list.add(new User("Anthony", "Luong", "CS", "Memes", friends, classes, 955111321, 21));
        list.add(new User("Jonathan", "Young", "CS", "Football", friends, classes, 955008130, 37));
    }

    public ArrayList<User> getUsers() {
        return list;
    }

    public boolean addUsers(ArrayList<User> Users) {
        list = Users;
        return true;
    }
}

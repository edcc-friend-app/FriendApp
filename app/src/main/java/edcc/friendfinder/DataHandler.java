package edcc.friendfinder;

import java.util.ArrayList;

public class DataHandler {

    private ArrayList<User> list = new ArrayList<>();
    private ArrayList<Course> classes = new ArrayList<>();
    private ArrayList<User> friends = new ArrayList<>();
    Course math = new Course("MATH 151", "Richard D");
    Course cs = new Course("CS 141", "Linda Z");
    Course english = new Course("ENG 101", "Kumar P");

    //String array of courses

    private String[] major = {"Accounting", "Allied Health Education", "Biology", "Business",
            "Business Information Technology", "Business Management", "Business Training Center",
            "Child, Youth, and Family Studies", "Computer Information Systems", "Computer Science",
            "Construction Management", "Culinary Arts", "Early Childhood Education", "Emergency Management",
            "Engineering and Science", "Engineering Technology", "Event Planning", "Family Support Studies",
            "General Studies", "Horticulture", "Hospitality and Tourism", "Music", "Nursing",
            "Occupational Safety and Health", "Paralegal", "Social and Human Services",
            "Transfer (General)", "Visual Communications"};;

    private String[] language = {"Arabic", "Chinese (Cantonese)", "Chinese (Mandarin)", "English",
            "French", "German", "Indonesian (Malay)", "Hindustani", "Japanese", "Korean", "Russian",
            "Spanish", "Vietnamese"};

    public DataHandler() {
        ArrayList<User> friends = new ArrayList<>();
        classes.add(math);
        classes.add(cs);
        classes.add(english);
        User friend1 = new User("Franshesco", "Coello","CS", "Soccer",
                friends, classes, 955123123, "English");
        list.add(friend1);
        list.add(new User("Anthony", "Luong", "CS", "Memes",
                friends, classes, 955111321, "Vietnamese"));
        list.add(new User("Jonathan", "Young", "CS", "Football",
                friends, classes, 955008130, "Chinese"));
    }

    public ArrayList<User> getUsers() {
        return list;
    }

    public String[] getMajor(){
        return major;
    }

    public String[] getLanguage(){
        return language;
    }

    public boolean addUsers(ArrayList<User> Users) {
        list = Users;
        return true;
    }
}

package edcc.friendfinder;

import java.util.ArrayList;

public class DataHandler {

    private ArrayList<User> list = new ArrayList<>();
    private ArrayList<Course> classes = new ArrayList<>();
    Course math = new Course("MATH 151", "Richard D");
    Course cs = new Course("CS 141", "Linda Z");
    Course english = new Course("ENG 101", "Kumar P");
    User friend1;
    User friend2;
    User friend3;
    User friend4;
    User friend5;
    User friend6;
    User friend7;
    User friend8;
    User friend9;
    User friend10;

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
        friend1 = new User("Franshesco", "Coello","CS", "Soccer",
                friends, classes, 955123123, "English");
        list.add(friend1);
        friend2 = new User("Anthony", "Luong", "CS", "Memes",
                friends, classes, 955111321, "Vietnamese");
        list.add(friend2);
        friend3 = new User("Jonathan", "Young", "Mathematics", "Football",
                friends, classes, 955008130, "Chinese");
        list.add(friend3);
        friend4 = new User("Susy", "Lincoln", "English", "Football",
                friends, classes, 955008130, "Chinese");
        list.add(friend4);
        friend5 = new User("Jared", "Kuddes", "English", "Football",
                friends, classes, 955008130, "Chinese");
        list.add(friend5);
        friend6 = new User("Sora", "Fung", "Biology", "Football",
                friends, classes, 955008130, "Chinese");
        list.add(friend6);
        friend7 = new User("Roxas", "Pham", "Mathematics", "Football",
                friends, classes, 955008130, "Chinese");
        list.add(friend7);
        friend8 = new User("Vivian", "Nguyen", "Biology", "Football",
                friends, classes, 955008130, "Chinese");
        list.add(friend8);
        friend9 = new User("Henry", "Zokkins", "Art", "Football",
                friends, classes, 955008130, "Chinese");
        list.add(friend9);
        friend10 = new User("Vy", "Bui", "Art", "Football",
                friends, classes, 955008130, "Chinese");
        list.add(friend10);
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

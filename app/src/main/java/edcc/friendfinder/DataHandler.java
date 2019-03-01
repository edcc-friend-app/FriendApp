package edcc.friendfinder;

import java.util.ArrayList;
import java.util.List;

public class DataHandler {

    private ArrayList<User> friends;
    private ArrayList<User> potFriends;
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
            "Transfer (General)", "Visual Communications"};

    private String[] language = {"Arabic", "Chinese (Cantonese)", "Chinese (Mandarin)", "English",
            "French", "German", "Indonesian (Malay)", "Hindustani", "Japanese", "Korean", "Russian",
            "Spanish", "Vietnamese"};

    public DataHandler() {
        friends = new ArrayList<>();
        potFriends = new ArrayList<>();
        classes.add(math);
        classes.add(cs);
        classes.add(english);

        friend1 = new User("Franshesco", "Coello","CS", "Soccer",
                classes, "English");
        List<Integer> classes1 = new ArrayList<>();
        classes1.add(0);
        classes1.add(1);
        classes1.add(2);
        friend1.setArrMatch(classes1);

        friend2 = new User("Anthony", "Luong", "CS", "Memes",
                classes, "Vietnamese");
        List<Integer> classes2 = new ArrayList<>();
        classes2.add(3);
        classes2.add(12);
        classes2.add(21);
        friend2.setArrMatch(classes2);

        friend3 = new User("Jonathan", "Young", "Mathematics", "Football",
                classes, "Chinese");
        List<Integer> classes3 = new ArrayList<>();
        classes3.add(8);
        classes3.add(5);
        classes3.add(24);
        friend3.setArrMatch(classes3);

        friend4 = new User("Susy", "Lincoln", "English", "Dancing",
                classes, "Spanish");
        List<Integer> classes4 = new ArrayList<>();
        classes4.add(13);
        classes4.add(4);
        classes4.add(19);
        friend4.setArrMatch(classes4);

        friend5 = new User("Jared", "Kuddes", "English", "Memes",
                classes, "French");
        List<Integer> classes5 = new ArrayList<>();
        classes5.add(23);
        classes5.add(1);
        classes5.add(16);
        friend5.setArrMatch(classes5);

        friend6 = new User("Sora", "Fung", "Biology", "Dancing",
                classes, "Chinese");
        List<Integer> classes6 = new ArrayList<>();
        classes6.add(3);
        classes6.add(21);
        classes6.add(6);
        friend6.setArrMatch(classes6);

        friend7 = new User("Roxas", "Pham", "Mathematics", "Guitar",
                classes, "Vietnamese");
        List<Integer> classes7 = new ArrayList<>();
        classes7.add(10);
        classes7.add(20);
        classes7.add(5);
        friend7.setArrMatch(classes7);

        friend8 = new User("Vivian", "Nguyen", "Biology", "Swimming",
                classes, "Vietnamese");
        List<Integer> classes8 = new ArrayList<>();
        classes8.add(2);
        classes8.add(3);
        classes8.add(5);
        friend8.setArrMatch(classes8);

        friend9 = new User("Henry", "Zokkins", "Art", "Tennis",
                classes, "English");
        List<Integer> classes9 = new ArrayList<>();
        classes9.add(3);
        classes9.add(15);
        classes9.add(22);
        friend9.setArrMatch(classes9);

        friend10 = new User("Vy", "Bui", "Art", "Swimming",
                classes, "Chinese");
        List<Integer> classes10 = new ArrayList<>();
        classes10.add(0);
        classes10.add(1);
        classes10.add(2);
        friend10.setArrMatch(classes10);

        //Add current Friends and potential Friends
        potFriends.add(friend1);
        potFriends.add(friend2);
        potFriends.add(friend3);
        potFriends.add(friend4);
        potFriends.add(friend5);
        potFriends.add(friend6);
        potFriends.add(friend7);
        potFriends.add(friend8);
        potFriends.add(friend9);
        potFriends.add(friend10);
    }

    public ArrayList<User> getFriends() {
        return friends;
    }

    public ArrayList<User> getPotFriends() {
        return potFriends;
    }

    public String[] getMajor(){
        return major;
    }

    public String[] getLanguage(){
        return language;
    }

    public boolean addFriends(ArrayList<User> Users) {
        friends = Users;
        return true;
    }

    public boolean addPotFriends(ArrayList<User> Users) {
        potFriends = Users;
        return true;
    }
}

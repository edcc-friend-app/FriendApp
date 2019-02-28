package edcc.friendfinder;

import java.util.ArrayList;

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
                friends, classes, 955123123, "English");
        int[] classes1 = new int[25];
        classes1[0] = 3;
        classes1[1] = 9;
        classes1[2] = 12;
        friend1.setArrMatch(classes1);
        friend2 = new User("Anthony", "Luong", "CS", "Memes",
                friends, classes, 955111321, "Vietnamese");
        int[] classes2 = new int[25];
        classes1[0] = 1;
        classes1[1] = 1;
        classes1[2] = 1;
        friend1.setArrMatch(classes2);
        friend3 = new User("Jonathan", "Young", "Mathematics", "Football",
                friends, classes, 955008130, "Chinese");
        int[] classes3 = new int[25];
        classes1[0] = 1;
        classes1[1] = 1;
        classes1[2] = 1;
        friend1.setArrMatch(classes1);
        friend4 = new User("Susy", "Lincoln", "English", "Dancing",
                friends, classes, 955062330, "Spanish");
        int[] classes4 = new int[25];
        classes1[0] = 1;
        classes1[1] = 1;
        classes1[2] = 1;
        friend1.setArrMatch(classes1);
        friend5 = new User("Jared", "Kuddes", "English", "Memes",
                friends, classes, 955025430, "French");
        int[] classes5 = new int[25];
        classes1[0] = 1;
        classes1[1] = 1;
        classes1[2] = 1;
        friend1.setArrMatch(classes1);
        friend6 = new User("Sora", "Fung", "Biology", "Dancing",
                friends, classes, 955073430, "Chinese");
        int[] classes6 = new int[25];
        classes1[0] = 1;
        classes1[1] = 1;
        classes1[2] = 1;
        friend1.setArrMatch(classes1);
        friend7 = new User("Roxas", "Pham", "Mathematics", "Guitar",
                friends, classes, 955013230, "Vietnamese");
        int[] classes7 = new int[25];
        classes1[0] = 1;
        classes1[1] = 1;
        classes1[2] = 1;
        friend1.setArrMatch(classes1);
        friend8 = new User("Vivian", "Nguyen", "Biology", "Swimming",
                friends, classes, 955008430, "Vietnamese");
        int[] classes8 = new int[25];
        classes1[0] = 1;
        classes1[1] = 1;
        classes1[2] = 1;
        friend1.setArrMatch(classes1);
        friend9 = new User("Henry", "Zokkins", "Art", "Tennis",
                friends, classes, 955073530, "English");
        int[] classes9 = new int[25];
        classes1[0] = 1;
        classes1[1] = 1;
        classes1[2] = 1;
        friend1.setArrMatch(classes1);
        friend10 = new User("Vy", "Bui", "Art", "Swimming",
                friends, classes, 955029630, "Chinese");
        int[] classes10 = new int[25];
        classes1[0] = 1;
        classes1[1] = 1;
        classes1[2] = 1;
        friend1.setArrMatch(classes1);
        //Add current Friends and potential Friends
        friends.add(friend1);
        friends.add(friend2);
        friends.add(friend3);
        friends.add(friend4);
        friends.add(friend5);
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

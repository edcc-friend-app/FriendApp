package edcc.friendfinder;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public class User implements Comparable<User> {
    private String firstName;
    private String lastName;
    private String major;
    private String bio;
    private ArrayList<User> friends;
    private ArrayList<Course> classes;
    private String language;
    private int id;
    //added from Linda's code
    static boolean listType;
    private String photo; //base64 encoded byte array
    //tests for match
    private int matchCount;
    private int[] arrMatch;
    private String[] courses;

    public User() {
    }

    public User(String firstName, String lastName, String major,
                String interests, ArrayList<User> friends, ArrayList<Course> classes, int id, String language) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.major = major;
        this.bio = interests;
        this.friends = friends;
        this.classes = classes;
        this.id = id;
        this.language = language;
        matchCount = 0;
        arrMatch = new int[25];
        courses = new String[]{"Accounting", "Allied Health Education", "Biology", "Business",
                "Business Information Technology", "Business Management", "Business Training Center",
                "Child, Youth, and Family Studies", "Computer Information Systems", "Computer Science",
                "Construction Management", "Culinary Arts", "Early Childhood Education", "Emergency Management",
                "Engineering and Science", "Engineering Technology", "Event Planning", "Family Support Studies",
                "General Studies", "Horticulture", "Hospitality and Tourism", "Music", "Nursing",
                "Occupational Safety and Health", "Paralegal", "Social and Human Services", "Transfer (General)", "Visual Communications"};
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public ArrayList<User> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<User> friends) {
        this.friends = friends;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setAge(String age) {
        this.language = language;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public ArrayList<Course> getClasses() {
        return classes;
    }

    public void setClasses(ArrayList<Course> classes) {
        this.classes = classes;
    }

    public int[] getArrMatch() {
        return arrMatch;
    }

    public void setArrMatch(int[] arrMatch) {
        this.arrMatch = arrMatch;
    }

    public String printClasses() {
        String strClasses = "";
        for (int i = 0; i < courses.length; i++) {
            if (arrMatch[i] == 1) {
                strClasses += courses[i] + '\n';
            }
        }
        return strClasses;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    @Override
    public int compareTo(User another) {
        String thisName = lastName.toLowerCase() + firstName.toLowerCase();
        String anotherName = another.lastName.toLowerCase() + another.firstName.toLowerCase();
        return thisName.compareTo(anotherName);
    }

    public int matchCompare(User other) {
        return this.matchCount - other.matchCount;
    }

    public void incrementCount(String type) {
        switch (type) {
            case "major":
                matchCount += 50;
                break;
            case "class":
                matchCount += 10;
                break;
            case "bio":
                matchCount += 20;
                break;
            case "language":
                matchCount += 30;
                break;

        }

    }


}

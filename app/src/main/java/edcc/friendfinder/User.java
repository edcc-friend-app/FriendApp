package edcc.friendfinder;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class User implements Comparable<User> {
    private String firstName;
    private String lastName;
    private String major;
    private String bio;
    //private ArrayList<User> friends;
    private ArrayList<Course> classes;
    private String language;
    private String availability;
    private String class1;
    private String class2;
    private String class3;
    private int id;
    static boolean listType;
    private String photo; //base64 encoded byte array
    private int friendId = -1;
    //tests for match
    private int matchCount;
    private List<Integer> arrMatch = new ArrayList<>();
    //private String[] courses;

    public User() {
        firstName = "";
        lastName = "";
        major = "";
        bio = "";
        classes = new ArrayList<>();
        language = "";
        matchCount = 0;
    }

    public User(String firstName, String lastName, String major,
                String interests, ArrayList<Course> classes, String language) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.major = major;
        this.bio = interests;
        this.classes = classes;
        this.language = language;
        matchCount = 0;
        //arrMatch = new ArrayList<>();
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

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public void setClass1(String class1) {
        this.class1 = class1;
    }

    public void setClass2(String class2) {
        this.class2 = class2;
    }

    public void setClass3(String class3) {
        this.class3 = class3;
    }

    public String getAvailability() {
        return availability;
    }

    public String getClass1() {
        return class1;
    }

    public String getClass2() {
        return class2;
    }

    public String getClass3() {
        return class3;
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

//    public ArrayList<User> getFriends() {
//        return friends;
//    }
//
//    public void setFriends(ArrayList<User> friends) {
//        this.friends = friends;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
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

    public List<Integer> getArrMatch() {
        return arrMatch;
    }

    public void setArrMatch(List<Integer> arrMatch) {
        this.arrMatch = arrMatch;
    }

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    public String printClasses() {
        String[] classes = Classes.courses;
        String strClasses = classes[arrMatch.get(0)] + '\n' + classes[arrMatch.get(1)] +
                '\n' + classes[arrMatch.get(2)];
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
                matchCount += 60;
                break;
            case "language":
                matchCount += 30;
                break;

        }

    }


}

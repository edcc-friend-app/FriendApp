package edcc.friendfinder;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public class User implements Comparable<User> {
    private String firstName;
    private String lastName;
    //private String edmail;
    private String major;
    private String bio;
    private ArrayList<User> friends;
    private ArrayList<Course> classes;
    private int age;
    private int id;
    //added from Linda's code
    static boolean listType;
    private String photo; //base64 encoded byte array
    //tests for match
    private int matchCount;

    public User() {}

    public User(String firstName, String lastName, String major,
                String bio, ArrayList<User> friends, ArrayList<Course> classes, int id, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
//        this.edmail = edmail;
        this.major = major;
        this.bio = bio;
        this.friends = friends;
        this.classes = classes;
        this.id = id;
        this.age = age;
        matchCount = 0;
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

//    public String getEdmail() {
//        return edmail;
//    }
//
//    public void setEdmail(String edmail) {
//        this.edmail = edmail;
//    }

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

    public String printClasses() {
        String strClasses = "";
        for (Course cs: classes) {
            strClasses += cs + "\n";
        }
        return strClasses;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return age == user.age &&
                id == user.id &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(major, user.major) &&
                Objects.equals(bio, user.bio) &&
                Objects.equals(friends, user.friends) &&
                Objects.equals(photo, user.photo);
    }

    @Override
    public int hashCode() {

        return Objects.hash(firstName, lastName, major, bio, friends, age, id, photo);
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
            case "interest":
                matchCount += 20;
                break;
            case "age":
                matchCount += 5;
                break;

        }

    }


}

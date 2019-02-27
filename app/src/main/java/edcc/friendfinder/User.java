package edcc.friendfinder;

import java.util.ArrayList;
import java.util.Objects;

public class User implements Comparable<User>{
    private String firstName;
    private String lastName;
    //private String edmail;
    //private String phoneNumber;
    private String major;
    private String interests;
    private ArrayList<User> friends;
    private int age;
    private int id;
    //added from Linda's code
    static boolean listType;
    private String photo; //base64 encoded byte array

    public User() {}

    public User(String firstName, String lastName, String major,
                String interests, ArrayList<User> friends, int id, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
//        this.edmail = edmail;
//        this.phoneNumber = phoneNumber;
        this.major = major;
        this.interests = interests;
        this.friends = friends;
        this.id = id;
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
//
//    public String getPhoneNumber() {
//        return phoneNumber;
//    }
//
//    public void setPhoneNumber(String phoneNumber) {
//        this.phoneNumber = phoneNumber;
//    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
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
                Objects.equals(interests, user.interests) &&
                Objects.equals(friends, user.friends) &&
                Objects.equals(photo, user.photo);
    }

    @Override
    public int hashCode() {

        return Objects.hash(firstName, lastName, major, interests, friends, age, id, photo);
    }

    @Override
    public int compareTo(User another) {
        String thisName = lastName.toLowerCase() + firstName.toLowerCase();
        String anotherName = another.lastName.toLowerCase() + another.firstName.toLowerCase();
        return thisName.compareTo(anotherName);
    }

}

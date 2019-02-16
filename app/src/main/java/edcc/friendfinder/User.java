package edcc.friendfinder;

import java.util.ArrayList;
import java.util.Objects;

public class User implements Comparable<User>{
    private String firstName;
    private String lastName;
    private String edmail;
    private String phoneNumber;
    private String major;
    private String interests;
    private ArrayList<User> friends;
    private String dateOfBirth;
    private int id;

    public User(String firstName, String lastName, String edmail, String phoneNumber, String major,
                String interests, ArrayList<User> friends, String dateOfBirth, int id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.edmail = edmail;
        this.phoneNumber = phoneNumber;
        this.major = major;
        this.interests = interests;
        this.friends = friends;
        this.dateOfBirth = dateOfBirth;
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

    public String getEdmail() {
        return edmail;
    }

    public void setEdmail(String edmail) {
        this.edmail = edmail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User profile = (User) o;
        return dateOfBirth == profile.dateOfBirth &&
                Objects.equals(firstName, profile.firstName) &&
                Objects.equals(lastName, profile.lastName) &&
                Objects.equals(edmail, profile.edmail) &&
                Objects.equals(phoneNumber, profile.phoneNumber) &&
                Objects.equals(major, profile.major) &&
                Objects.equals(interests, profile.interests) &&
                Objects.equals(friends, profile.friends);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, edmail, phoneNumber, major, interests, friends);
    }

    @Override
    public int compareTo(User another) {
        String thisName = lastName.toLowerCase() + firstName.toLowerCase();
        String anotherName = another.lastName.toLowerCase() + another.firstName.toLowerCase();
        return thisName.compareTo(anotherName);
    }

}

package edcc.friendfinder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anthony Luong
 * @author Estefano Felipa
 * @author Jonathan Young
 * @version 1.0 3/10/19
 */
public class User implements Comparable<User> {
    private String firstName;
    private String lastName;
    private String major;
    private String bio;
    private String language;
    private String availability;
    private int id = -1;
    private String photo; //base64 encoded byte array
    //tests for match
    private int matchCount;
    private List<Integer> arrMatch = new ArrayList<>();

    public User() {
        firstName = "";
        lastName = "";
        major = "";
        bio = "";
        language = "";
        matchCount = 0;
    }

    public User(String firstName, String lastName, String major,
                String bio, String language, String availability) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.major = major;
        this.bio = bio;
        this.language = language;
        this.availability = availability;
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

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
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

    public List<Integer> getArrMatch() {
        return arrMatch;
    }

    public void setArrMatch(List<Integer> arrMatch) {
        this.arrMatch = arrMatch;
    }

    public int getMatchCount() {
        return matchCount;
    }

    public void setMatchCount(int matchCount) {
        this.matchCount = matchCount;
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

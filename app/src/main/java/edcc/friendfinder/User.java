package edcc.friendfinder;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class represents one user.
 *
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
    private List<Integer> arrMatch;

    /**
     * Default constructor.
     */
    public User() {
        firstName = "";
        lastName = "";
        major = "";
        bio = "";
        language = "";
        availability = "";
        arrMatch = new ArrayList<>();
        matchCount = 0;
    }

    /**
     * Full constructor.
     *
     * @param firstName    first name of the user
     * @param lastName     last name of the user
     * @param major        the user's major
     * @param bio          the user's brief description
     * @param language     the user's language preference
     * @param availability the user's availability
     */
    public User(String firstName, String lastName, String major,
                String bio, String language, String availability, List<Integer> classes) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.major = major;
        this.bio = bio;
        this.language = language;
        this.availability = availability;
        arrMatch = classes;
        matchCount = 0;
    }

    /**
     * Provides access to the user's first name.
     *
     * @return the user's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Allows the user's first name to be changed.
     *
     * @param firstName the user's first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Provides access to the user's last name.
     *
     * @return the user's last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Allows the user's last name to be changed.
     *
     * @param lastName the user's last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Provides access to the user's availability
     *
     * @return the user's availability
     */
    public String getAvailability() {
        return availability;
    }

    /**
     * Allows the user's availability to be changed.
     *
     * @param availability the user's availability
     */
    public void setAvailability(String availability) {
        this.availability = availability;
    }

    /**
     * Provides access to the user's major
     *
     * @return the user's major
     */
    public String getMajor() {
        return major;
    }

    /**
     * Allows the user's major to be changed.
     *
     * @param major the user's major
     */
    public void setMajor(String major) {
        this.major = major;
    }

    /**
     * Provides access to the user's bio
     *
     * @return the user's bio
     */
    public String getBio() {
        return bio;
    }

    /**
     * Allows the user's bio to be changed
     *
     * @param bio the user's bio
     */
    public void setBio(String bio) {
        this.bio = bio;
    }

    /**
     * Provides access to the user's ID from Firebase
     *
     * @return the user's Firebase ID
     */
    public int getId() {
        return id;
    }

    /**
     * Allows the user's Firebase ID to be changed
     *
     * @param id the user's Firebase ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Provides access to the user's language preference
     *
     * @return the user's language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Allows the user's language preference to be changed
     *
     * @param language the user's language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Provides access to the user's profile picture
     *
     * @return the user's profile picture
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * Allows the user's profile picture to be changed
     *
     * @param photo the user's profile picture
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * Provides access to the user's list of information from their profile
     *
     * @return the user's profile information as an array
     */
    public List<Integer> getArrMatch() {
        return arrMatch;
    }

    /**
     * Allows the user's list of information to be changed
     *
     * @param arrMatch the user's profile information as an array
     */
    public void setArrMatch(List<Integer> arrMatch) {
        this.arrMatch = arrMatch;
    }

    /**
     * Provides access to the user's match counter
     *
     * @return the user's number of matches
     */
    public int getMatchCount() {
        return matchCount;
    }

    /**
     * Allows the user's match counter to be changed
     *
     * @param matchCount the user's number of matches
     */
    public void setMatchCount(int matchCount) {
        this.matchCount = matchCount;
    }

    @Override
    public String toString() {
        NumberFormat numFormat = NumberFormat.getPercentInstance();
        numFormat.setMinimumFractionDigits(1);
        double percentage = matchCount / 260.0;
        return numFormat.format(percentage) + " " + firstName + " " + lastName;
    }

    public String printName() {
        return firstName + " " + lastName;
    }

    /**
     * Compares one user to another for sorting purposes. Uses the String representation of
     * each user for comparison.
     *
     * @param other the other user to compare to this one
     * @return the user's String value, negative if lower, positive if greater, and zero if the same
     */
    @Override
    public int compareTo(User other) {
        return  other.matchCount - this.matchCount ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return matchCount == user.matchCount &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(major, user.major) &&
                Objects.equals(bio, user.bio) &&
                Objects.equals(language, user.language) &&
                Objects.equals(availability, user.availability) &&
                Objects.equals(arrMatch, user.arrMatch);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, major, bio, language, availability, id, photo, matchCount, arrMatch);
    }

    /**
     * Compares one user to another to see their compatibility.
     *
     * @param other the other user to compare compatibility values
     * @return places current user before the other if value is positive,
     * else the other way around if the value is negative
     */
    public int matchCompare(User other) {
        return this.matchCount - other.matchCount;
    }

    /**
     * Increments compatibility values depending on what they match on
     *
     * @param type user profile information to be compared
     */
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

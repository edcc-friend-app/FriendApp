package edcc.friendfinder;

public class Users {

    private String first_name, last_name, language, major, bio, availability, profile_image, uid;

    public Users() {

    }

    public Users(String first_name, String last_name, String language, String major, String bio, String availability, String profile_image, String uid) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.language = language;
        this.major = major;
        this.bio = bio;
        this.availability = availability;
        this.profile_image = profile_image;
        this.uid = uid;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
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

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}

package edcc.friendfinder;

import java.util.ArrayList;
import java.util.List;

/**
 * Pseudo users/friends within the app
 * @author Anthony Luong
 * @author Estefano Felipa
 * @author Jonathan Young
 * @version 1.0 3/10/19
 */
public class DataHandler {

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
    private ArrayList<User> potFriends;
    private List<String> courses;
    private List<String> languages;
    private List<String> majors;

    public DataHandler() {
        potFriends = new ArrayList<>();

        List<Integer> classes1 = new ArrayList<>();
        classes1.add(0);
        classes1.add(1);
        classes1.add(2);
        friend1 = new User("Franshesco", "Coello", "Computer Science", "Soccer", "English",
                "All day", classes1);

        List<Integer> classes2 = new ArrayList<>();
        classes2.add(3);
        classes2.add(12);
        classes2.add(21);
        friend2 = new User("Anthony", "Luong", "Computer Science", "Memes", "Vietnamese", "All day", classes2);

        List<Integer> classes3 = new ArrayList<>();
        classes3.add(8);
        classes3.add(5);
        classes3.add(24);
        friend3 = new User("Jonathan", "Young", "Business", "Football", "Chinese", "All day", classes3);

        List<Integer> classes4 = new ArrayList<>();
        classes4.add(13);
        classes4.add(4);
        classes4.add(19);
        friend4 = new User("Susy", "Lincoln", "Business", "Dancing", "Spanish", "All day", classes4);

        List<Integer> classes5 = new ArrayList<>();
        classes5.add(23);
        classes5.add(1);
        classes5.add(16);
        friend5 = new User("Jared", "Kuddes", "Nursing", "Memes", "French", "All day", classes5);

        List<Integer> classes6 = new ArrayList<>();
        classes6.add(3);
        classes6.add(21);
        classes6.add(6);
        friend6 = new User("Sora", "Fung", "Biology", "Dancing", "Chinese", "All day", classes6);

        List<Integer> classes7 = new ArrayList<>();
        classes7.add(10);
        classes7.add(20);
        classes7.add(5);
        friend7 = new User("Roxas", "Pham", "Accounting", "Guitar", "Vietnamese", "All day", classes7);

        List<Integer> classes8 = new ArrayList<>();
        classes8.add(2);
        classes8.add(3);
        classes8.add(5);
        friend8 = new User("Vivian", "Nguyen", "Biology", "Swimming", "Vietnamese", "All day", classes8);

        List<Integer> classes9 = new ArrayList<>();
        classes9.add(3);
        classes9.add(15);
        classes9.add(22);
        friend9 = new User("Henry", "Zokkins", "Music", "Tennis", "English", "All day", classes9);

        List<Integer> classes10 = new ArrayList<>();
        classes10.add(0);
        classes10.add(1);
        classes10.add(2);
        friend10 = new User("Vy", "Bui", "Biology", "Swimming", "Chinese", "All day", classes10);

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
        //Initialize courses
        courses = new ArrayList<>();
        courses.add("ACCT& 201");
        courses.add("ACCT& 202");
        courses.add("ACCT& 203");
        courses.add("ASL& 121");
        courses.add("ASL& 122");
        courses.add("ASL& 123");
        courses.add("ANTH 201");
        courses.add("ANTH 202");
        courses.add("ANTH 203");
        courses.add("ANTH 201");
        courses.add("ARAB 121");
        courses.add("ARAB 122");
        courses.add("ARAB 123");
        courses.add("ART 101");
        courses.add("ART 102");
        courses.add("ART 103");
        courses.add("BIOL& 211");
        courses.add("BIOL& 212");
        courses.add("BIOL& 213");
        courses.add("CHEM& 141");
        courses.add("CHEM& 142");
        courses.add("CHEM& 143");
        courses.add("CHEM& 241");
        courses.add("CHEM& 242");
        courses.add("CHEM& 243");
        courses.add("CHIN& 121");
        courses.add("CHIN& 122");
        courses.add("CHIN& 123");
        //Initialize languages
        languages = new ArrayList<>();
        languages.add("Arabic");
        languages.add("Chinese (Cantonese)");
        languages.add("Chinese (Mandarin)");
        languages.add("English");
        languages.add("French");
        languages.add("German");
        languages.add("Indonesian (Malay)");
        languages.add("Hindustani");
        languages.add("Japanese");
        languages.add("Korean");
        languages.add("Russian");
        languages.add("Spanish");
        languages.add("Vietnamese");
        //Initialize majors
        majors = new ArrayList<>();
        majors.add("Accounting");
        majors.add("Allied Health Education");
        majors.add("Biology");
        majors.add("Business");
        majors.add("Business Information Technology");
        majors.add("Business Management");
        majors.add("Business Training Center");
        majors.add("Child, Youth, and Family Studies");
        majors.add("Computer Information Systems");
        majors.add("Computer Science");
        majors.add("Construction Management");
        majors.add("Culinary Arts");
        majors.add("Early Childhood Education");
        majors.add("Emergency Management");
        majors.add("Engineering and Science");
        majors.add("Engineering Technology");
        majors.add("Event Planning");
        majors.add("Family Support Studies");
        majors.add("General Studies");
        majors.add("Horticulture");
        majors.add("Hospitality and Tourism");
        majors.add("Music");
        majors.add("Nursing");
        majors.add("Occupational Safety and Health");
        majors.add("Paralegal");
        majors.add("Social and Human Services");
        majors.add("Transfer (General)");
        majors.add("Visual Communications");
    }

    public ArrayList<User> getPotFriends() {
        return potFriends;
    }

    public List<String> getCourses() {
        return courses;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public List<String> getMajors() {
        return majors;
    }
}

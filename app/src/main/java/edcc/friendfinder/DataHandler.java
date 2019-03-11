package edcc.friendfinder;

import java.util.ArrayList;
import java.util.List;

public class DataHandler {

    private ArrayList<User> potFriends;
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

    public DataHandler() {
        potFriends = new ArrayList<>();

        friend1 = new User("Franshesco", "Coello", "Computer Science", "Soccer", "English",
                "All day");
        List<Integer> classes1 = new ArrayList<>();
        classes1.add(0);
        classes1.add(1);
        classes1.add(2);
        friend1.setArrMatch(classes1);

        friend2 = new User("Anthony", "Luong", "Computer Science", "Memes", "Vietnamese", "All day");
        List<Integer> classes2 = new ArrayList<>();
        classes2.add(3);
        classes2.add(12);
        classes2.add(21);
        friend2.setArrMatch(classes2);

        friend3 = new User("Jonathan", "Young", "Business", "Football", "Chinese", "All day");
        List<Integer> classes3 = new ArrayList<>();
        classes3.add(8);
        classes3.add(5);
        classes3.add(24);
        friend3.setArrMatch(classes3);

        friend4 = new User("Susy", "Lincoln", "Business", "Dancing", "Spanish", "All day");
        List<Integer> classes4 = new ArrayList<>();
        classes4.add(13);
        classes4.add(4);
        classes4.add(19);
        friend4.setArrMatch(classes4);

        friend5 = new User("Jared", "Kuddes", "Nursing", "Memes", "French", "All day");
        List<Integer> classes5 = new ArrayList<>();
        classes5.add(23);
        classes5.add(1);
        classes5.add(16);
        friend5.setArrMatch(classes5);

        friend6 = new User("Sora", "Fung", "Biology", "Dancing", "Chinese", "All day");
        List<Integer> classes6 = new ArrayList<>();
        classes6.add(3);
        classes6.add(21);
        classes6.add(6);
        friend6.setArrMatch(classes6);

        friend7 = new User("Roxas", "Pham", "Accounting", "Guitar", "Vietnamese", "All day");
        List<Integer> classes7 = new ArrayList<>();
        classes7.add(10);
        classes7.add(20);
        classes7.add(5);
        friend7.setArrMatch(classes7);

        friend8 = new User("Vivian", "Nguyen", "Biology", "Swimming", "Vietnamese", "All day");
        List<Integer> classes8 = new ArrayList<>();
        classes8.add(2);
        classes8.add(3);
        classes8.add(5);
        friend8.setArrMatch(classes8);

        friend9 = new User("Henry", "Zokkins", "Music", "Tennis", "English", "All day");
        List<Integer> classes9 = new ArrayList<>();
        classes9.add(3);
        classes9.add(15);
        classes9.add(22);
        friend9.setArrMatch(classes9);

        friend10 = new User("Vy", "Bui", "Biology", "Swimming", "Chinese", "All day");
        List<Integer> classes10 = new ArrayList<>();
        classes10.add(0);
        classes10.add(1);
        classes10.add(2);
        friend10.setArrMatch(classes10);

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
    }

    public ArrayList<User> getPotFriends() {
        return potFriends;
    }

    public boolean addPotFriends(ArrayList<User> Users) {
        potFriends = Users;
        return true;
    }
}

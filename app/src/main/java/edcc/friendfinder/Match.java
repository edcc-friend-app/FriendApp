package edcc.friendfinder;

import java.util.ArrayList;

public class Match {
    private int count;
    private User thisUser;
    private ArrayList<User> friends;

    public Match(User thisUser, ArrayList<User> friends) {
        this.thisUser = thisUser;
        this.friends = friends;
        count = 0;
    }

    public ArrayList<User> getPotFriends() {
        //friends;
        sort();
        return friends;
    }

    public void compareMajor() {
        for (User f: friends) {
            if(thisUser.getMajor().equalsIgnoreCase(f.getMajor())) {
                f.incrementCount("major");
            }
        }
    }

    public void compareClasses() {
//        for (User f: friends) {
//            if(thisUser.getMajor().equalsIgnoreCase(f.getMajor())) {
//                f.incrementCount("major");
//            }
//        }
    }

    public void compareBio() {
//        for (User f: friends) {
//            if(thisUser.getMajor().equalsIgnoreCase(f.getMajor())) {
//                f.incrementCount("major");
//            }
//        }
    }
    public void compareAge() {
        for (User f: friends) {
            if(thisUser.getAge() == f.getAge()) {
                f.incrementCount("age");
            }
        }
    }

    public ArrayList<User> getFriends() {
        return friends;
    }

    //bubbleSort from cs142
    public void sort() {
        boolean swapped;
        for (int i = 0; i < friends.size(); i++) {
            swapped = false;
            for (int j = 1; j < friends.size() - i; j++) {
                if (friends.get(j).matchCompare(friends.get(j - 1)) <= 0) {
                    User temp = friends.get(j);
                    friends.set(j, friends.get(j - 1));
                    friends.set(j - 1, temp);
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
        }
    }

}

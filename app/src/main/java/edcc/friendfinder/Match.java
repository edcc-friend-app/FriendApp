package edcc.friendfinder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Match {
    private User thisUser;
    private List<User> friends;

    public Match(User thisUser, List<User> friends) {
        this.thisUser = thisUser;
        this.friends = friends;
        for (User f: friends) {
            f.setMatchCount(0);
        }
    }

    public List<User> getPotFriends() {
        //friends;
        compareMajor();
        compareClasses();
        compareLanguage();
        sort();
        return friends;
    }

    public void compareMajor() {
        for (User f : friends) {
            if (thisUser.getMajor().equalsIgnoreCase(f.getMajor())) {
                f.incrementCount("major");
            }
        }
    }

    public void compareClasses() {
        for (User f : friends) {
            if (compareClassesHelper(thisUser.getArrMatch(), f.getArrMatch())) {
                f.incrementCount("classes");
            }
        }
    }

    public boolean compareClassesHelper(List<Integer> arr1, List<Integer> arr2) {
        Collections.sort(arr1);
        Collections.sort(arr2);
//        for (Integer i: arr1) {
//            if(i == arr2)
//        }
        for (int i = 0; i < arr1.size(); i++) {
            if (arr1.get(i) == arr2.get(i)) {
                return true;
            }
        }
        return false;
    }

    public void compareLanguage() {
        for (User f : friends) {
            if (thisUser.getLanguage().equalsIgnoreCase(f.getLanguage())) {
                f.incrementCount("language");
            }
        }
    }

    public List<User> getFriends() {
        return friends;
    }

    //bubbleSort from cs142
    public void sort() {
        boolean swapped;
        for (int i = 0; i < friends.size(); i++) {
            swapped = false;
            for (int j = 1; j < friends.size() - i; j++) {
                if (friends.get(j).matchCompare(friends.get(j - 1))> 0) {
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

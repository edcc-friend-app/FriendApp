package edcc.friendfinder;

import java.util.Collections;
import java.util.List;

/**
 * Class to handle other users and categorize likelihood of networking with them
 *
 * @author Anthony Luong
 * @author Estefano Felipa
 * @author Jonathan Young
 * @version 1.0 3/10/19
 */
public class Match {

    //fields
    private User thisUser;
    private List<User> friends;

    /**
     * Complete constructor.
     *
     * @param thisUser current user
     * @param friends  potential friends
     */
    public Match(User thisUser, List<User> friends) {
        this.thisUser = thisUser;
        this.friends = friends;
        for (User f : friends) {
            f.setMatchCount(0);
        }
    }

    /**
     * Getter for the other user's information.
     *
     * @return other user's information
     */
    public List<User> getPotFriends() {
        //friends;
        compareMajor();
        compareClasses();
        compareLanguage();
        sort();
        return friends;
    }

    /**
     * Compares whether or not the majors are the same between users
     */
    public void compareMajor() {
        for (User f : friends) {
            if (thisUser.getMajor().equalsIgnoreCase(f.getMajor())) {
                f.incrementCount("major");
            }
        }
    }

    /**
     * Compares whether or not the classes are the same between users
     */
    public void compareClasses() {
        for (User f : friends) {
            if (compareClassesHelper(thisUser.getArrMatch(), f.getArrMatch())) {
                f.incrementCount("classes");
            }
        }
    }

    /**
     * Compares whether or not the majors are the same between users amongst the other two fields
     *
     * @param arr1 one of the other spinner options to be compared
     * @param arr2 another one of the other spinner options to be compared
     * @return true or false depending if they are the same selected classes
     */
    public boolean compareClassesHelper(List<Integer> arr1, List<Integer> arr2) {
        Collections.sort(arr1);
        Collections.sort(arr2);

        for (int i = 0; i < arr1.size(); i++) {
            if (arr1.get(i) == arr2.get(i)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Compares whether or not the language preferences are the same between users
     */
    public void compareLanguage() {
        for (User f : friends) {
            if (thisUser.getLanguage().equalsIgnoreCase(f.getLanguage())) {
                f.incrementCount("language");
            }
        }
    }

    /**
     * Sorting method to organize the list of potential friends based on their strength in likelihood
     * of being your friend from the algorithm.
     */
    public void sort() {
        boolean swapped;
        for (int i = 0; i < friends.size(); i++) {
            swapped = false;
            for (int j = 1; j < friends.size() - i; j++) {
                if (friends.get(j).matchCompare(friends.get(j - 1)) > 0) {
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

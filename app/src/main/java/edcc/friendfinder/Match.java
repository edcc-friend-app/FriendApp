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
class Match {

    //fields
    private final User thisUser;
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
        Collections.sort(friends);
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
            compareClassesHelper(thisUser.getArrMatch(), f.getArrMatch(), f);
        }
    }


    /**
     * Compares whether or not the majors are the same between users amongst the other two fields
     *
     * @param arr1 one of the other spinner options to be compared
     * @param arr2 another one of the other spinner options to be compared
     */
    private void compareClassesHelper(List<Integer> arr1, List<Integer> arr2, User f) {
        Collections.sort(arr1);
        Collections.sort(arr2);
        for (int a : arr1) {
            for (int b : arr2) {
                if (a == b) {
                    f.incrementCount("class");
                }
            }
        }
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

}

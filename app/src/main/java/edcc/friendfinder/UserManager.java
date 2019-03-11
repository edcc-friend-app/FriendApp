package edcc.friendfinder;

import android.content.Context;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manage the list of user objects.
 *
 * @author Anthony Luong
 * @author Estefano Felipa
 * @author Jonathan Young
 * @version 1.0 3/10/19
 */
public class UserManager {

    private static UserManager um;
    private static String userId;
    private List<User> friendList;
    private int nextFriendId;
    private List<User> userList;
    private DataHandler dh;
    private User thisUser;
    private PreferencesManager pm;
    private Match match;
    private List<String> courses;
    private List<String> languages;
    private List<String> majors;

/**
 *Private constructor.
 */
    private UserManager(Context ctx) {
        dh = new DataHandler();
        pm = PreferencesManager.getInstance(ctx);
        friendList = new ArrayList<>();
        userList = new ArrayList<>();
        thisUser = new User();
        courses = dh.getCourses();
        languages = dh.getLanguages();
        majors = dh.getMajors();
    }


    /**
     * Singleton implementation - returns the single instance of
     * the UserManager class.
     */
    public static UserManager getUserManager(Context ctx, String userId) {
        UserManager.userId = userId;
        if (um == null) {
            um = new UserManager(ctx);
        }
        return um;
    }

    /**
     * Provides access to a sorted list of all users.
     * Sorts in reverse if user preferences require it.
     * @param id - these users name
     * @return List<User> - the list of users
     */
    User getUser(String id) {
        //search for id
        int index = -1;
        for (int i = 0; i < userList.size(); i++) {
            User u = userList.get(i);
            if (u.toString().equalsIgnoreCase(id)) {
                index = i;
                break;
            }
        }
        //if found
        if (index >= 0) {
            return userList.get(index);
        } else {
            return null;
        }
    }

    /**
     * Replaces the user list when Firebase has pushed an update to
     * an activity. Does not require updating the DB since it came from the DB.
     *
     * @param user the new list of User objects
     */
    public void setUser(User user) {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getId() == user.getId()) {
                userList.set(i, user);
            }
        }
    }

    /**
     * Gets a list of potential friends, placing higher matches as priority on this top of the list.
     * @return list of User objects
     */
    public List<User> getPotentialFriends() {
        userList = dh.getPotFriends();
        match = new Match(thisUser, userList);
        return match.getPotFriends();
    }

    /**
     * Gets current friend list
     * @return list of User objects that are added by you
     */
    List<User> getFriendList() {
        Collections.sort(friendList);
        return friendList;
    }

    /**
     * Replaces a user object in the friend list when Firebase has pushed an update to
     * an activity. Does not require updating the DB since it came from the DB.
     *
     * @param list the user object list with which to replace the existing friends list
     */
    void setFriendList(List<User> list) {
        Collections.sort(list);
        friendList = list;
        if (friendList.size() > 0) {
            nextFriendId = friendList.get(0).getId();
            for (User u : friendList) {
                if (u.getId() > nextFriendId) {
                    nextFriendId = u.getId();
                }
            }
            nextFriendId++;
        }
    }

    /**
     * Getter for the user that is added
     * @param id the user's name
     * @return the user object
     */
    User getFriend(int id) {
        //search for id
        int index = -1;
        for (int i = 0; i < friendList.size(); i++) {
            User f = friendList.get(i);
            if (f.getId() == id) {
                index = i;
                break;
            }
        }
        //if found
        if (index >= 0) {
            return friendList.get(index);
        } else {
            return null;
        }
    }

    /**
     * Places the newly added user into the official friends list.
     * @param friend user object that is placed within the new friend list
     */
    public void setFriend(User friend) {
        for (int i = 0; i < friendList.size(); i++) {
            if (friendList.get(i).getId() == friend.getId()) {
                friendList.set(i, friend);
            }
        }
    }

    /**
     * Method to execute the add function
     * @param newFriend user object that is selected to add
     * @return user object into the friend list
     */
    int addFriend(User newFriend) {
        if (newFriend == null) {
            return -1;
        }
        newFriend.setId(nextFriendId);
        friendList.add(newFriend);
        nextFriendId++;
        Collections.sort(friendList);
        if (!pm.isSortAZ()) {
            Collections.reverse(friendList);
        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(userId).collection("friends")
                .document(String.valueOf(newFriend.getId())).set(newFriend);
        userList.remove(newFriend);
        return newFriend.getId();
    }

    /**
     * Deletes the current user object from your friends list
     * @param id the user's name that's selected
     */
    void deleteFriend(int id) {
        int index = -1;
        //find friend in list
        for (int i = 0; i < friendList.size(); i++) {
            User f = friendList.get(i);
            if (f.getId() == id) {
                index = i;
                break;
            }
        }
        //if found
        if (index >= 0) {
            //delete
            User removedFriend = friendList.remove(index);
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("users").document(userId).collection("friends")
                    .document(String.valueOf(id)).delete();
            userList.add(removedFriend);
        }
    }

    /**
     * Getter for the selected user object.
     * @return user object
     */
    public User getThisUser() {
        return thisUser;
    }

    /**
     * Setter for the selected user object.
     * @param thisUser the user object selected
     */
    public void setThisUser(User thisUser) {
        this.thisUser = thisUser;
        thisUser.setId(0);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(userId).collection("profile")
                .document(String.valueOf(thisUser.getId())).set(thisUser);
    }

    /**
     * Getter for the course list
     * @return list of course objects
     */
    public List<String> getCourses() {
        return courses;
    }


    /**
     * Getter for the language list
     * @return list of language objects
     */
    public List<String> getLanguages() {
        return languages;
    }


    /**
     * Getter for the majors list
     * @return list of major objects
     */
    public List<String> getMajors() {
        return majors;
    }

}


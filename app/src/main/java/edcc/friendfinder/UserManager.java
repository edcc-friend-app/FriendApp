package edcc.friendfinder;

import android.content.Context;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
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
    private int nextUserId;
    private DataHandler dh;
    private User thisUser;
    private PreferencesManager pm;
    private Match match;
    private List<String> courses;
    private List<String> languages;
    private List<String> majors;

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


    public static UserManager getUserManager(Context ctx, String userId) {
        UserManager.userId = userId;
        if (um == null) {
            um = new UserManager(ctx);
        }
        return um;
    }

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

    public void setUser(User user) {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getId() == user.getId()) {
                userList.set(i, user);
            }
        }
    }

    public List<User> getPotentialFriends() {
        userList = dh.getPotFriends();
        match = new Match(thisUser, userList);
        return match.getPotFriends();
    }

    List<User> getFriendList() {
        Collections.sort(friendList);
        return friendList;
    }

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

    public void setFriend(User friend) {
        for (int i = 0; i < friendList.size(); i++) {
            if (friendList.get(i).getId() == friend.getId()) {
                friendList.set(i, friend);
            }
        }
    }

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

    public User getThisUser() {
        return thisUser;
    }

    public void setThisUser(User thisUser) {
        this.thisUser = thisUser;
        thisUser.setId(0);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(userId).collection("profile")
                .document(String.valueOf(thisUser.getId())).set(thisUser);
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


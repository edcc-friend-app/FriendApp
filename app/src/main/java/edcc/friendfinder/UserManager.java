package edcc.friendfinder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.content.Context;

import com.google.firebase.firestore.FirebaseFirestore;

public class UserManager {

    private static UserManager um;
    private List<User> friendList;
    private int nextFriendId;
    private List<User> userList;
    private int nextUserId;
    private DataHandler dh;
    private User thisUser;
    private PreferencesManager pm;
    private static String userId;
    private ArrayList<Course> classes;
    private int[] currClasses;
    private Match match;

    private UserManager(Context ctx) {
        dh = new DataHandler();
        friendList = dh.getFriends();
        userList = dh.getPotFriends();
        classes = new ArrayList<>();
        classes.add(new Course("CS 240", "Linda Zuvich"));
        classes.add(new Course("MATH 272", "Wayne Neidhardt"));
        classes.add(new Course("PHYS 222", "Tom Flemming"));
        thisUser = new User("Estefano", "Felipa", "CS", "Soccer",
                (ArrayList)friendList, classes, 958024838, "Spanish");
        currClasses = new int[25];
        currClasses[0] = 1;
        currClasses[6] = 1;
        currClasses[15] = 1;
        thisUser.setArrMatch(currClasses);
        match = new Match(thisUser, userList);
    }


    public static UserManager getUserManager(Context ctx, String userId) {
        UserManager.userId = userId;
        if (um == null) {
            um = new UserManager(ctx);
        }
        return um;
    }

    List<User> getUserList() {
        Collections.sort(userList);
        if (!pm.isSortAZ()) {
            Collections.reverse(userList);
        }
        return userList;
    }

    void setUserList(List<User> list) {
        Collections.sort(list);
        userList = list;
        if (userList.size() > 0) {
            nextUserId = userList.get(0).getId();
            for (User u : userList) {
                if (u.getId() > nextUserId) {
                    nextUserId = u.getId();
                }
            }
            nextUserId++;
        }
    }

    User getUser(int id) {
        //search for id
        int index = -1;
        for (int i = 0; i < userList.size(); i++) {
            User u = userList.get(i);
            if (u.getId() == id) {
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
        return match.getPotFriends();
    }

    void replaceUser(User user) {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getId() == user.getId()) {
                userList.set(i, user);
            }
        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(userId).collection("pets")
                .document(String.valueOf(user.getId())).set(user);
    }

    void addUser(User newUser) {
        if (newUser == null) {
            return;
        }
        newUser.setId(nextUserId);
        userList.add(newUser);
        nextUserId++;
        Collections.sort(userList);
        if (!pm.isSortAZ()) {
            Collections.reverse(userList);
        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(userId).collection("pets")
                .document(String.valueOf(newUser.getId())).set(newUser);
    }

    void deleteUser(int id) {
        int index = -1;
        //find pet in list
        for (int i = 0; i < userList.size(); i++) {
            User u = userList.get(i);
            if (u.getId() == id) {
                index = i;
                break;
            }
        }
        //if found
        if (index >= 0) {
            //delete
            userList.remove(index);
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("users").document(userId).collection("pets")
                    .document(String.valueOf(id)).delete();
        }
    }

    boolean UserHasFriends(int friendId) {
        int index = -1;
        for (int i = 0; i < userList.size() && index < 0; i++) {
            if (userList.get(i).getFriendId() == friendId) {
                index = i;
            }
        }
        return index > -1;
    }

    boolean friendHasFriends(int friendId) {
        int index = -1;
        for (int i = 0; i < friendList.size() && index < 0; i++) {
            if (friendList.get(i).getFriendId() == friendId) {
                index = i;
            }
        }
        return index > -1;
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

    void replaceFriend(User friend) {
        for (int i = 0; i < friendList.size(); i++) {
            if (friendList.get(i).getId() == friend.getId()) {
                friendList.set(i, friend);
            }
        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(userId).collection("vets")
                .document(String.valueOf(friend.getId())).set(friend);

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
        db.collection("users").document(userId).collection("vets")
                .document(String.valueOf(newFriend.getId())).set(newFriend);

        return newFriend.getId();
    }

    void deleteFriend(int id) {
        int index = -1;
        //find vet in list
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
            friendList.remove(index);
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("users").document(userId).collection("vets")
                    .document(String.valueOf(id)).delete();
        }
    }
    public User getThisUser() {
        return thisUser;
    }

    public void setThisUser(User thisUser) {
        this.thisUser = thisUser;
    }
}


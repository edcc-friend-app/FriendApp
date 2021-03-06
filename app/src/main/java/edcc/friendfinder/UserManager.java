package edcc.friendfinder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Manage the list of user objects.
 *
 * @author Anthony Luong
 * @author Estefano Felipa
 * @author Jonathan Young
 * @version 1.0 3/10/19
 */
@SuppressWarnings("unused")
public class UserManager {

    //fields
    private static UserManager um;
    private static String userId;
    private static Random rand = new Random();
    private static int myRand = rand.nextInt();
    private List<User> friendList;
    private int nextFriendId;
    private int nextUserId;
    private List<User> userList;
    private DataHandler dh;
    private User thisUser;
    private final List<String> courses;
    private final List<String> languages;
    private final List<String> majors;

    private DatabaseReference usersRef;
    private FirebaseAuth mAuth;
    private String currentUserID;


    /**
     * Private constructor.
     */
    private UserManager(Context ctx) {
        dh = new DataHandler();
        friendList = new ArrayList<>();
        userList = new ArrayList<>();
        thisUser = new User();
        courses = dh.getCourses();
        languages = dh.getLanguages();
        majors = dh.getMajors();

        mAuth = FirebaseAuth.getInstance();
//        currentUserID = mAuth.getCurrentUser().getUid();
        usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
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
     *
     * @param id - these users name
     * @return List<User> - the list of users
     */
    User getUser(String id) {
        //search for id
        int index = -1;
        for (int i = 0; i < userList.size(); i++) {
            User u = userList.get(i);
            if (u.printName().equalsIgnoreCase(id)) {
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
     * Gets a list of potential friends, placing higher matches as priority on this top of the list.
     *
     * @return list of User objects
     */
    public List<User> getPotentialFriends() {
        userList = dh.getPotFriends();
        List<User> delete = new ArrayList<>();
        List<User> friends = getFriendList();
        for (User e : userList) {
            for (User f : friends) {
                if (e.equals(f)) {
                    delete.add(e);
                }
            }
        }
        userList.removeAll(delete);
        Match match = new Match(thisUser, userList);
        return match.getPotFriends();
    }

    /**
     * Gets current friend list
     *
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
     *
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
     *
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
     *
     * @param newFriend user object that is selected to add
     */
    void addFriend(User newFriend) {
        if (newFriend == null) {
            return;
        }
        newFriend.setId(nextFriendId);
        friendList.add(newFriend);
        nextFriendId++;
        Collections.sort(friendList);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(userId).collection("friends")
                .document(String.valueOf(newFriend.getId())).set(newFriend);
        userList.remove(newFriend);
    }

    /**
     * Deletes the current user object from your friends list
     *
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
     *
     * @return user object
     */
    public User getThisUser() {
        return thisUser;
    }

    /**
     * Setter for the selected user object.
     *
     * @param thisUser the user object selected
     */
    public void setThisUser(User thisUser) {
        this.thisUser = thisUser;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if (thisUser == null) {
            return;
        }
        thisUser.setTestId(nextUserId);
        thisUser.setNextUserid(++nextUserId);
        thisUser.setId(0);

        db.collection("users").document(userId).collection("profile")
                .document(String.valueOf(thisUser.getId())).set(thisUser);

//        Map userMap = new HashMap();
//
//        userMap.put("first_name", thisUser.getFirstName());
//        userMap.put("last_name", thisUser.getLastName());
//        userMap.put("language", thisUser.getLanguage());
//        userMap.put("major", thisUser.getMajor());
//        userMap.put("bio", thisUser.getBio());
//        userMap.put("availability", thisUser.getAvailability());
//        userMap.put("photo", thisUser.getPhoto());
//        userMap.put("uid", currentUserID);
//
//
//        usersRef.child(currentUserID).updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
//            @Override
//            public void onComplete(@NonNull Task task) {
//                if (task.isSuccessful()) {
//                    //Toast.makeText(, "User Correctly Uploaded to Firebase", Toast.LENGTH_SHORT).show();
//                } else {
//                }
//            }
//        });


    }

    /**
     * Getter for the course list
     *
     * @return list of course objects
     */
    public List<String> getCourses() {
        return courses;
    }


    /**
     * Getter for the language list
     *
     * @return list of language objects
     */
    public List<String> getLanguages() {
        return languages;
    }


    /**
     * Getter for the majors list
     *
     * @return list of major objects
     */
    public List<String> getMajors() {
        return majors;
    }

}


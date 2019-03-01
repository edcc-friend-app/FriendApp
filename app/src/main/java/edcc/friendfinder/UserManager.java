package edcc.friendfinder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.content.Context;

public class UserManager {

    private static UserManager um;
    private ArrayList<User> users;
    private ArrayList<User> potFriends;
    private DataHandler dh;
    private User thisUser;
    private ArrayList<Course> classes;
    private static String userId;
    private int[] currClasses;
    private Match match;

    private UserManager() {
        dh = new DataHandler();
        users = dh.getFriends();
        potFriends = dh.getPotFriends();
        classes = new ArrayList<>();
        classes.add(new Course("CS 240", "Linda Zuvich"));
        classes.add(new Course("MATH 272", "Wayne Neidhardt"));
        classes.add(new Course("PHYS 222", "Tom Flemming"));
        thisUser = new User("Estefano", "Felipa", "CS", "Soccer",
                users, classes, 958024838, "Spanish");
        currClasses = new int[25];
        currClasses[0] = 1;
        currClasses[6] = 1;
        currClasses[15] = 1;
        thisUser.setArrMatch(currClasses);
        match = new Match(thisUser, potFriends);
    }


    public static UserManager getUserManager() {
        UserManager.userId = userId;
        if (um == null) {
            um = new UserManager();
        }
        return um;
    }

    public List<User> getUsers() {
        Collections.sort(users);
        return users;
    }

    public List<User> getPotentialFriends() {
        return match.getPotFriends();
    }


    public User getUser(int id) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id) {
                return users.get(i);
            }
        }
        return null;
    }

    public User getPotFriend(int id) {
        for (int i = 0; i < potFriends.size(); i++) {
            if (potFriends.get(i).getId() == id) {
                return potFriends.get(i);
            }
        }
        return null;
    }

    public void addFriend(User user) {
        users.add(user);
        dh.addFriends(users);
        potFriends.remove(user);
        dh.addPotFriends(potFriends);
    }

    public void deleteUser(User user) {
        users.remove(user);
        dh.addFriends(users);
    }

    public void updateUser(User user) {
        int index = -1;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == user.getId()) {
                index = i;
            }
        }
        users.get(index).setFriends(user.getFriends());
        users.get(index).setBio(user.getBio());
        users.get(index).setMajor(user.getMajor());
        users.get(index).setFirstName(user.getFirstName());
        users.get(index).setLastName(user.getLastName());
    }

    /* Replaces a pet object in the pet list when a user has updated details.
     *
     * @param pet the pet object with which to replace the existing one
     */
    void replaceUser(User pet) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == pet.getId()) {
                users.set(i, pet);
            }
        }
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        db.collection("users").document(userId).collection("pets")
//                .document(String.valueOf(pet.getPetId())).set(pet);
    }

    public User getThisUser() {
        return thisUser;
    }

    public void setThisUser(User thisUser) {
        this.thisUser = thisUser;
    }
}



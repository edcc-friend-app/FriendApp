package edcc.friendfinder;

import android.content.Context;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        courses = new ArrayList<>();
        courses.add("ACCT& 201");
        courses.add("ACCT& 202");
        courses.add("ACCT& 203");
        courses.add("ASL& 121");
        courses.add("ASL& 122");
        courses.add("ASL& 123");
        courses.add("ANTH 201");
        courses.add("ANTH 202");
        courses.add("ANTH 203");
        courses.add("ANTH 201");
        courses.add("ARAB 121");
        courses.add("ARAB 122");
        courses.add("ARAB 123");
        courses.add("ART 101");
        courses.add("ART 102");
        courses.add("ART 103");
        courses.add("BIOL& 211");
        courses.add("BIOL& 212");
        courses.add("BIOL& 213");
        courses.add("CHEM& 141");
        courses.add("CHEM& 142");
        courses.add("CHEM& 143");
        courses.add("CHEM& 241");
        courses.add("CHEM& 242");
        courses.add("CHEM& 243");
        courses.add("CHIN& 121");
        courses.add("CHIN& 122");
        courses.add("CHIN& 123");
        languages = new ArrayList<>();
        languages.add("Arabic");
        languages.add("Chinese (Cantonese)");
        languages.add("Chinese (Mandarin)");
        languages.add("English");
        languages.add("French");
        languages.add("German");
        languages.add("Indonesian (Malay)");
        languages.add("Hindustani");
        languages.add("Japanese");
        languages.add("Korean");
        languages.add("Russian");
        languages.add("Spanish");
        languages.add("Vietnamese");
        majors = new ArrayList<>();
        majors.add("Accounting");
        majors.add("Allied Health Education");
        majors.add("Biology");
        majors.add("Business");
        majors.add("Business Information Technology");
        majors.add("Business Management");
        majors.add("Business Training Center");
        majors.add("Child, Youth, and Family Studies");
        majors.add("Computer Information Systems");
        majors.add("Computer Science");
        majors.add("Construction Management");
        majors.add("Culinary Arts");
        majors.add("Early Childhood Education");
        majors.add("Emergency Management");
        majors.add("Engineering and Science");
        majors.add("Engineering Technology");
        majors.add("Event Planning");
        majors.add("Family Support Studies");
        majors.add("General Studies");
        majors.add("Horticulture");
        majors.add("Hospitality and Tourism");
        majors.add("Music");
        majors.add("Nursing");
        majors.add("Occupational Safety and Health");
        majors.add("Paralegal");
        majors.add("Social and Human Services");
        majors.add("Transfer (General)");
        majors.add("Visual Communications");
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

    void replaceUser(User user) {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getId() == user.getId()) {
                userList.set(i, user);
            }
        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(userId).collection("friends")
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
        db.collection("users").document(userId).collection("friends")
                .document(String.valueOf(newUser.getId())).set(newUser);
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
            db.collection("users").document(userId).collection("friends")
                    .document(String.valueOf(id)).delete();
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


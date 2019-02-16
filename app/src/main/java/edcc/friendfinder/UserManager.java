package edcc.friendfinder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserManager {

    private static UserManager um;
    private ArrayList<User> users;
    private DataHandler dh;

    private UserManager() {
        dh = new DataHandler();
        users = dh.getUsers();
    }

    public static UserManager getUserManager() {
        if (um == null) {
            um = new UserManager();
        }
        return um;
    }

    public List<User> getUsers() {
        Collections.sort(users);
        return users;
    }

//    public String[] getNames() {
//
//    }

    public User getUser(int id) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id) {
                return users.get(i);
            }
        }
        return null;
    }

    public void addUser(User user) {
        users.add(user);
        dh.addUsers(users);
    }

    public void deleteUser(User user) {
        users.remove(user);
        dh.addUsers(users);
    }

    public void updateUser(User user) {
        int index = -1;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == user.getId()) {
                index = i;
            }
        }
        users.get(index).setDateOfBirth(user.getDateOfBirth());
        users.get(index).setEdmail(user.getEdmail());
        users.get(index).setFriends(user.getFriends());
        users.get(index).setInterests(user.getInterests());
        users.get(index).setMajor(user.getMajor());
        users.get(index).setPhoneNumber(user.getPhoneNumber());
        users.get(index).setFirstName(user.getFirstName());
        users.get(index).setLastName(user.getLastName());
    }
}

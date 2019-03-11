package edcc.friendfinder;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class UserManagerTest {

    User thisUser;

    @Before
    public void setUp() throws Exception {
        List<Integer> classes = new ArrayList<>();
        classes.add(2);
        classes.add(4);
        classes.add(16);
        thisUser = new User("My", "UMT", "Business", "Getting 4.0's", "English", "Err Day", classes);

    }

    @Test
    public void getUserManager() {
    }

    @Test
    public void getUser() {
    }

    @Test
    public void setUser() {
    }

    @Test
    public void getPotentialFriends() {
    }

    @Test
    public void getFriendList() {
    }

    @Test
    public void setFriendList() {
    }

    @Test
    public void getFriend() {
    }

    @Test
    public void setFriend() {
    }

    @Test
    public void getThisUser() {
    }

    @Test
    public void getCourses() {
    }

    @Test
    public void getLanguages() {
    }

    @Test
    public void getMajors() {
    }
}
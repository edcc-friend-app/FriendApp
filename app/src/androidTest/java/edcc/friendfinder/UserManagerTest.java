package edcc.friendfinder;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class UserManagerTest {


    User thisUser;
    User diffUser;
    ArrayList<User> friends;
    private Match match;
    Context appContext = InstrumentationRegistry.getTargetContext();

    @Before
    public void setUp() throws Exception {
        List<Integer> classes = new ArrayList<>();
        classes.add(0);
        classes.add(8);
        classes.add(24);
        thisUser = new User("Test", "One", "Music", "Watching movies", "Chinese", "Everjy weekend", classes);
        //friends set up
        List<Integer> classes1 = new ArrayList<>();
        classes1.add(0);
        classes1.add(3);
        classes1.add(6);
        User friend1 = new User("Franshesco", "Coello", "Computer Science", "Soccer", "English",
                "All day", classes1);

        List<Integer> classes2 = new ArrayList<>();
        classes2.add(3);
        classes2.add(12);
        classes2.add(21);
        User friend2 = new User("Anthony", "Luong", "Music", "Memes", "Vietnamese", "All day", classes2);

        List<Integer> classes3 = new ArrayList<>();
        classes3.add(8);
        classes3.add(5);
        classes3.add(24);
        User friend3 = new User("Jonathan", "Young", "Business", "Football", "Chinese", "All day", classes3);

        List<Integer> classes4 = new ArrayList<>();
        classes4.add(13);
        classes4.add(4);
        classes4.add(19);
        User friend4 = new User("Susy", "Lincoln", "Music", "Dancing", "Spanish", "All day", classes4);

        List<Integer> classes5 = new ArrayList<>();
        classes5.add(23);
        classes5.add(1);
        classes5.add(16);
        User friend5 = new User("Jared", "Kuddes", "Nursing", "Memes", "French", "All day", classes5);

        friends = new ArrayList<>();
        friends.add(friend1);
        friends.add(friend2);
        friends.add(friend3);
        friends.add(friend4);
        friends.add(friend5);
        match = new Match(thisUser, friends);
    }

    @Test
    public void getUserManager() {

    }

    @Test
    public void getUser() {
        assertNull(diffUser);
        assertNotNull(thisUser);
        assertEquals(thisUser.toString(), "0.0% Test One");
    }


    @Test
    public void getPotentialFriends() {
        List<User> user;
        user = match.getPotFriends();
        System.out.println(match.toString());
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
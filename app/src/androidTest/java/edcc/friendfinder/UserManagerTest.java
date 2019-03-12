package edcc.friendfinder;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserManagerTest {


    User thisUser;
    ArrayList<User> friends;
    private Match match;
    Context appContext = InstrumentationRegistry.getTargetContext();
    UserManager um;

    @Before
    public void setUp() throws Exception {
        um = UserManager.getUserManager(appContext, "0");
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
    public void testGetUserManager() {
        assertNotNull(um);
    }

    @Test
    public void testGetUser() {
        User user1 = um.getUser("Anthony Luong");
        assertEquals("Anthony", user1.getFirstName());
        assertEquals("Luong", user1.getLastName());
        User user2 = um.getUser("Jonathan Young");
        assertEquals("Jonathan", user2.getFirstName());
        assertEquals("Young", user2.getLastName());
        User user3 = um.getUser("Franshesco Coello");
        assertEquals("Franshesco", user3.getFirstName());
        assertEquals("Coello", user3.getLastName());

    }


    @Test
    public void testGetPotentialFriends() {
        List<User> friends = um.getPotentialFriends();
        assertEquals("0.0% Franshesco Coello", friends.get(0).toString());
        assertEquals("0.0% Anthony Luong", friends.get(1).toString());
        assertEquals("0.0% Jonathan Young", friends.get(2).toString());
        assertEquals("0.0% Susy Lincoln", friends.get(3).toString());
        assertEquals("0.0% Jared Kuddes", friends.get(4).toString());
        assertEquals("0.0% Sora Fung", friends.get(5).toString());
        assertEquals("0.0% Roxas Pham", friends.get(6).toString());
        assertEquals("0.0% Vivian Nguyen", friends.get(7).toString());
        assertEquals("0.0% Henry Zokkins", friends.get(8).toString());
        assertEquals("0.0% Vy Bui", friends.get(9).toString());

    }

}
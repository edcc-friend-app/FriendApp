package edcc.friendfinder;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MatchTest {

    Match match;
    User user;
    User friend1;
    User friend2;
    User friend3;
    User friend4;
    User friend5;
    ArrayList<User> friends;

    @Before
    public void setUp() throws Exception{
        List<Integer> classes = new ArrayList<>();
        classes.add(0);
        classes.add(8);
        classes.add(24);
        user = new User("Test", "One", "Music", "Watching movies", "Chinese", "Everjy weekend", classes);
        //friends set up
        List<Integer> classes1 = new ArrayList<>();
        classes1.add(0);
        classes1.add(3);
        classes1.add(6);
        friend1 = new User("Franshesco", "Coello", "Computer Science", "Soccer", "English",
                "All day", classes1);

        List<Integer> classes2 = new ArrayList<>();
        classes2.add(3);
        classes2.add(12);
        classes2.add(21);
        friend2 = new User("Anthony", "Luong", "Music", "Memes", "Vietnamese", "All day", classes2);

        List<Integer> classes3 = new ArrayList<>();
        classes3.add(8);
        classes3.add(5);
        classes3.add(24);
        friend3 = new User("Jonathan", "Young", "Business", "Football", "Chinese", "All day", classes3);

        List<Integer> classes4 = new ArrayList<>();
        classes4.add(13);
        classes4.add(4);
        classes4.add(19);
        friend4 = new User("Susy", "Lincoln", "Music", "Dancing", "Spanish", "All day", classes4);

        List<Integer> classes5 = new ArrayList<>();
        classes5.add(23);
        classes5.add(1);
        classes5.add(16);
        friend5 = new User("Jared", "Kuddes", "Nursing", "Memes", "French", "All day", classes5);

        friends = new ArrayList<>();
        friends.add(friend1);
        friends.add(friend2);
        friends.add(friend3);
        friends.add(friend4);
        friends.add(friend5);
        match = new Match(user, friends);
    }
    @Test
    public void testGetPotFriends() {
        List<User> users;
        users = match.getPotFriends();
        assertEquals(150, users.get(0).getMatchCount());
        assertEquals(60, users.get(1).getMatchCount());
        assertEquals(50, users.get(2).getMatchCount());
        assertEquals(50, users.get(3).getMatchCount());
        assertEquals(0, users.get(4).getMatchCount());
    }

    @Test
    public void compareMajor() {
        match.compareMajor();
        assertEquals(0, friends.get(0).getMatchCount());
        assertEquals(50, friends.get(1).getMatchCount());
        assertEquals(0, friends.get(2).getMatchCount());
        assertEquals(50, friends.get(3).getMatchCount());
        assertEquals(0, friends.get(4).getMatchCount());

    }

    @Test
    public void testCompareClasses() {
        match.compareClasses();
        assertEquals(60, friends.get(0).getMatchCount());
        assertEquals(0, friends.get(1).getMatchCount());
        assertEquals(120, friends.get(2).getMatchCount());
        assertEquals(0, friends.get(3).getMatchCount());
        assertEquals(0, friends.get(4).getMatchCount());
    }

    @Test
    public void testCompareLanguage() {
        match.compareLanguage();
        assertEquals(0, friends.get(0).getMatchCount());
        assertEquals(0, friends.get(1).getMatchCount());
        assertEquals(30, friends.get(2).getMatchCount());
        assertEquals(0, friends.get(3).getMatchCount());
        assertEquals(0, friends.get(4).getMatchCount());
    }

}
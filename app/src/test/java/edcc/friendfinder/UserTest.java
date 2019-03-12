package edcc.friendfinder;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;


public class UserTest {

    User user = new User();
    List<Integer> list = new ArrayList<>();

    @Before
    public void setUp() {
        user.setFirstName("Freddie");
        user.setLastName("Wonder");
        user.setMajor("Biology");
        user.setLanguage("Vietnamese");
        user.setAvailability("Free");
        user.setBio("I like to look at memes");
        user.setId(29);
        user.setPhoto("asui324nb12m");
        list.add(12);
        list.add(1);
        list.add(5);
        user.setArrMatch(list);

    }

    @Test
    public void testGetFirstName() {
        assertEquals("Freddie", user.getFirstName());
    }

    @Test
    public void testSetFirstName() {
        user.setFirstName("Tom");
        assertEquals("Tom", user.getFirstName());
    }

    @Test
    public void testGetLastName() {
        assertEquals("Wonder", user.getLastName());
    }

    @Test
    public void testSetLastName() {
        user.setLastName("Hanks");
        assertEquals("Hanks", user.getLastName());
    }

    @Test
    public void testGetAvailability() {
        assertEquals("Free", user.getAvailability());
    }

    @Test
    public void testSetAvailability() {
        user.setAvailability("Not available");
        assertEquals("Not available", user.getAvailability());
    }

    @Test
    public void testGetMajor() {
        assertEquals("Biology", user.getMajor());
    }

    @Test
    public void testSetMajor() {
        user.setMajor("Computer Science");
        assertEquals("Computer Science", user.getMajor());
    }

    @Test
    public void testGetBio() {
        assertEquals("I like to look at memes", user.getBio());
    }

    @Test
    public void testSetBio() {
        user.setBio("I prefer to study in groups");
        assertEquals("I prefer to study in groups", user.getBio());
    }

    @Test
    public void testGetId() {
        assertEquals(29, user.getId());
    }

    @Test
    public void setId() {
        user.setId(100);
        assertEquals(100, user.getId());
    }

    @Test
    public void getLanguage() {
        assertEquals("Vietnamese", user.getLanguage());
    }

    @Test
    public void setLanguage() {
        user.setLanguage("Spanish");
        assertEquals("Spanish", user.getLanguage());
    }

    @Test
    public void getPhoto() {
        assertEquals("asui324nb12m", user.getPhoto());
    }

    @Test
    public void setPhoto() {
        user.setPhoto("gibberish1234");
        assertEquals("gibberish1234", user.getPhoto());
    }

    @Test
    public void getArrMatch() {
        List<Integer> list2 = new ArrayList<>();
        list2.add(12);
        list2.add(1);
        list2.add(5);

        assertEquals(list2, user.getArrMatch());
    }

    @Test
    public void setArrMatch() {
        List<Integer> list3 = new ArrayList<>();
        list3.add(4);
        list3.add(5);
        list3.add(3);

        List<Integer> list4 = new ArrayList<>();
        list4.add(4);
        list4.add(5);
        list4.add(3);

        user.setArrMatch(list3);
        assertEquals(list4, user.getArrMatch());
    }

    @Test
    public void testToString() {
        assertEquals("0.0% Freddie Wonder", user.toString());
    }

    @Test
    public void compareTo() {
        User user2 = new User();
        user2.setFirstName("Tidus");
        user2.setLastName("Ngo");
        user2.setMajor("Biology");
        user2.setLanguage("Vietnamese");
        user2.setAvailability("Busy");
        user2.setBio("I like to swim");
        user2.setMatchCount(20);
        assertTrue(user.compareTo(user2) > 0);
    }

    @Test
    public void incrementCount() {
        user.incrementCount("class");
        assertEquals(60, user.getMatchCount());
        user.incrementCount("major");
        assertEquals(110, user.getMatchCount());
        user.incrementCount("language");
        assertEquals(140, user.getMatchCount());
    }
}
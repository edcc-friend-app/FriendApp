package edcc.friendfinder;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class UserTest {

    User user = new User();

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

    }
    @Test
    public void getFirstName() {
        assertEquals("Freddie", user.getFirstName());
    }

    @Test
    public void setFirstName() {
        user.setFirstName("Tom");
        assertEquals("Tom", user.getFirstName());
    }

    @Test
    public void getLastName() {
        assertEquals("Wonder", user.getLastName());
    }

    @Test
    public void setLastName() {
        user.setLastName("Hanks");
        assertEquals("Hanks", user.getLastName());
    }

    @Test
    public void getAvailability() {
        assertEquals("Free", user.getAvailability());
    }

    @Test
    public void setAvailability() {
        user.setAvailability("Not available");
        assertEquals("Not available", user.getAvailability());
    }

    @Test
    public void getMajor() {
        assertEquals("Biology", user.getMajor());
    }

    @Test
    public void setMajor() {
        user.setMajor("Computer Science");
        assertEquals("Computer Science", user.getMajor());
    }

    @Test
    public void getBio() {
        assertEquals("I like to look at memes", user.getBio());
    }

    @Test
    public void setBio() {
        user.setBio("I prefer to study in groups");
        assertEquals("I prefer to study in groups", user.getBio());
    }

    @Test
    public void getId() {
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
    }

    @Test
    public void setArrMatch() {
    }

    @Test
    public void getMatchCount() {
    }

    @Test
    public void setMatchCount() {
    }

    @Test
    public void testToString() {
    }

    @Test
    public void compareTo() {
    }

    @Test
    public void matchCompare() {
    }

    @Test
    public void incrementCount() {
    }
}
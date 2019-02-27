package edcc.friendfinder;

public class Course {
    private String name;
    private String instructor;

    public Course(String name, String instructor) {
        this.name = name;
        this.instructor = instructor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }


    @Override
    public String toString() {
        return name;
    }
}

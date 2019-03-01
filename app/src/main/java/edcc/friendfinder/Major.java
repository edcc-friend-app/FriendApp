package edcc.friendfinder;

/**
 * Class represents Majors available
 *
 * @author Jonathan Young
 * @author Anthony Luong
 * @author Estefano Felipe
 */
class Major implements Comparable<Major> {

    private String[] majors = {"Accounting", "Allied Health Education", "Biology", "Business",
            "Business Information Technology", "Business Management", "Business Training Center",
            "Child, Youth, and Family Studies", "Computer Information Systems", "Computer Science",
            "Construction Management", "Culinary Arts", "Early Childhood Education", "Emergency Management",
            "Engineering and Science", "Engineering Technology", "Event Planning", "Family Support Studies",
            "General Studies", "Horticulture", "Hospitality and Tourism", "Music", "Nursing",
            "Occupational Safety and Health", "Paralegal", "Social and Human Services",
            "Transfer (General)", "Visual Communications"};
    private String major;
    private int majorId;

    public Major() {

    }


    public Major(int incMajors) {
        major = majors[incMajors];
    }

    String getMajor(){
        return major;
    }

    String[] getMajorList(){
        return majors;
    }

    public int getMajorId() {
        return majorId;
    }

    public void setMajorId(int majorId) {
        this.majorId = majorId;
    }

    @Override
    public int compareTo(Major o) {
        return 0;
    }
}

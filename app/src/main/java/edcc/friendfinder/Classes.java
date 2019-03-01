package edcc.friendfinder;

class Classes implements Comparable<Classes> {

    static final String[] courses = new String[]{"ACCT& 201", "ACCT& 202", "ACCT& 203", "ASL& 121", "ASL& 122", "ASL& 123",
            "ANTH 201", "ANTH 202", "ANTH 203", "ARAB 121", "ARAB 122", "ARAB 123", "ART 101", "ART 102",
            "ART 103", "BIOL& 211", "BIOL& 212", "BIOL& 213", "CHEM& 141", "CHEM& 142", "CHEM& 143",
            "CHEM& 241", "CHEM& 242", "CHEM& 243", "CHIN& 121", "CHIN& 122", "CHIN& 123"};

    private String course;
    private int classId;

    public Classes() {

    }

    public Classes(int incClasses) {
        course = courses[incClasses];
    }

    String getCourse() {
        return course;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    String[] getCourseList() {
        return courses;


    }

    @Override
    public int compareTo(Classes o) {
        return 0;
    }
}

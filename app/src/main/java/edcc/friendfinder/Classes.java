package edcc.friendfinder;

/**
 * Classes list to populate in the spinner.
 * @author Anthony Luong
 * @author Estefano Felipa
 * @author Jonathan Young
 * @version 1.0 3/10/19
 */
class Classes implements Comparable<Classes> {

    static final String[] courses = new String[]{"ACCT& 201", "ACCT& 202", "ACCT& 203", "ASL& 121", "ASL& 122", "ASL& 123",
            "ANTH 201", "ANTH 202", "ANTH 203", "ARAB 121", "ARAB 122", "ARAB 123", "ART 101", "ART 102",
            "ART 103", "BIOL& 211", "BIOL& 212", "BIOL& 213", "CHEM& 141", "CHEM& 142", "CHEM& 143",
            "CHEM& 241", "CHEM& 242", "CHEM& 243", "CHIN& 121", "CHIN& 122", "CHIN& 123"};

    private String course;

    public Classes() {

    }

    public Classes(int incClasses) {
        course = courses[incClasses];
    }

    @Override
    public int compareTo(Classes o) {
        return 0;
    }
}

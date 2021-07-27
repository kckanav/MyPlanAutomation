package Structures;

public class Course {

    public final String section;
    public final String sln;
    public final String availability;
    public final String classTimings;

    public Course(String sln, String section, String availability, String classTimings) {
        this.section = section;
        this.availability = availability;
        this.sln = sln;
        this.classTimings = classTimings;
    }
    public String lectureCode() {
        return "" + section.charAt(0);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() == this.getClass()) {
            Course toCheck = (Course) obj;
            return toCheck.sln == this.sln;
        }
        return false;
    }
}

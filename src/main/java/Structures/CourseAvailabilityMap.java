package Structures;

import java.util.*;

public class CourseAvailabilityMap {

    private Map<Course, List<Course>> map;
    public final String courseName;

    public CourseAvailabilityMap(String courseName) {
        this.courseName = courseName;
        map = new HashMap<>();
    }

    public boolean putLecture(Course course) {
        if (!map.containsKey(course)) {
            map.put(course, new LinkedList<>());
            return true;
        } else {
            return false;
        }
    }

    public boolean putSection(Course section) {
        for (Course c: map.keySet()) {
            if (section.section.charAt(0) == c.section.charAt(0)) {
                map.get(c).add(section);
                return true;
            }
        }
        return false;
    }

    public boolean containsLecture(String sectionCode) {
        for (Course c: map.keySet()) {
            if (sectionCode.charAt(0) == c.section.charAt(0)) {
                return true;
            }
        }
        return false;
    }

    public List<Course> getSections(Course lec) {
        return map.get(lec);
    }

    @Override
    public String toString() {
        String toReturn = "Course Availability for " + courseName + "\n";
        for (Course lec : map.keySet()) {
            toReturn += lec.section + " " + lec.classTimings + " " + lec.availability + " (" + lec.sln + ")\n";
            for (Course sec: map.get(lec)) {
                toReturn += "   " + sec.section + " " + sec.classTimings + " " + sec.availability + " (" + sec.sln + ")\n";
            }
        }
        return toReturn;
    }
}

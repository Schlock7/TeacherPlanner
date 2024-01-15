package misc;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public abstract class Calculator extends Types {
    public static float getAttendanceDecimal(Student student) {
        ArrayList<Boolean> rawAttendance = new ArrayList<>(student.attendance.values());
        int present = 0;
        int absent = 0;

        for (Boolean bool : rawAttendance) {
            if (bool) {
                present++;
            }
            else {
                absent++;
            }
        }
        return (float) present / (present+absent);
    }

    public static float getAverageGrade(Student student) {
        ArrayList<Integer> rawGrades = new ArrayList<>(student.grades.values());
        int sum = 0;

        for (Integer rawGrade : rawGrades) {
            sum = sum + rawGrade;
        }
        if (rawGrades.isEmpty()) {
            return -1;
        }
        return((float) Math.round(((float) sum / rawGrades.size()) * 100) / 100);
    }
}

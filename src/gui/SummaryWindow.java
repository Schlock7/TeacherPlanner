package gui;

import misc.PieChart;
import misc.Types;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

import static misc.Calculator.getAttendanceDecimal;
import static misc.Calculator.getAverageGrade;

public class SummaryWindow extends Types {
    private JFrame mainFrame = new JFrame();

    public SummaryWindow(Student student) {
        mainFrame.setLayout(new BoxLayout(mainFrame.getContentPane(), BoxLayout.Y_AXIS));
        mainFrame.setResizable(false);

        mainFrame.add(new PieChart(student));

        JLabel attendanceLabel = new JLabel("Attendance: " + getAttendanceDecimal(student) * 100 + "%");
        setPreferredSizeAndAdd(attendanceLabel);

        if (getAverageGrade(student) == -1) {
            JLabel noGradeLabel = new JLabel("No grade information available");
            setPreferredSizeAndAdd(noGradeLabel);
        } else {
            JLabel gradeLabel = new JLabel("Average grade: " + getAverageGrade(student));
            setPreferredSizeAndAdd(gradeLabel);
        }

        generateTable(student);

        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    private void generateTable(Student student) {
        String[] columnNames = {"Assignment", "Grade"};
        String[][] data = new String[student.grades.size()][2];

        Collection<String> rawKeys = student.grades.keySet();

        int i = 0;
        for (String key : rawKeys)
        {
            data[i][0] = key;
            data[i][1] = String.valueOf(student.grades.get(key));
            i++;
        }

        JTable attendanceTable = new JTable(data, columnNames);
        attendanceTable.setMaximumSize(new Dimension(200, 120));

        JScrollPane tableSP = new JScrollPane(attendanceTable);
        tableSP.setPreferredSize(new Dimension(200, 120));
        mainFrame.add(tableSP);
    }

    public void setPreferredSizeAndAdd(JLabel label) {
        label.setPreferredSize(new Dimension(200, 22));
        mainFrame.add(label);
    }
}

package gui;

import misc.Types;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

public class ViewReportWindow extends Types {
    JFrame mainFrame = new JFrame();
    AttendanceReport report;
    ViewReportWindow(AttendanceReport inputReport) {
        mainFrame.setLayout(new BorderLayout());
        report = inputReport;
        JLabel titleLabel = new JLabel("  " + report.title);
        titleLabel.setPreferredSize(new Dimension(300, 30));
        mainFrame.add(titleLabel, BorderLayout.NORTH);
        generateTable();
        mainFrame.pack();
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);
    }

    private void generateTable() {
        String[] columnNames = {"Student name", "Presence", "Reason"};
        String[][] data = new String[report.reportFlock.students.size()][3];

        int i = 0;
        for (Student student :  report.reportFlock.students)
        {
            if (student.attendance.get(report.reportDate) != null) {
                if (student.attendance.get(report.reportDate)) {
                    data[i][1] = "present";
                } else {
                    data[i][1] = "absent";
                }
            }
            else {
                break;
            }

            data[i][0] = student.studentName;

            if (student.excuse.get(report.reportDate) != null) {
                data[i][2] = student.excuse.get(report.reportDate);
            }
            else {
                data[i][2] = "N/A";
            }
            i++;
        }

        JTable attendanceTable = new JTable(data, columnNames);

        JScrollPane tableSP = new JScrollPane(attendanceTable);
        mainFrame.add(tableSP, BorderLayout.CENTER);
    }
}

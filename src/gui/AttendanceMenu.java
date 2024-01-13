package gui;

import misc.Types;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;

public class AttendanceMenu extends Types {
    static ArrayList<JPanel> attendanceRows = new ArrayList<>();
    static transient JFrame mainFrame = new JFrame();
    private static Calendar selectedDate;
    private static JDateChooser dateChooser;

    static Flock attendanceFlock;

    AttendanceMenu(Flock flock) {
        attendanceFlock = flock;
        mainFrame.setLayout(new GridLayout(0, 1, 20, 5));
        mainFrame.add(new titleDatePanel());
        updateAttendanceRows();
        mainFrame.pack();
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);
    }

    public void updateAttendanceRows() {
        for (JPanel attendanceRow : attendanceRows) {
            mainFrame.remove(attendanceRow);
        }
        attendanceRows.clear();
        for (int i = 0; i < attendanceFlock.students.size(); i++) {
            AttendanceRow attendanceRow = new AttendanceRow(attendanceFlock.students.get(i));
            attendanceRows.add(attendanceRow);
            mainFrame.add(attendanceRow);
        }
        mainFrame.repaint();
        mainFrame.pack();
    }

    static class AttendanceRow extends JPanel {
        AttendanceRow(Types.Student student) {
            this.setBackground(Color.lightGray);
            JLabel name = new JLabel(student.studentName);

            JCheckBox present = new JCheckBox("Present?");
            present.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Handle checkbox action if needed
                }
            });

            this.add(name);
            this.add(present);
        }
    }
    public static class titleDatePanel extends JPanel {
        titleDatePanel() {
            JLabel title = new JLabel("Attendance report for the date: ");
            this.add(title);
            dateChooser = new JDateChooser();
            dateChooser.setDate(Calendar.getInstance().getTime()); // Set the initial date to the current date

            // Event listener for date selection
            dateChooser.getDateEditor().addPropertyChangeListener("date", evt -> {
                selectedDate = convertDateToCalendar(dateChooser.getDate());
            });
            this.add(dateChooser);
        }

        private Calendar convertDateToCalendar(java.util.Date date) {
            if (date == null) {
                return null;
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        }
    }

    public static class submitCancelRow extends JPanel {
        submitCancelRow() {
            JButton submit = new JButton("Submit");
            submit.addActionListener(e->{

            });

            JButton cancel = new JButton("Cancel");
            cancel.addActionListener(e -> {
                mainFrame.setVisible(false);
                mainFrame.dispose();
            });
        }
    }
}
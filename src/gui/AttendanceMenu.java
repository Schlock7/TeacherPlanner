package gui;

import misc.Types;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AttendanceMenu extends Types {
    static ArrayList<JPanel> attendanceRows = new ArrayList<>();
    transient JFrame mainFrame = new JFrame();
    private static Calendar selectedDate;
    private static JDateChooser dateChooser;

    static Flock attendanceFlock;

    AttendanceMenu(Flock flock) throws IOException {
        selectedDate = getSelectedDate();
        attendanceFlock = flock;
        setDefaultAttendance();
        mainFrame.setLayout(new GridLayout(0, 1, 20, 5));
        new titleDatePanel();
        updateAttendanceRows();
        new submitCancelRow();
        mainFrame.repaint();
        mainFrame.pack();
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);
    }

    public Calendar getSelectedDate() {
        JPanel panel = new JPanel();
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDate(Calendar.getInstance().getTime());
        dateChooser.setPreferredSize(new Dimension(115, 35));
        panel.add(dateChooser);

        int result = JOptionPane.showConfirmDialog(null, panel, "Select a Date",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        Date selection = null;
        if (result == JOptionPane.OK_OPTION) {
            selection = dateChooser.getDate();
        }
        return convertDateToCalendar(selection);
    }

    public void setDefaultAttendance() throws IOException {
        for (Student student : attendanceFlock.students) {
            student.addAttendance(selectedDate, true);
        }
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
        mainFrame.pack();
        mainFrame.repaint();
    }

    class AttendanceRow extends JPanel {
        private JButton editExcuse;
        AttendanceRow(Student student) {
            this.setBackground(Color.lightGray);
            JLabel name = new JLabel(student.studentName);

            JCheckBox present = new JCheckBox("Present?");
            present.setSelected(true);
            present.addActionListener(e-> {
                if (!present.isSelected()) {
                    try {
                        student.addAttendance(selectedDate, false);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    editExcuse = new JButton("Edit excuse");
                    add(editExcuse);
                    editExcuse.addActionListener(event->{
                        try {
                            student.addExcuse(selectedDate, JOptionPane.showInputDialog("Excuse: "));
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    });
                }
                else {
                    try {
                        student.addAttendance(selectedDate, true);
                        student.addExcuse(selectedDate, null);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    remove(editExcuse);
                }
                revalidate();
                repaint();
                mainFrame.pack();
                mainFrame.repaint();
        });
            this.add(name);
            this.add(present);
        }
    }
    public class titleDatePanel extends JPanel {
        titleDatePanel() {
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy");
            String labelTitle = sdf.format(selectedDate.getTime());

            JLabel title = new JLabel("Attendance report for the date: " + labelTitle);
            this.add(title);

            mainFrame.add(this);
        }
    }

    public Calendar convertDateToCalendar(java.util.Date date) {
        if (date == null) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public class submitCancelRow extends JPanel {
        submitCancelRow() {
            JButton submit = new JButton("Submit");
            submit.addActionListener(e->{
                try {
                    new AttendanceReport(attendanceFlock, selectedDate);
                    writeFlocksFile();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                mainFrame.setVisible(false);
                mainFrame.dispose();
            });

            JButton cancel = new JButton("Cancel");
            cancel.addActionListener(e -> {
                for (Student student : attendanceFlock.students) {
                    student.attendance.remove(selectedDate);
                    student.excuse.remove(selectedDate);
                }
                mainFrame.setVisible(false);
                mainFrame.dispose();
            });
            this.add(submit);
            this.add(cancel);

            mainFrame.add(this);
        }
    }
}
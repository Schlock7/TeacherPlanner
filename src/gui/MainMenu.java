package gui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import misc.Types;

public class MainMenu extends Types implements Serializable{
    transient JFrame MainFrame = new JFrame();
    ArrayList<JPanel> studentPanels = new ArrayList<>();
    Flock exampleFlock = new Flock("Biology");

    Flock currentClass = flocks.get(0);

    class TopBar extends JPanel
    {
        TopBar() {
            JButton analyticsButton = new JButton("Analytics");
            JButton editClassesButton = new JButton("Edit Classes");
            JButton studentSearch = new JButton("Search Student");

            this.add(analyticsButton);
            this.add(editClassesButton);
            this.add(studentSearch);

            MainFrame.add(this);
        }
    }

    class MiddleBar extends JPanel implements Serializable
    {
        MiddleBar() {
             JComboBox<String> selectClass = new JComboBox<>(getFlockNames());
            selectClass.addActionListener(e -> {
                for (JPanel panel : studentPanels) {
                    MainFrame.remove(panel);
                }
                studentPanels.clear(); // Clear the list of studentPanels

                // Update currentClass to the selected class
                currentClass = flocks.get(selectClass.getSelectedIndex());

                // Add new StudentRow panels for each student in the selected class
                for (Student student : currentClass.students) {
                    StudentRow studentRow = new StudentRow(student);
                    studentPanels.add(studentRow);
                    MainFrame.add(studentRow);
                }
                MainFrame.repaint();
                MainFrame.pack();
            });

            selectClass.addActionListener(e -> currentClass = flocks.get(selectClass.getSelectedIndex()));

            JButton newAttendanceReport = new JButton("New Attendance Report");
            newAttendanceReport.addActionListener(e -> new AttendanceMenu());

            JButton addStudent = getAddStudentButton();

            JButton addFlock = new JButton("Add Class");
            addFlock.addActionListener(e -> {
                String name = JOptionPane.showInputDialog("Class name: ");
                if (name != null) {
                    if (!name.isEmpty()) {
                        try {
                            Flock newflock = new Flock(name);
                            MainFrame.remove(this);
                            MainFrame.add(new MiddleBar());
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        MainFrame.pack();
                    }
                }
            });

            this.add(selectClass);
            this.add(newAttendanceReport);
            this.add(addStudent);
            this.add(addFlock);

            MainFrame.add(this);
        }

        private JButton getAddStudentButton() {
            JButton addStudent = new JButton("Add Student");
            addStudent.addActionListener(e -> {
                String studentName = JOptionPane.showInputDialog("Student name: ");
                if (studentName != null) {
                    if (!studentName.isEmpty()) {
                        try {
                            Student newStudent = new Student(currentClass, studentName);
                            currentClass.students.add(newStudent);
                            MainFrame.add(new StudentRow(newStudent));
                        } catch (ClassNotFoundException | IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        MainFrame.pack();
                    }
                }
            });
            return addStudent;
        }
    }

    class StudentRow extends JPanel
    {
        StudentRow(Student student)
        {
            studentPanels.add(this);

            this.setBackground(Color.lightGray);

            JLabel name = new JLabel(student.studentName);

            JButton unenroll = getUnenrollButton(student);

            JButton addGrade = new JButton("Add Grade");

            JButton summary = new JButton("Summary");

            this.add(name);
            this.add(unenroll);
            this.add(addGrade);
            this.add(summary);

            // add exclamation mark for bad performance as if statement here later
        }

        private JButton getUnenrollButton(Student student) {
            JButton unenroll = new JButton("Unenroll");
            unenroll.addActionListener(e -> {
                for (JPanel panel : studentPanels) {
                    MainFrame.remove(panel);
                }
                studentPanels.remove(currentClass.findStudentIndexByName(student.studentName));
                currentClass.students.remove(currentClass.findStudentIndexByName(student.studentName));
                for (JPanel panel : studentPanels) {
                    MainFrame.add(panel);
                }
                MainFrame.repaint();
                MainFrame.pack();
            });
            return unenroll;
        }
    }
    MainMenu() throws IOException {
        MainFrame.setResizable(false);
        MainFrame.setLayout(new GridLayout(0, 1, 20, 5));
        new TopBar();
        new MiddleBar();
        MainFrame.pack();
        MainFrame.setLocationRelativeTo(null);
        MainFrame.setVisible(true);
        for (int i = 0; i < currentClass.students.size(); i++) {
            StudentRow studentRow = new StudentRow(currentClass.students.get(i));
            MainFrame.add(studentRow);
        }
    }
}

// use fileoutputstream and fileinputstream
package gui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import misc.Types;

public class MainMenu extends Types implements Serializable{
    JFrame MainFrame = new JFrame();
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
        MiddleBar()
        {
            JComboBox<String> selectClass = new JComboBox<>(GetFlockNames());
            selectClass.addActionListener(e -> currentClass = flocks.get(selectClass.getSelectedIndex()));

            JButton newAttendanceReport = new JButton("New Attendance Report");
            newAttendanceReport.addActionListener(e -> new AttendanceMenu());

            JButton addStudent = new JButton("Add Student");
            addStudent.addActionListener(e -> {
                String studentName = JOptionPane.showInputDialog("Student name: ");
                if (studentName != null) {
                    if (!studentName.equals("")) {
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

            this.add(selectClass);
            this.add(newAttendanceReport);
            this.add(addStudent);

            MainFrame.add(this);
        }
    }

    class StudentRow extends JPanel
    {
        StudentRow(Student student)
        {
            studentPanels.add(this);

            this.setBackground(Color.lightGray);

            JLabel name = new JLabel(student.studentName);

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

            JButton addGrade = new JButton("Add Grade");

            JButton summary = new JButton("Summary");

            this.add(name);
            this.add(unenroll);
            this.add(addGrade);
            this.add(summary);

            // add exclamation mark for bad performance as if statement here later
        }
    }
    MainMenu() throws IOException {
        Flock biology = new Flock("biology");
        MainFrame.setResizable(false);
        MainFrame.setLayout(new GridLayout(0, 1, 20, 20));
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
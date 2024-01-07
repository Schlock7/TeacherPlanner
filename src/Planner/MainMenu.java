package Planner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class MainMenu {
    JFrame MainFrame = new JFrame();
    ArrayList<String> classes = new ArrayList<>();
    ArrayList<String> students = new ArrayList<>();
    ArrayList<JPanel> studentPanels = new ArrayList<>();
    String currentClass;

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

    class MiddleBar extends JPanel
    {
        MiddleBar()
        {
            String[] classesConst = new String[classes.size()];
            classesConst = classes.toArray(classesConst);

            JComboBox<String> selectClass = new JComboBox<>(classesConst);
            selectClass.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    currentClass = classes.get(selectClass.getSelectedIndex());
                }
            });

            JButton newAttendanceReport = new JButton("New Attendance Report");
            newAttendanceReport.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new AttendanceMenu();
                }
            });

            JButton addStudent = new JButton("Add Student");
            addStudent.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String studentName = JOptionPane.showInputDialog("Student name: ");
                    if (studentName.length() != 0) {
                        students.add(studentName);
                        MainFrame.add(new StudentRow(studentName, currentClass));
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
    class StudentRow extends JPanel {
        StudentRow(String studentName, String currentclass) {
            studentPanels.add(this);
            System.out.println(studentPanels.size());

            this.setBackground(Color.lightGray);

            JLabel name = new JLabel(studentName);

            JButton unenroll = new JButton("Unenroll");
            unenroll.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("action event");
                    for (JPanel panel : studentPanels) {
                        MainFrame.remove(panel);
                    }
                    studentPanels.remove(students.indexOf(studentName));
                    students.remove(studentName); //must still make this general
                    for (JPanel panel : studentPanels) {
                        MainFrame.add(panel);
                    }
                    MainFrame.repaint();
                    MainFrame.pack();
                }
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
    MainMenu() {
        MainFrame.setResizable(false);
        classes.add("Biology");
        if (classes.isEmpty()) {
            classes.add("No classes exist");
        }
        /* else {
            classes.remove("No classes exist");
        } */

        MainFrame.setLayout(new GridLayout(0, 1, 20, 20));
        new TopBar();
        new MiddleBar();
        MainFrame.pack();
        MainFrame.setLocationRelativeTo(null);
        MainFrame.setVisible(true);
        for (String studentName : students) {
            StudentRow studentRow = new StudentRow(studentName, currentClass);
            MainFrame.add(studentRow);
        }
    }
}

// use fileoutputstream and fileinputstream
package Planner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainMenu {
    JFrame MainFrame = new JFrame();
    ArrayList<String> classes = new ArrayList<String>();
    ArrayList<String> students = new ArrayList<String>();
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
                    students.add(studentName);
                    for (int i = 0; i < students.toArray().length; i++) {
                        System.out.println(students.get(i));
                    }
                }
            });

            this.add(selectClass);
            this.add(newAttendanceReport);
            this.add(addStudent);

            MainFrame.add(this);
        }

        class StudentRow extends JPanel {
            StudentRow() {

                this.setBackground(Color.lightGray);

                JLabel name = new JLabel();

                JButton unenroll = new JButton("Unenroll");
                unenroll.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        students.remove(name); //this does not work
                    }
                });



                // add exclamation mark for bad performance as if statement here later


            }
        }
    }
    MainMenu() {
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
    }
}
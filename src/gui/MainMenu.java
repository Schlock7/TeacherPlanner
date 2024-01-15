package gui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

import misc.Types;

public class MainMenu extends Types implements Serializable{
    transient JFrame mainFrame = new JFrame();
    static ArrayList<JPanel> studentPanels = new ArrayList<>();
    private static Flock currentClass;

    MainMenu() throws IOException, ClassNotFoundException {
        try {
            currentClass = flocks.get(0);
        }
        catch (IndexOutOfBoundsException e) {
            currentClass = new Flock("Example class");
        }
        if (flocks.size() > 1) {
            for (String name : getFlockNames())
                if (name.equals("Example class")) {
                    flocks.remove(0);
                    currentClass = flocks.get(0);
                }
        }
        mainFrame.setResizable(false);
        mainFrame.setLayout(new GridLayout(0, 1, 20, 5));
        mainFrame.setLocationRelativeTo(null);
        new TopBar();
        new MiddleBar();
        mainFrame.setVisible(true);
        updateStudentRows();
        mainFrame.pack();
    }

    class TopBar extends JPanel
    {
        TopBar() {
            JButton editClassesButton = new JButton("Edit Classes");
            editClassesButton.addActionListener(e-> {
                mainFrame.setVisible(false);
                mainFrame.dispose();
                new EditClassesMenu();
            });
            JButton viewAttendanceReports = new JButton("View Attendance Reports");
            viewAttendanceReports.addActionListener(e-> {
                mainFrame.setVisible(false);
                mainFrame.dispose();
                try {
                    new ViewAttendanceReportsMenu(currentClass);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            this.add(editClassesButton);
            this.add(viewAttendanceReports);

            mainFrame.add(this);
        }
    }

    class MiddleBar extends JPanel implements Serializable
    {
        MiddleBar() {
            JComboBox<String> selectClass = getSelectClassComboBox();
            selectClass.setSelectedIndex(getFlockNames().length - 1);

            JButton newAttendanceReport = new JButton("New Attendance Report");
            newAttendanceReport.addActionListener(e -> {
                try {
                    new AttendanceMenu(currentClass);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            JButton addStudent = getAddStudentButton();

            JButton addFlock = getAddFlockJButton();

            this.add(selectClass);
            this.add(newAttendanceReport);
            this.add(addStudent);
            this.add(addFlock);

            mainFrame.add(this);
        }

        private JButton getAddFlockJButton() {
            JButton addFlock = new JButton("Add Class");
            addFlock.addActionListener(e -> {
                String name = JOptionPane.showInputDialog("Class name: ");
                if (name != null) {
                    if (!name.isEmpty()) {
                        try {
                            Flock flock = new Flock(name);
                            mainFrame.remove(this);
                            mainFrame.add(new MiddleBar());

                            updateStudentRows();
                        } catch (IOException | ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                        mainFrame.pack();
                    }
                }
            });
            return addFlock;
        }

        private JComboBox<String> getSelectClassComboBox() {
            JComboBox<String> selectClass = new JComboBox<>(getFlockNames());
            selectClass.addActionListener(e -> {
                currentClass = flocks.get(selectClass.getSelectedIndex());
                updateStudentRows();
            });

            selectClass.addActionListener(e -> currentClass = flocks.get(selectClass.getSelectedIndex()));
            return selectClass;
        }

        private JButton getAddStudentButton() {
            JButton addStudent = new JButton("Add Student");
            addStudent.addActionListener(e -> {
                String studentName = JOptionPane.showInputDialog("Student name: ");
                if (studentName != null) {
                    if (!studentName.isEmpty()) {
                        try {
                            Student newStudent = new Student(currentClass, studentName);
                            updateStudentRows();
                        } catch (ClassNotFoundException | IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        mainFrame.pack();
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
            this.setBackground(Color.lightGray);

            JLabel name = new JLabel(student.studentName);
            JButton unenroll = getUnenrollButton(student);
            JButton addGrade = new JButton("Add Grade");
            addGrade.addActionListener(e->
            {
                JPanel panel = new JPanel(new GridLayout(2, 2));
                panel.add(new JLabel("Assignment Name: "));
                JTextField nameField = new JTextField();
                panel.add(nameField);

                panel.add(new JLabel("Grade: "));
                JTextField gradeField = new JTextField();
                panel.add(gradeField);

                int result = JOptionPane.showConfirmDialog(null, panel, "Enter Assignment Details",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    String assignmentName = Objects.requireNonNull(nameField.getText());
                    try {
                        student.addGrade(assignmentName, Integer.parseInt(gradeField.getText()));
                    } catch (NumberFormatException | IOException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid input for Grade. Please enter a valid integer.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            JButton summary = new JButton("Summary");
            summary.addActionListener(e-> new SummaryWindow(student));

            this.add(name);
            this.add(unenroll);
            this.add(addGrade);
            this.add(summary);

            // add exclamation mark for bad performance as if statement here later
        }
    }

    public void updateStudentRows() {
        for (JPanel studentRow : studentPanels) {
            mainFrame.remove(studentRow);
        }
        studentPanels.clear();
        for (int i = 0; i < currentClass.students.size(); i++) {
            StudentRow studentRow = new StudentRow(currentClass.students.get(i));
            studentPanels.add(studentRow);
            mainFrame.add(studentRow);
        }
        mainFrame.repaint();
        mainFrame.pack();
    }

        private JButton getUnenrollButton(Student student) {
            JButton unenroll = new JButton("Unenroll");
            unenroll.addActionListener(e -> {
                currentClass.students.remove(currentClass.findStudentIndexByName(student.studentName));
                updateStudentRows();
            });
            return unenroll;
        }
}
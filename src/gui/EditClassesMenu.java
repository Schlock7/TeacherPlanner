package gui;

import misc.Types;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;


public class EditClassesMenu extends Types
{
    transient JFrame mainFrame = new JFrame();
    static ArrayList<JPanel> flockRows = new ArrayList<>();

    EditClassesMenu() {
        mainFrame.setResizable(false);
        mainFrame.setLayout(new GridLayout(0, 1, 20, 5));
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
        mainFrame.pack();
        new TopBar();
        updateFlockRows();
    }

    public class TopBar extends JPanel
    {
        TopBar()
        {
            JButton analyticsButton = new JButton("Analytics");
            analyticsButton.addActionListener(e -> {
                // new analyticsMenu();
            });

            JButton backToMainMenu = new JButton("Back to Main Menu");
            backToMainMenu.addActionListener(e -> {
                try {
                    new MainMenu();
                } catch (IOException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                mainFrame.setVisible(false);
                mainFrame.dispose();
            });
            JButton searchStudentButton = new JButton("Search Student");
            searchStudentButton.addActionListener(e -> {
                String studentName = JOptionPane.showInputDialog("Student name: ");
                // new studentSummary(studentName);
            });

            this.add(analyticsButton);
            this.add(backToMainMenu);
            this.add(searchStudentButton);

            mainFrame.add(this);
        }
    }

    public void updateFlockRows()
    {
        for (JPanel FlockRow : flockRows)
        {
            mainFrame.remove(FlockRow);
        }
        flockRows.clear();
        for (Flock flock : flocks)
        {
            FlockRow flockRow = new FlockRow(flock);
            flockRows.add(flockRow);
            mainFrame.add(flockRow);
        }
        mainFrame.repaint();
        mainFrame.pack();
    }

    class FlockRow extends JPanel
    {
        FlockRow(Flock flock) {

            this.setBackground(Color.lightGray);

            JLabel classNameLabel = new JLabel(flock.name);

            JButton addStudentButton = new JButton("Add Student");
            addStudentButton.addActionListener(e -> {
                String studentName = JOptionPane.showInputDialog("Student name: ");
                if (!studentName.isEmpty()) {
                    try {
                        Student newStudent = new Student(flock, studentName);
                    } catch (ClassNotFoundException | IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });

            JButton deleteButton = new JButton("Delete");
            deleteButton.setForeground(Color.RED);
            // deleteButton.setOpaque(true);
            deleteButton.addActionListener(e -> {
                int input = JOptionPane.showConfirmDialog(null,
                        "This will permanently delete the class " + flock.name,
                        "Confirm class delete", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                System.out.println(input);
                if (input == 0) {
                    try {
                        flocks.remove(flock);
                        writeFlocksFile();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    updateFlockRows();
                }
            });

            JButton renameButton = new JButton("Rename");
            renameButton.addActionListener(e -> {
                String flockName = JOptionPane.showInputDialog("Class name: ");
                if (!flockName.isEmpty()) {
                    try {
                        flock.name = flockName;
                        writeFlocksFile();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                updateFlockRows();
            });

            this.add(classNameLabel);
            this.add(renameButton);
            this.add(addStudentButton);
            this.add(deleteButton);
        }
    }
}
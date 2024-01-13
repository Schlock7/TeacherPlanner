package gui;

import misc.Types;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;


public class EditClassesMenu extends Types
{
    transient JFrame MainFrame = new JFrame();
    static ArrayList<JPanel> flockRows = new ArrayList<>();

    EditClassesMenu() {
        MainFrame.setResizable(false);
        MainFrame.setLayout(new GridLayout(0, 1, 20, 5));
        MainFrame.setLocationRelativeTo(null);
        MainFrame.setVisible(true);
        MainFrame.pack();
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
                MainFrame.setVisible(false);
                MainFrame.dispose();
            });
            JButton searchStudentButton = new JButton("Search Student");
            searchStudentButton.addActionListener(e -> {
                String studentName = JOptionPane.showInputDialog("Student name: ");
                // new studentSummary(studentName);
            });

            this.add(analyticsButton);
            this.add(backToMainMenu);
            this.add(searchStudentButton);

            MainFrame.add(this);
        }
    }

    public void updateFlockRows()
    {
        for (JPanel FlockRow : flockRows)
        {
            MainFrame.remove(FlockRow);
        }
        flockRows.clear();
        for (Flock flock : flocks)
        {
            FlockRow flockRow = new FlockRow(flock);
            flockRows.add(flockRow);
            MainFrame.add(flockRow);
        }
        MainFrame.repaint();
        MainFrame.pack();
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

/* You see I think the advantage of reporting Mr Trembley to be a creep is he doesn't want to talk to you so bad that he literally just lets you do
anything */
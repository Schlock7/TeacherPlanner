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

    }

    public class TopBar extends JPanel
    {
        TopBar()
        {
            JButton analyticsButton = new JButton("Analytics");
            JButton backToMainMenu = new JButton("Analytics");
            JButton searchStudentButton = new JButton("Analytics");

            this.add(analyticsButton);
            this.add(backToMainMenu);
            this.add(searchStudentButton);

            this.add(MainFrame);
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

            this.setBackground(Color.gray);

            JButton addStudentButton = new JButton("Add Student");
            addStudentButton.addActionListener(e -> {
                String studentName = JOptionPane.showInputDialog("Class name: ");
                if (!studentName.isEmpty()) {
                    try {
                        Student newStudent = new Student(flock, studentName);
                    } catch (ClassNotFoundException | IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });

            JButton deleteButton = new JButton("Delete");
            deleteButton.setBackground(Color.RED);
            deleteButton.setOpaque(true);

            JButton renameButton = new JButton("Rename");
            renameButton.addActionListener(e -> {
                String flockName = JOptionPane.showInputDialog("Class name: ");
                if (!flockName.isEmpty()) {
                    try {
                        Student newStudent = new Student(flock, flockName);
                    } catch (ClassNotFoundException | IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });

            this.add(renameButton);
            this.add(addStudentButton);
            this.add(deleteButton);


            };
        }
    }
}

/* You see I think the advantage of reporting Mr Trembley to be a creep is he doesn't want to talk to you so bad that he literally just lets you do
anything */
package Planner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class AttendanceMenu {

    AttendanceMenu(){
        ArrayList<String> presentStudents = new ArrayList<>();
    }

    class AttendenceRow extends JPanel {
        AttendenceRow(String studentName, String currentclass) {

            this.setBackground(Color.lightGray);
            JLabel name = new JLabel(studentName);

            JCheckBox present = new JCheckBox("Present?");
            present.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                }
            });

            JButton addGrade = new JButton("Add Grade");

            JButton summary = new JButton("Summary");

            this.add(name);
            this.add(present);

            // add exclamation mark for bad performance as if statement here later
        }
    }
}

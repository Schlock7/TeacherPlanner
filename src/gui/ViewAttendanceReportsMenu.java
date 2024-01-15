package gui;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import misc.Types;
class ViewAttendanceReportsMenu extends Types implements Serializable {
    private static Flock currentClass;
    transient JFrame mainFrame = new JFrame();
    static ArrayList<JPanel> attendanceReportRows = new ArrayList<>();

    ViewAttendanceReportsMenu(Flock oldCurrentClass) throws IOException {
        currentClass = oldCurrentClass;
        System.out.println();
        mainFrame.setResizable(false);
        mainFrame.setLayout(new GridLayout(0, 1, 20, 5));
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
        new TopBar();
        updateAttendanceReportRows();
        mainFrame.pack();
    }

    class TopBar extends JPanel {
        TopBar() {
            JComboBox<String> selectClass = new JComboBox<>(getFlockNames());
            selectClass.addActionListener(e -> {
                currentClass = flocks.get(selectClass.getSelectedIndex());
                try {
                    updateAttendanceReportRows();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
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

            this.add(selectClass);
            this.add(backToMainMenu);

            mainFrame.add(this);
        }
    }

    class AttendanceReportRow extends JPanel {
        AttendanceReportRow(AttendanceReport report) {
            this.setBackground(new Color(130, 192, 242)); // set background color to custom light blue (Color.blue is dogshit)
            JLabel title = new JLabel(report.title);

            JButton viewReport = new JButton("View");
            viewReport.addActionListener(e-> {
                new ViewReportWindow(report);
            });

            JButton deleteReport = new JButton("delete");
            deleteReport.setForeground(Color.RED);
            deleteReport.addActionListener(e->{
                int input = JOptionPane.showConfirmDialog(null,
                        "This will permanently delete this attendance report",
                        "Confirm report delete", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                if (input == 0) {
                    try {
                        attendanceReportRows.remove(report);
                        currentClass.attendanceReports.remove(report);
                        writeFlocksFile();
                        try {
                            updateAttendanceReportRows();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });

            attendanceReportRows.add(this);

            this.add(title);
            this.add(viewReport);
            this.add(deleteReport);

            mainFrame.add(this);
        }

    }
    public void  updateAttendanceReportRows() throws IOException {
        for (JPanel attendanceReportRow : attendanceReportRows) {
            mainFrame.remove(attendanceReportRow);
        }
        attendanceReportRows.clear();
        for (AttendanceReport attendanceReport : currentClass.attendanceReports) {
            mainFrame.add(new AttendanceReportRow(attendanceReport));
        }
        mainFrame.repaint();
        mainFrame.pack();
    }
}


package misc;

import misc.Types;
import misc.Calculator;

import javax.swing.*;
import java.awt.*;

import static misc.Calculator.getAttendanceDecimal;

public class PieChart extends JPanel {

    private final Types.Student student;

    public PieChart(Types.Student student) {
        this.student = student;
        JPanel pieChartPanel = createPieChartPanel(student, 100, getAttendanceDecimal(student));
        pieChartPanel.setPreferredSize(new Dimension(200,200));

        add(pieChartPanel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        double attendanceDecimal = getAttendanceDecimal(student);

        JPanel pieChartPanel = createPieChartPanel(student, 100, attendanceDecimal);

        add(pieChartPanel);
    }

    public JPanel createPieChartPanel(Types.Student student, int r, double percentage) {
        return new PieChartPanel(student, percentage);
    }

    public static class PieChartPanel extends JPanel {
        private Types.Student localStudent;
        private final double percentage;

        public PieChartPanel(Types.Student student, double percentage) {
            this.localStudent = student;
            this.percentage = percentage;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            int r = 200;
            int startAngle = 0;
            int extent = (int) (percentage * 360);

            g2d.setColor(Color.BLUE);
            g2d.fillArc(0, 0, r, r, startAngle, extent);

            g2d.setColor(Color.BLACK);
            g2d.drawOval(0, 0, r, r);
        }
    }
}
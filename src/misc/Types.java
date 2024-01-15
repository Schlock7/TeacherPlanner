package misc;

import gui.Login;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public abstract class Types {
    public static ArrayList<Flock> flocks = new ArrayList<>();
    static File flocksFile = new File("data/flocks.txt");

    public static void initialize() throws IOException {
        boolean b = (new File("data/flocks.txt").exists());
        BufferedReader br = new BufferedReader(new FileReader("data/flocks.txt"));
        if (!b) {
            File flocksFile = new File("data/flocks.txt");
        }
        else if (!(br.readLine() == null)) {
            flocks = readFlocksFile();
        }

        new Login();
    }



    public static void writeFlocksFile() throws IOException {
        FileOutputStream outputStream = new FileOutputStream(flocksFile);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(flocks);
    }

    public static ArrayList<Flock> readFlocksFile() throws IOException {
        FileInputStream inputStream = new FileInputStream(flocksFile);
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

        try {
            Object readObject = objectInputStream.readObject();
            if (readObject instanceof ArrayList<?> readFlocksRaw) {
                if (readFlocksRaw.isEmpty() || readFlocksRaw.get(0) instanceof Flock) {
                    @SuppressWarnings("unchecked")
                    ArrayList<Flock> readFlocks = (ArrayList<Flock>) readFlocksRaw;
                    return readFlocks;
                }
            }
        } catch (ClassCastException | ClassNotFoundException e) {
            throw new RuntimeException();
        }

        return new ArrayList<>(); // Return an empty list if unable to read or mismatched data
    }

    public String[] getFlockNames() {
        String[] flockNames = new String[flocks.size()];

        for (int i = 0; i < flocks.size(); i++) {
            flockNames[i] = flocks.get(i).name;
        }

        return flockNames;
    }

    public class Student implements Serializable
    {
        public HashMap<String, Integer> grades = new HashMap<>();
        public HashMap<Calendar, Boolean> attendance = new HashMap<>();
        public HashMap<Calendar, String> excuse = new HashMap<>();
        public String studentName;

        public Student(Flock flock, String name) throws ClassNotFoundException, IOException
        {
            flock.students.add(this);
            studentName = name;
            writeFlocksFile();
        }

        public void addAttendance(Calendar date, Boolean present) throws IOException
        {
            attendance.put(date, present);
            writeFlocksFile();
        }

        public void addExcuse(Calendar date, String e) throws IOException {
            excuse.put(date, e);
            writeFlocksFile();
        }

        public void addGrade(String task, int grade) throws IOException
        {
            grades.put(task, grade);
            writeFlocksFile();
        }
    }

    public static class AttendanceReport implements Serializable
    {
        public String title;
        public Flock reportFlock;
        public Calendar reportDate;
        public AttendanceReport(Flock flock, Calendar date) throws IOException {
            reportFlock = flock;
            reportDate = date;
            flock.attendanceReports.add(this);
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy");
            title = "Attendance report from " + sdf.format(date.getTime());
            writeFlocksFile();
        }
    }


    public class Flock implements Serializable
    {
        public String name;
        public ArrayList<Student> students = new ArrayList<>();
        public ArrayList<AttendanceReport> attendanceReports = new ArrayList<>();
        public Flock(String flockName) throws IOException, ClassNotFoundException {
            name = flockName;
            ArrayList<String> students = new ArrayList<>();
            flocks.add(this);
            writeFlocksFile();
        }

        public int findStudentIndexByName(String targetStudentName) {
            for (int i = 0; i < students.size(); i++) {
                Student student = students.get(i);
                if (student.studentName.equals(targetStudentName)) {
                    return i; // Return the index if the studentName matches
                }
            }
            return -1; // Return -1 if the student is not found
        }
    }

}
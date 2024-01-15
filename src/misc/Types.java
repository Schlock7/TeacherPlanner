package misc;

import gui.Login;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public abstract class Types {
    public static ArrayList<Flock> flocks = new ArrayList<>();
    static String flocksFilePath = "data/flocks.txt";  // Classpath-relative path

    public static void initialize() {
        flocks = readFlocksFile();
        new Login();
    }

    public static void writeFlocksFile() throws IOException {
        String filePath;

        if (isRunningFromJar()) {
            String externalDir = System.getProperty("user.home") + File.separator + "TeacherPlannerData";
            Files.createDirectories(Paths.get(externalDir));
            filePath = externalDir + File.separator + "flocks.txt";
        } else {
            filePath = Objects.requireNonNull(Types.class.getClassLoader().getResource(flocksFilePath)).getFile();
        }

        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                new FileOutputStream(filePath))) {

            objectOutputStream.writeObject(flocks);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Flock> readFlocksFile() {
        String filePath;

        if (isRunningFromJar()) {
            String externalDir = System.getProperty("user.home") + File.separator + "TeacherPlannerData";
            filePath = externalDir + File.separator + "flocks.txt";
        } else {
            filePath = Objects.requireNonNull(Types.class.getClassLoader().getResource(flocksFilePath)).getFile();
        }

        try (ObjectInputStream objectInputStream = new ObjectInputStream(
                new FileInputStream(filePath))) {

            Object readObject = objectInputStream.readObject();

            if (readObject instanceof @SuppressWarnings("unchecked")ArrayList<?> readFlocksRaw) {

                if (!readFlocksRaw.isEmpty() && readFlocksRaw.get(0) instanceof Flock) {
                    @SuppressWarnings("unchecked")
                    ArrayList<Flock> readFlocks = (ArrayList<Flock>) readFlocksRaw;
                    return readFlocks;
                }
            }
        } catch (EOFException eof) {
            System.out.println("File is empty");
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    private static boolean isRunningFromJar() {
        String className = Types.class.getName().replace('.', '/');
        String classJar = Objects.requireNonNull(Types.class.getResource("/" + className + ".class")).toString();
        return classJar.startsWith("jar:");
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
                    return i;
                }
            }
            return -1;
        }
    }

}
package misc;

import gui.Login;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Types {
    public ArrayList<Flock> flocks = new ArrayList<>();
    File flocksFile = new File("data/flocks.txt");

    public String[] GetFlockNames() {
        String[] flockNames = new String[flocks.size()];
        for (int i = 0; i < flocks.size(); i++) {
            flockNames[i] = Flock.name;
        }
        return flockNames;
    }

    public static void initialize()
    {
        File flocksFile = new File("data/flocks.txt");
        new Login();
    }
    public class Student implements Serializable
    {
        HashMap<String, Integer> grades = new HashMap<>();
        HashMap<Calendar, Boolean> attendance = new HashMap<>();
        public String studentName;

        public Student(Flock flock, String name) throws ClassNotFoundException, IOException{
            flock.students.add(this);
            studentName = name;
            FileOutputStream outputStream = new FileOutputStream(flocksFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(flocks);
        }

        public void addAttendance (Calendar date, Boolean present) throws IOException
        {
            attendance.put(date, present);
            FileOutputStream outputStream = new FileOutputStream(flocksFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(flocks);
        }

        public void addGrade (String task, int grade) throws IOException
        {
            grades.put(task, grade);
            FileOutputStream outputStream = new FileOutputStream(flocksFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(flocks);
        }
    }


    public class Flock implements Serializable
    {
        public static String name;
        public ArrayList<Student> students = new ArrayList<>();
        public Flock(String flockName) throws IOException
        {
            String name = flockName;
            ArrayList<String> students = new ArrayList<>();
            flocks.add(this);
            FileOutputStream outputStream = new FileOutputStream(flocksFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(this);

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
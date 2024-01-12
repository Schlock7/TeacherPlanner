package misc;

import gui.Login;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public abstract class Types {
    public static ArrayList<Flock> flocks = new ArrayList<>();
    static File flocksFile = new File("data/flocks.txt");

    public static void initialize() throws IOException, ClassNotFoundException {
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

    public static ArrayList<Flock> readFlocksFile() throws IOException, ClassNotFoundException {
        FileInputStream inputStream = new FileInputStream(flocksFile);
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

        try {
            Object readObject = objectInputStream.readObject();
            System.out.println("Type of readObject: " + readObject.getClass().getName());

            if (readObject instanceof ArrayList<?> readFlocksRaw) {
                if (readFlocksRaw.isEmpty() || readFlocksRaw.get(0) instanceof Flock) {
                    @SuppressWarnings("unchecked")
                    ArrayList<Flock> readFlocks = (ArrayList<Flock>) readFlocksRaw;
                    return readFlocks;
                }
            }
        } catch (ClassCastException | ClassNotFoundException e) {
            e.printStackTrace();
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
        public String name;
        public ArrayList<Student> students = new ArrayList<>();
        public Flock(String flockName) throws IOException, ClassNotFoundException {
            name = flockName;
            ArrayList<String> students = new ArrayList<>();
            flocks.add(this);
            FileOutputStream outputStream = new FileOutputStream(flocksFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(flocks);

            FileInputStream inputStream = new FileInputStream(flocksFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            Object readObject = objectInputStream.readObject();
            System.out.println("Type of readObject: " + readObject.getClass().getName());
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
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Student {
    private ArrayList<Aclass> transcript;       //an Arraylist for the transcript
    private String studentID, name, securityNo, birthDate, phoneNo, address;

    //the constructor
    public Student(String stuID, String nam, String secNo, String bDate, String pNo, String adrs, ArrayList<Aclass> tScript) {
        studentID = stuID;
        name = nam;
        securityNo = secNo;
        birthDate = bDate;
        phoneNo = pNo;
        address = adrs;
        transcript = tScript;
    }

    //deep copy method
    public Student deepCopy() {
        Student clone = new Student(studentID, name, securityNo, birthDate, phoneNo, address, transcript);
        return clone;
    }

    //add a class method
    public void add(Aclass aClass) {
        transcript.add(aClass);
    }

    //drop a class method
    public void drop(String className) {
        for (int i = 0; i < transcript.size(); i++)            //make sure for the class to be in the student has taken or is taking the class
        {
            Aclass oneClass = transcript.get(i);        //get that class's name and assign it to an Aclass's object
            if (oneClass.equals(className))               //if the class is the same class that needs to be dropped
            {
                if (oneClass.getLetterGrade() != 'X')    //if it doesn't have the grade of X, it's complete
                    System.out.print("The class is complete and cannot be dropped.");
                else
                    transcript.remove(className);       //remove the class
            }
        }
    }

    //get Student ID number
    public String getStudentID() {
        return studentID;
    }

    //get student's name
    public String getName() {
        return name;
    }

    public int compareTo(String targetKey) {
        return studentID.compareTo(targetKey);
    }

    public ArrayList<Aclass> getTranscript() {
        return transcript;
    }

    //toString method
    public String toString() {
        String str = "\nThe student ID is: " + studentID + " \nThe student's name is: " + name +
                "\nThe student's social security number is: " + securityNo + "\nThe student's birth date is: " + birthDate +
                "\nThe student's phone number is: " + phoneNo + "\nThe student's address is: " + address
                +"\n-----------\nThe Transcript:";

        for (int i = 0; i < transcript.size(); i++) {
            str += "\n" + transcript.get(i);
        }
        return str;
    }
}


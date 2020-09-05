import com.sun.source.tree.BinaryTree;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class SchoolSystem {
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);  //a Scanner object to read from keyboard
        BinarySearchTree bTree = new BinarySearchTree();    //to call the Tree's operations
        Student writeStudent = null;    //to call the Student class
        ArrayList<String> infoArray = new ArrayList<>();    //to add the student info
        ArrayList<String> classArray = new ArrayList<>();   //to add classes
        ArrayList<Aclass> classList = null;    //to add classes from the CSV file
        String studentID = "";
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");    //to print actual date
        Date date = new Date();                                                    //to print actual date
        try {
            Scanner scanInfo = new Scanner(new File("studentInformation.csv"));
            Scanner scanGrade = new Scanner(new File("studentGrade.csv"));

            //read the students' information from the CSV file
            while (scanInfo.hasNextLine()) {
                //System.out.println(scanInfo.nextLine());
                String[] token = scanInfo.nextLine().split(",");    //split the student's information
                String ID = token[0];
                String name = token[1] + ", " + token[2];
                String SS = token[3];
                String birthday = token[4];
                String phone = token[5];
                String address = token[6];

                //Read the classes from the CSV file
                classList = new ArrayList<>();
                String line = scanGrade.nextLine();      //read line of classes of a student
                String[] array = line.split(",");
                for (int i = 1; i < array.length; i += 2) { //start at 1 to avoid student ID
                                                            //and jump 2 places at a time to use both
                    Aclass oneClass = new Aclass(array[i], array[i + 1].charAt(0));
                    classList.add(oneClass);                //add class with its grade to the class list
                }
                //System.out.println(Arrays.toString(new ArrayList[]{classList}));
                bTree.insert(new Student(ID, name, SS, birthday, phone, address, classList));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //switch-case for the options
        int option;
        do{
            System.out.println("\nWhat would you like to do?");
            System.out.println("1. Add One New Student\n" +
                    "2. Remove one student\n" +
                    "3. Find a student by ID\n" +
                    "4. Add a class for one student\n" +
                    "5. Drop a class for one student\n" +
                    "6. Print the list of students in one class\n" +
                    "7. Print out the transcript of one student\n" +
                    "8. Show All\n" + "9. Exit");
            option = keyboard.nextInt();    //read the option from keyboard
            keyboard.nextLine();            //this is for the bug in Java
            switch (option) {
                case 1: //Add a new student
                    System.out.print("Enter Student ID (7 digits): ");
                    String ID = keyboard.nextLine();
                    infoArray.add(0, ID);
                    System.out.print("Enter Last Name: ");
                    String lName = keyboard.nextLine();
                    infoArray.add(1, lName);
                    System.out.print("Enter First Name: ");
                    String fName = keyboard.nextLine();
                    infoArray.add(2, fName);
                    String name = lName + ", " + fName; //assign last name and first name to name
                    System.out.print("Enter Social Security Number: ");
                    String SS = keyboard.nextLine();
                    infoArray.add(3, SS);
                    System.out.print("Enter Birth Date: ");
                    String birthDate = keyboard.nextLine();
                    infoArray.add(4, birthDate);
                    System.out.print("Enter Phone Number: ");
                    String phone = keyboard.nextLine();
                    infoArray.add(5, phone);
                    System.out.print("Enter Address: ");
                    String address = keyboard.nextLine();
                    infoArray.add(6, address);
                    writeStudent = new Student(ID, name, SS, birthDate, phone, address, null);
                    bTree.insert(writeStudent);

                    writeStudent(infoArray);    //write the information to files
                    break;

                case 2: //remove one student
                    System.out.print("Enter a student ID to be removed: ");
                    String dltID = keyboard.nextLine();
                    if (bTree.delete(dltID))
                        System.out.print("Student was deleted.");
                    else System.out.print("Student was not found.");
                    break;

                case 3: //find a student by ID (Fetch)
                    System.out.print("Enter a student ID to be shown: ");
                    String fetchID = keyboard.nextLine();
                    Student fetchStud = bTree.fetch(fetchID);
                    if (fetchStud == null)
                        System.out.print("The student with ID number: " + fetchID + " was not found.");
                    else System.out.println("Here's everything about the student:\n" + fetchStud.toString());
                    break;

                case 4: //Add a class for one student
                    System.out.print("Enter a student ID to add a class to: ");
                    studentID = keyboard.nextLine();  //read the student ID from keyboard
                    Student addCls = bTree.fetch(studentID); //fetch that student node
                    if (addCls == null) System.out.print("The student cannot be found.");    //if it doesn't exist
                    else {  //if found
                        System.out.print("\nEnter the class name by entering semester(2characters) year(4digits) – 4 letters of class 4 digits of class: ");
                        String className = keyboard.nextLine();
                        Aclass addClass = new Aclass(className.toUpperCase(), 'X'); //make an Aclass object
                        addCls.add(addClass);  //add the class
                        System.out.print(addCls.toString());
                        classArray.add(className);//add the class to the array of classes

                        //write the class to file
                        writeClass(studentID, classArray);      //write the class
                    }
                    break;

                case 5: //drop a class
                    System.out.print("\nEnter a student ID to drop a class from: ");
                    String dropID = keyboard.nextLine();
                    Student dropStudent = bTree.fetch(dropID);
                    if (dropStudent == null) System.out.print("\nThe student with ID: " + dropID + " wasn't found.");
                    else {
                        System.out.print("\nEnter the class name to be dropped by entering semester(2characters) year(4digits) – 4 letter of class 4 digits of class: ");
                        String dropClass = keyboard.nextLine();
                        dropStudent.drop(dropClass);
                        System.out.println("\nDropped successfully.");
                        System.out.println(dropStudent.toString());
                    }
                    break;

                case 6: //Print the list of students in one class
                    System.out.print("\nEnter the class name (2character semester 4 digit year – 4 letters and 4 digits for class): ");  //by entering semester(2characters) year(4digits) – 4 letter of class 4 digits of class: ");
                    String list = keyboard.nextLine();
                    System.out.println("className = " + list);
                    bTree.classList(list);
                    break;

                case 7: //Print out the transcript of one student
                    System.out.print("\nEnter a student ID to print the tranScript of: ");
                    String tranID = keyboard.nextLine();        //read the student ID from the keyboard
                    Student transIDNode = bTree.fetch(tranID);
                    if (transIDNode == null) System.out.print("\nThe student with ID: " + tranID + " wasn't found.");
                    else {
                        System.out.println("\nSCHOOL ABC – TRANSCRIPT\n" +
                                "-----------------------------\n" +
                                "Student:\t" + transIDNode.getName() + "\n" +
                                "Student ID:\t" + transIDNode.getStudentID() +
                                "\nDate:\t\t" + formatter.format(date) +
                                "\n-----------------------------");
                        System.out.println(Arrays.toString(transIDNode.getTranscript().toArray()).
                                replace("[", " ").
                                replace("]", "\r").
                                replace(",", ""));
                    }
                    break;

                case 8: //showAll
                    bTree.showAll();    //simply call this method to take care of it
                    break;

                case 9: //exit
                    //write the information of students
                    writeStudent(infoArray);

                    //write the student classes
                    writeClass(studentID, classArray);

                    System.exit(0);
            }//end of switch case
        }while(option != 0);
    }

    //write the information of students to file
    public static void writeStudent(ArrayList<String> infoArray){
        try {
            BufferedWriter writeInfo = new BufferedWriter(new FileWriter("studentInformation.csv", true));
            PrintWriter appendInfo = new PrintWriter(writeInfo);
            //assign the information of Student node to the array to append it to file
            appendInfo.print("\n");
            for (String element : infoArray) {
                appendInfo.print(element + ",");    //append the node's information to file
            }
            writeInfo.close();          //close the file
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //write the student classes to file
    public static void writeClass(String studentID, ArrayList<String> classArray){
        try {
            //write the classes of that student
            BufferedWriter writeClasses = new BufferedWriter(new FileWriter("studentGrade.csv", true));
            PrintWriter appendClasses = new PrintWriter(writeClasses);
            //add the classes to the studentGrade file
            appendClasses.print("\n" + studentID + ",");
            for (String element : classArray) {
                appendClasses.print(element + "," + 'X');
            }
            //close the file
            appendClasses.close();   //close the classes file
        } catch (IOException e) {
            e.printStackTrace();    //nailed it
        }
    }
}

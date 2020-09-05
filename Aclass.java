public class Aclass {
    private String clasName;
    private char letterGrade;

    //no-arg constructor
    public Aclass (){
        clasName=null;
        letterGrade='\0';
    }

    //constructor
    public Aclass(String cName, char ltrGrade){
        clasName=cName;
        letterGrade=ltrGrade;
    }

    //the getters
    public String getClasName(){return clasName;}

    public char getLetterGrade(){return letterGrade;}

    //the toString method
    public String toString()
    {
        if(letterGrade == 'X')
            return clasName + " : The Class is Still in Progress.";
        else
            return clasName + " : " + letterGrade;
    }
}

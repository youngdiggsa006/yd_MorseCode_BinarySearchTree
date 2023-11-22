/**
 * @author Amaris Young-Diggs
 * @project Project 6: Morse Code Implentation
 * @class CMSC 256 Section 001
 * @description: this class is the Morse Character which is the letter and string of the
 * morse code. It contains getter and setters to obtain the repsective parameters.
 * It has a compareTo method which sorts based on the morse code, a dot is less than a dash.
 * Additionally, this class contains parameterized and no arg constructors, along with
 * an equals method, toString, and hashCode
 */
package cmsc256;

public class MorseCharacter implements Comparable<MorseCharacter> {
    private String codeRep; //the morse code string
    private char letter; //the letter that the morse code is

    /**
     * accessor method for the code
     * @return the code
     */
    public String getCode() {
        return codeRep;
    }

    /**
     * accessor method for the char letter
     * @return the letter the morse code represent
     */
    public char getLetter() { return letter; }

    /**
     * no arg constructor for Morse Character
     * it sets the string to an empty string
     * sets the letter to an empty character
     */
    public MorseCharacter() {
        this.codeRep = " ";
        this.letter = '\0';
    }

    /**
     * parameterized constructor for Morse Character
     * @param letter the char letter to be taken in
     * @param codeRepresentation the morse code string
     */
    public MorseCharacter(char letter, String codeRepresentation){
        this.letter = letter;
        this.codeRep = codeRepresentation;
    }

    /**
     * method that verifies the equals method
     * @return the hashcode of the object
     */
    @Override
    public int hashCode() {
        return codeRep.hashCode() * String.valueOf(letter).hashCode();
    }

    /**
     * checks to see if the object take in is an instance of Morse Character
     * checks to see if the two objects are equal
     * @param obj the object being taken in
     * @return true or false
     */
    @Override
    public boolean equals(Object obj)
    {
        if(obj == null) return false;
        if(!(obj instanceof MorseCharacter)) return false;
        MorseCharacter other = (MorseCharacter) obj;
        if(this == other) return true;
        return this.hashCode() == other.hashCode();
    }

    /**
     * compares the string code of each character
     * @param o the object to be compared.
     * @return an integer
     */
    @Override
    public int compareTo(MorseCharacter o)
    {
        if(o == null){ throw new IllegalArgumentException(); } //if the object is null then throw exception
        int i = 0, result = 0; //initialize a result variable
        String tCodeStr = this.getCode(); //get the string of this
        String oCodeStr = o.getCode(); //get the string of the Morse Character object taken in

        while(result == 0 && i < tCodeStr.length() && i < oCodeStr.length())
        {
            char thisCodeChar = tCodeStr.charAt(i);
            char otherCodeChar = oCodeStr.charAt(i);
            if(thisCodeChar != otherCodeChar)
            {
                if(thisCodeChar == '.' && otherCodeChar == '-'){ result = -1; }
                else{ result = 1; }
            }
            i++;
        }
        if(result != 0 || tCodeStr.length() == oCodeStr.length()){ return result; }
        else{ result = tCodeStr.length() - oCodeStr.length(); }
        return result;
    }

    /**
     * to String method that returns only the letter of the Morse Character Ojbect
     * @return
     */
    @Override
    public String toString() { return String.valueOf(letter); }

    /**
     * main method to test the getter methods and isEqual method
     * @param args
     */
    public static void main(String[] args) {
        MorseCharacter A = new MorseCharacter('A', ".-");
        System.out.println("Expect A: " + A.getLetter());
        System.out.println("Expect .-: " + A.getCode());
        boolean isEqual = A.equals(new MorseCharacter('A', ".-"));
        System.out.println("Expect true: " + isEqual);
        MorseCharacter I = new MorseCharacter('I', "..");
        System.out.println("Expect true: " + (I.compareTo(A) < 0));
        System.out.println("Expect false: " + (A.compareTo(I) < 0));
    }
}

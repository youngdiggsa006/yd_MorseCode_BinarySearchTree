package cmsc256;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MorseCharacterTest {

    @Test
    void getCode() {
        MorseCharacter testMorseA = new MorseCharacter('A',".-");
        assertEquals(".-", testMorseA.getCode());
    }

    @Test
    void getLetter() {
        MorseCharacter testMorseA = new MorseCharacter('A',".-");
        assertEquals('A', testMorseA.getLetter());
    }

    @Test
    void testEquals() {
        MorseCharacter testMorseA = new MorseCharacter('A', ".-");
        boolean isEqual = testMorseA.equals(new MorseCharacter('A', ".-"));
        assertTrue(isEqual);
    }

    @Test
    void compareToTestForTrue() {
        MorseCharacter testMorseA = new MorseCharacter('A', ".-");
        MorseCharacter testMorseI = new MorseCharacter('I', "..");
        int answer = testMorseI.compareTo(testMorseA);
        assertTrue(answer < 0);
    }

    @Test
    void compareToTestForFalse(){
        MorseCharacter testMorseA = new MorseCharacter('A', ".-");
        MorseCharacter testMorseI = new MorseCharacter('I', "..");
        int answer = testMorseA.compareTo(testMorseI);
        assertFalse(answer < 0);
    }

    @Test
    void testToString() {
        MorseCharacter testMorseA = new MorseCharacter('A', ".-");
        assertEquals("A", testMorseA.toString());
    }
}
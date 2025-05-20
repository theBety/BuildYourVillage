package Tests;

import main.GamePanel;
import main.UtilityTool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class UtilityToolTest {
    GamePanel gp = new GamePanel();
    UtilityTool ut = new UtilityTool(gp);
    String word;

    @BeforeEach
    void setUp() {
        word = "PneumonoultramicroscopicsilicovolcanoconiosiS";
    }

    @Test
    void firstLetterUppercase() {
        String result = ut.firstLetterUppercase(word);
        assertEquals("Pneumonoultramicroscopicsilicovolcanoconiosis", result);
    }
}
package Tests;

import entity.Entity;
import entity.Player;
import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;
import object.ObjClay;
import object.ToolAxe;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    GamePanel gp = new GamePanel();
    KeyHandler keyHandler = new KeyHandler(gp);
    Player p = new Player(gp, keyHandler);
    UtilityTool ut = new UtilityTool(gp);
    Entity goodItem = new ObjClay(gp);
    Entity badItem = new ToolAxe(gp, 1);

    @Test
    void findItemInInventoryRight() {
        int result = ut.findItemInInventory("Picaxe");
        assertEquals(5, result);
    }

    @Test
    void findItemInInventoryWrong() {
        int result = ut.findItemInInventory("dog");
        assertEquals(-1, result);
    }

    @Test
    void canStackItemTrue() {
        assertTrue(p.canStackItem(goodItem));
    }

    @Test
    void canStackItemFalse() {
        assertFalse(p.canStackItem(badItem));
    }


}
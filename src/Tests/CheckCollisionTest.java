package Tests;

import entity.Entity;
import entity.Player;
import main.CheckCollision;
import main.GamePanel;
import main.KeyHandler;
import main.PlacingSetter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckCollisionTest {
    GamePanel gp = new GamePanel();
    KeyHandler keyH = new KeyHandler(gp);
    Entity p = new Player(gp, keyH);
    CheckCollision checkCollision = new CheckCollision(gp);
    PlacingSetter setter = new PlacingSetter(gp);

    @Test
    void checkObjectNoCollision() {
        int result = checkCollision.checkObject(p,true);
        assertEquals(-1, result);
    }
}
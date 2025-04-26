package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class ObjTrapdoor extends SuperObject{
    GamePanel gp;

    public ObjTrapdoor(GamePanel gp) {
        name = "trapdoor";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/trapdoor.png")));
            utilityTool.scaledImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        collisionObject = true;
    }
}

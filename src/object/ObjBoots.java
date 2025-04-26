package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class ObjBoots extends SuperObject{
    //pridat neco jako druh boty - rychlejsi/ lepsi boty
    GamePanel gp;

    public ObjBoots(GamePanel gp) {
        name = "boots";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/boots.png")));
            utilityTool.scaledImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        collisionObject = true;
    }
}

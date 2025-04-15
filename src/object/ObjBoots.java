package object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class ObjBoots extends SuperObject{
    //pridat neco jako druh boty - rychlejsi/ lepsi boty
    public ObjBoots() {
        name = "boots";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/boots.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        collisionObject = true;
    }
}

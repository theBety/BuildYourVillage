package object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class ObjTrapdoor extends SuperObject{
    public ObjTrapdoor() {
        name = "trapdoor";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/trapdoor.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

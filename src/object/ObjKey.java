package object;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class ObjKey extends SuperObject{

    public ObjKey() {
        name = "key";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/key.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}

package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UtilityTool {
    public BufferedImage scaledImage(BufferedImage imageToScale, int width, int height) {
        // Scaling images - saves time
        BufferedImage scaledImage = new BufferedImage(width, height, imageToScale.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(imageToScale, 0, 0, width, height, null);

        return scaledImage;
    }

    public String firstLetterUppercase(String word){
        if(word == null || word.isEmpty()){
            return "";
        }
        return word.substring(0,1).toUpperCase() + word.substring(1).toLowerCase();
    }

}

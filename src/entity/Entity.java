package entity;

import java.awt.image.BufferedImage;

public abstract class Entity {
    protected int x,y;
    protected int speed;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;
    public int spriteCounter = 0;
    public int spriteNumber = 1;
}

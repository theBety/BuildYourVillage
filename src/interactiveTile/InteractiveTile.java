package interactiveTile;

import entity.Entity;
import main.GamePanel;

public class InteractiveTile extends Entity {
    GamePanel gp;
    public boolean isDestructible = false;

    public InteractiveTile(GamePanel gp, int col, int row) {
        super(gp);
        this.gp = gp;
    }

    @Override
    public void update() {
        if (isInvincible) {
            invincibleCounter++;
            if (invincibleCounter > 20) {
                invincibleCounter = 0;
                isInvincible = false;
            }
        }
        collisionOn = false;
        gp.checkCollision.checkTile(this);
        gp.checkCollision.checkPlayer(this);
        gp.checkCollision.checkObject(this, false);
        if (!collisionOn) {
            switch (direction) {
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
            }
        }
    }

    public boolean isRequiredItem(Entity entity) {
        return false;
    }

    public void playSoundEffect() {
    }
}

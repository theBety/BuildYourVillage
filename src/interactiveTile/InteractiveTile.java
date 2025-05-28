package interactiveTile;

import entity.Entity;
import main.GamePanel;

public class InteractiveTile extends Entity {
    GamePanel gp;
    public boolean isDestructible = false;
    public Entity dropItem;

    public InteractiveTile(GamePanel gp) {
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

    /**
     * What tool is required for the tile to be destroyed
     * @param entity What tile is player trying to break
     * @return if the tool is correct or not
     */
    public boolean isRequiredItem(Entity entity) {
        return false;
    }

    public void playSoundEffect() {
    }
}

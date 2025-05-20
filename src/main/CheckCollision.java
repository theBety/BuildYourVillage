package main;

import entity.Entity;

public class CheckCollision {

    GamePanel gp;

    public CheckCollision(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tm.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gp.tm.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
                if (gp.tm.tile[tileNum1].collision || gp.tm.tile[tileNum2].collision) entity.collisionOn = true;

                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tm.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
                tileNum2 = gp.tm.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
                if (gp.tm.tile[tileNum1].collision || gp.tm.tile[tileNum2].collision) entity.collisionOn = true;

                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tm.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gp.tm.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
                if (gp.tm.tile[tileNum1].collision || gp.tm.tile[tileNum2].collision) entity.collisionOn = true;

                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tm.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
                tileNum2 = gp.tm.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
                if (gp.tm.tile[tileNum1].collision || gp.tm.tile[tileNum2].collision) entity.collisionOn = true;
                break;
        }
    }

    /**
     * Checks Collision between player and object on a map. If a collision is happening, the method returns index of
     * an object with which the player is in collision
     * @param entity with what entity the method is dealing
     * @param isPlayer if the entity is player or not
     * @return index in an array.
     */
    public int checkObject(Entity entity, boolean isPlayer) {
        int index = -1;

        for (int i = 0; i < gp.objects.length; i++) {
            if (gp.objects[gp.currentMap][i] != null) {
                entity.solidArea.x += entity.worldX;
                entity.solidArea.y += entity.worldY;

                gp.objects[gp.currentMap][i].solidArea.x += gp.objects[gp.currentMap][i].worldX;
                gp.objects[gp.currentMap][i].solidArea.y += gp.objects[gp.currentMap][i].worldY;

                switch (entity.direction) {
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(gp.objects[gp.currentMap][i].solidArea)) {
                            if (gp.objects[gp.currentMap][i].collisionObject) {
                                entity.collisionOn = true;
                            }
                            if (isPlayer) {
                                index = i;
                            }
                        }
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(gp.objects[gp.currentMap][i].solidArea)) {
                            if (gp.objects[gp.currentMap][i].collisionObject) {
                                entity.collisionOn = true;
                            }
                            if (isPlayer) {
                                index = i;
                            }
                        }
                        break;
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        //checks if two rectangles are in collision
                        if (entity.solidArea.intersects(gp.objects[gp.currentMap][i].solidArea)) {
                            if (gp.objects[gp.currentMap][i].collisionObject) {
                                entity.collisionOn = true;
                            }
                            if (isPlayer) {
                                index = i;
                            }
                        }
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        if (entity.solidArea.intersects(gp.objects[gp.currentMap][i].solidArea)) {
                            if (gp.objects[gp.currentMap][i].collisionObject) {
                                entity.collisionOn = true;
                            }
                            if (isPlayer) {
                                index = i;
                            }
                        }
                        break;
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.objects[gp.currentMap][i].solidArea.x = gp.objects[gp.currentMap][i].solidAreaDefaultX;
                gp.objects[gp.currentMap][i].solidArea.y = gp.objects[gp.currentMap][i].solidAreaDefaultY;
            }
        }
        return index;
    }

    /**
     * Method goes through Entities and compares theirs and players'
     * solid areas if they hit each other at any coordinate.
     * If not, method returns -1. If so, method returns index of npc that collided with player
     *
     * @param entity player
     * @param npc    array of NPCs
     * @return index if collision happened or not.
     */
    public int checkEntity(Entity entity, Entity[][] npc) {
        int index = -1;
        int counterOfEntities = 0;
        for (Entity n : npc[gp.currentMap]) {
            counterOfEntities++;
            if (n != null) {
                entity.solidArea.x += entity.worldX;
                entity.solidArea.y += entity.worldY;

                n.solidArea.x += n.worldX;
                n.solidArea.y += n.worldY;

                switch (entity.direction) {
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(n.solidArea)) {
                            entity.collisionOn = true;
                            index = counterOfEntities - 1;
                        }
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(n.solidArea)) {
                            entity.collisionOn = true;
                            index = counterOfEntities - 1;
                        }
                        break;
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        //checks if two rectangles are in collision
                        if (entity.solidArea.intersects(n.solidArea)) {
                            entity.collisionOn = true;
                            index = counterOfEntities - 1;
                        }
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        if (entity.solidArea.intersects(n.solidArea)) {
                            entity.collisionOn = true;
                            index = counterOfEntities - 1;
                        }
                        break;
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                n.solidArea.x = n.solidAreaDefaultX;
                n.solidArea.y = n.solidAreaDefaultY;
            }
        }
        return index;
    }

    /**
     * Checks collision between player and entity (npc, for example). I need this method because if it wasn't there. Entity could go
     * through player but player not through entity. (Collision would be only one-sided).
     *
     * @param entity that's being checked.
     */
    public void checkPlayer(Entity entity) {
        entity.solidArea.x += entity.worldX;
        entity.solidArea.y += entity.worldY;

        gp.player.solidArea.x += gp.player.worldX;
        gp.player.solidArea.y += gp.player.worldY;

        switch (entity.direction) {
            case "left":
                entity.solidArea.x -= entity.speed;
                break;
            case "right":
                entity.solidArea.x += entity.speed;
                break;
            case "up":
                entity.solidArea.y -= entity.speed;
                break;
            case "down":
                entity.solidArea.y += entity.speed;
                break;
        }
        //checks if two rectangles are in collision
        if (entity.solidArea.intersects(gp.player.solidArea)) entity.collisionOn = true;

        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
    }
}

package Main;

import Entity.Entity;

public class CollisionChecker {
  
  GamePanel gamePanel;
  public CollisionChecker(GamePanel gamePanel) {
    this.gamePanel = gamePanel;
  }
  public void checkTile(Entity entity) {
    int entityLeftWorldX = entity.worldX + entity.solidArea.x;
    int entityRightWorldX = entityLeftWorldX + entity.solidArea.width;
    int entityTopWorldY = entity.worldY + entity.solidArea.y;
    int entityBottomWorldY = entityTopWorldY + entity.solidArea.height;
    
    int entityLeftCol = entityLeftWorldX/gamePanel.TILESIZE;
    int entityRightCol = entityRightWorldX/gamePanel.TILESIZE;
    int entityTopRow = entityTopWorldY/gamePanel.TILESIZE;
    int entityBottomRow = entityBottomWorldY/gamePanel.TILESIZE;
    
    int tileNum1;
    int tileNum2;
    
    if (entity.direction.equals("down")) {
      entityBottomRow += entity.speed/gamePanel.TILESIZE;
    } else if (entity.direction.equals("up")) {
      entityTopRow -= entity.speed/gamePanel.TILESIZE;
    } else if (entity.direction.equals("right")) {
      entityRightCol += entity.speed/gamePanel.TILESIZE;
    } else if (entity.direction.equals("left")) {
      entityRightCol -= entity.speed/gamePanel.TILESIZE;
    }
  }
}

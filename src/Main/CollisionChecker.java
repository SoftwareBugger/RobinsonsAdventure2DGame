package Main;

import Entity.Entity;

public class CollisionChecker {
  
  GamePanel gamePanel;
  public CollisionChecker(GamePanel gamePanel) {
    this.gamePanel = gamePanel;
  }
  
  /**
   * checking if the entity is hitting a solid tile
   * @param entity
   */
  public void checkTile(Entity entity) {
    // the coordinates of the solid area
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
    
    // check the direction
    if (entity.direction.equals("down")) {
      // predict next row coordinate
      entityBottomRow += entity.speed/gamePanel.TILESIZE;
      tileNum1 = gamePanel.manager.mapTileNumbers[entityBottomRow][entityLeftCol];
      tileNum2 = gamePanel.manager.mapTileNumbers[entityBottomRow][entityRightCol];
      if (gamePanel.manager.tiles[tileNum1].collision == true ||
          gamePanel.manager.tiles[tileNum2].collision == true) {
        entity.collisionOn = true;
      }
      
      
    } else if (entity.direction.equals("up")) {
      // predict next row coordinate
      entityTopRow -= entity.speed/gamePanel.TILESIZE;
      tileNum1 = gamePanel.manager.mapTileNumbers[entityTopRow][entityLeftCol];
      tileNum2 = gamePanel.manager.mapTileNumbers[entityTopRow][entityRightCol];
      if (gamePanel.manager.tiles[tileNum1].collision == true ||
          gamePanel.manager.tiles[tileNum2].collision == true) {
        entity.collisionOn = true;
      }
    } else if (entity.direction.equals("right")) {
      // predict next column coordinate
      entityRightCol += entity.speed/gamePanel.TILESIZE;
      tileNum1 = gamePanel.manager.mapTileNumbers[entityTopRow][entityRightCol];
      tileNum2 = gamePanel.manager.mapTileNumbers[entityBottomRow][entityRightCol];
      if (gamePanel.manager.tiles[tileNum1].collision == true ||
          gamePanel.manager.tiles[tileNum2].collision == true) {
        entity.collisionOn = true;
      }
    } else if (entity.direction.equals("left")) {
      // predict next column coordinate
      entityRightCol -= entity.speed/gamePanel.TILESIZE;
      tileNum1 = gamePanel.manager.mapTileNumbers[entityTopRow][entityLeftCol];
      tileNum2 = gamePanel.manager.mapTileNumbers[entityBottomRow][entityLeftCol];
      if (gamePanel.manager.tiles[tileNum1].collision == true ||
          gamePanel.manager.tiles[tileNum2].collision == true) {
        entity.collisionOn = true;
      }
    }
  }
}

package Entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import Main.GamePanel;
import Main.KeyHandler;

public class Player extends Entity {
  
  GamePanel gamePanel;
  KeyHandler keyHandler;
  
  // screen position of the player, usually the center of the screen and doesn't change
  public final int ScreenX;
  public final int ScreenY;
  
  public Player(GamePanel gamePanel, KeyHandler keyHandler) {
    this.gamePanel = gamePanel;
    this.keyHandler = keyHandler;
    this.ScreenX = gamePanel.SCREENWIDTH/2;
    this.ScreenY = gamePanel.SCREENHEIGHT/2;
    // x, y, width, height
    // the solid area of the player, usually smaller than the tile
    this.solidArea = new Rectangle(8, 16, 32, 32);
    this.setDefaultValues();
    this.getUserImage();
  }
  
  public void setDefaultValues() {
    // initial position of the player
    this.worldX = 25*gamePanel.TILESIZE;
    this.worldY = 25*gamePanel.TILESIZE;
    this.speed = 3;
    this.direction = "down"; // any is fine
  }
  
  public void getUserImage() {
    try {
      // read file words and getclass.getResourcesAsStream doesn't
      this.up1 = ImageIO.read(new File("/Users/lukelvhan/eclipse-workspace/My2DGame/res/character/up1.png"));
      this.up2 = ImageIO.read(new File("/Users/lukelvhan/eclipse-workspace/My2DGame/res/character/up2.png"));
      this.left1 = ImageIO.read(new File("/Users/lukelvhan/eclipse-workspace/My2DGame/res/character/left1.png"));
      this.left2 = ImageIO.read(new File("/Users/lukelvhan/eclipse-workspace/My2DGame/res/character/left2.png"));
      this.right1 = ImageIO.read(new File("/Users/lukelvhan/eclipse-workspace/My2DGame/res/character/right1.png"));
      this.right2 = ImageIO.read(new File("/Users/lukelvhan/eclipse-workspace/My2DGame/res/character/right2.png"));
      this.down1 = ImageIO.read(new File("/Users/lukelvhan/eclipse-workspace/My2DGame/res/character/down1.png"));
      this.down2 = ImageIO.read(new File("/Users/lukelvhan/eclipse-workspace/My2DGame/res/character/down2.png"));
    } catch (IOException e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
  }
  // move all the update and draw methods to the individual players
  public void update() {
//    // solid area
//    int topLeftX = this.worldX + gamePanel.TILESIZE/3;
//    int topLeftY = this.worldY + gamePanel.TILESIZE/3;
//    int topRightX = this.worldX + gamePanel.TILESIZE - gamePanel.TILESIZE/3;
//    int topRightY = this.worldY + gamePanel.TILESIZE/3;
//    int bottomLeftX = this.worldX + gamePanel.TILESIZE/3;
//    int bottomLeftY = this.worldY + gamePanel.TILESIZE;
//    int bottomRightX = this.worldX + gamePanel.TILESIZE - gamePanel.TILESIZE/3; 
//    int bottomRightY = this.worldY + gamePanel.TILESIZE;
//    // move the character
//    if (this.keyHandler.upPressed == true) {
//      this.direction = "up";
//      if (!gamePanel.manager.tiles[gamePanel.manager.mapTileNumbers[topRightY/gamePanel.TILESIZE][topRightX/gamePanel.TILESIZE]].collision &&
//          !gamePanel.manager.tiles[gamePanel.manager.mapTileNumbers[topLeftY/gamePanel.TILESIZE][topLeftX/gamePanel.TILESIZE]].collision) 
//        this.worldY -= this.speed;
//    } else if (this.keyHandler.downPressed == true) {
//      this.direction = "down";
//      if (!gamePanel.manager.tiles[gamePanel.manager.mapTileNumbers[bottomRightY/gamePanel.TILESIZE][bottomRightX/gamePanel.TILESIZE]].collision &&
//          !gamePanel.manager.tiles[gamePanel.manager.mapTileNumbers[bottomLeftY/gamePanel.TILESIZE][bottomLeftX/gamePanel.TILESIZE]].collision) 
//        this.worldY += this.speed;
//    } else if (this.keyHandler.leftPressed == true) {
//      this.direction = "left";
//      if (!gamePanel.manager.tiles[gamePanel.manager.mapTileNumbers[topLeftY/gamePanel.TILESIZE][topLeftX/gamePanel.TILESIZE]].collision &&
//          !gamePanel.manager.tiles[gamePanel.manager.mapTileNumbers[bottomLeftY/gamePanel.TILESIZE][bottomLeftX/gamePanel.TILESIZE]].collision) 
//        this.worldX -= this.speed;
//    } else if (this.keyHandler.rightPressed == true) {
//      this.direction = "right";
//      if (!gamePanel.manager.tiles[gamePanel.manager.mapTileNumbers[topRightY/gamePanel.TILESIZE][topRightX/gamePanel.TILESIZE]].collision &&
//          !gamePanel.manager.tiles[gamePanel.manager.mapTileNumbers[bottomRightY/gamePanel.TILESIZE][bottomRightX/gamePanel.TILESIZE]].collision)
//        this.worldX += this.speed;
//    }
    // move the character
    if (this.keyHandler.upPressed == true) {
      this.direction = "up";
      this.worldY -= this.speed;
    } else if (this.keyHandler.downPressed == true) {
      this.direction = "down";
      this.worldY += this.speed;
    } else if (this.keyHandler.leftPressed == true) {
      this.direction = "left";
      this.worldX -= this.speed;
    } else if (this.keyHandler.rightPressed == true) {
      this.direction = "right";
      this.worldX += this.speed;
    }
    
    collisionOn = false;
    // pass the player in the checker
    gamePanel.collisionChecker.checkTile(this);
    
    // The sprite changes every 12 frames which means 6 times in each second
    // only update sprite when it's moving
    if (this.keyHandler.upPressed == true ||
        this.keyHandler.downPressed == true ||
        this.keyHandler.leftPressed == true ||
        this.keyHandler.rightPressed == true) {
      spriteCounter++;
      if (spriteCounter > 12) {
        if (spriteNum == 1) spriteNum = 2;
        else if (spriteNum == 2) spriteNum = 1;
        spriteCounter = 0;
      }
    } else {
      // it no key pressed, make the character stand
      spriteNum = 2;
    }
    
    
  }
  
  public void draw(Graphics2D g2) {
//    g2.setColor(Color.white);
//    g2.fillRect(this.x, this.y, gamePanel.TILESIZE, gamePanel.TILESIZE);
    BufferedImage image = null;
    if (this.direction.equals("up")) {
      if (spriteNum == 1) image = up1;
      if (spriteNum == 2) image = up2;
    }
    if (this.direction.equals("down")) {
      if (spriteNum == 1) image = down1;
      if (spriteNum == 2) image = down2;
    }
    if (this.direction.equals("left")) {
      if (spriteNum == 1) image = left1;
      if (spriteNum == 2) image = left2;
    }
    if (this.direction.equals("right")) {
      if (spriteNum == 1) image = right1;
      if (spriteNum == 2) image = right2;
    }
    // the last parameter is image observer, can be null for now
    g2.drawImage(image, ScreenX, ScreenY, gamePanel.TILESIZE, gamePanel.TILESIZE, null);
  }
}

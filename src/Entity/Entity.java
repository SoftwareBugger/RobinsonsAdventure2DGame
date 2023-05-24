package Entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * super class of all characters
 * @author lukelvhan
 *
 */
public class Entity { 
  
  // not screen position, player's universal position in the world
  public int worldX;
  public int worldY;
  // screen's position
  public int screenX;
  public int screenY;
  public int speed;
  
  // different objects for different pictures
  public BufferedImage up1;
  public BufferedImage up2;
  public BufferedImage down1;
  public BufferedImage down2;
  public BufferedImage right1;
  public BufferedImage right2;
  public BufferedImage left1;
  public BufferedImage left2;
  
  // use to select pictures
  public String direction;
  
  // spriteCounter use to count frames in unit of FPS, when the frame reaches 12 times, the sprite switches
  // spriteNum1 indicates posture 1 and 2 indicates posture 2 in each set of pictures of each direction
  public int spriteCounter = 0;
  public int spriteNum = 1;
  
  // solid area using a rectangle class
  public Rectangle solidArea;
  public boolean collisionOn = false;

}

package Tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import javax.imageio.ImageIO;
import Main.GamePanel;

public class TileManager {
  GamePanel gamePanel;
  public Tile[] tiles;
  public int[][] mapTileNumbers;
  
  public TileManager(GamePanel gamePanel) {
    this.gamePanel = gamePanel;
    this.tiles = new Tile[9];
    this.mapTileNumbers = new int[this.gamePanel.WORLDROW][this.gamePanel.WORLDCOLUMN];
    getTileImage();
    loadMap("/Users/lukelvhan/eclipse-workspace/My2DGame/src/maps/WorldMap.txt");
  }
  
  /**
   * loads all the tile images as BufferedImage
   */
  public void getTileImage() {
    
//    // instantiate some tiles
//    try {
//      // fill everything with grass 
//      for (int i = 0; i < tiles.length; i++) {
//        for (int j = 0; j < tiles[0].length; j++) {
//          tiles[i][j] = new Tile();
//          tiles[i][j].image = ImageIO.read(new File("/Users/lukelvhan/eclipse-workspace/My2DGame/res/tiles/grass.png"));
//        }
//      }
//      
//      // we can save the map data in a text file
//      // fill a river
//      for (int i = tiles.length/2; i < tiles.length/2 + 3; i++) {
//        for (int j = 0; j < tiles[0].length; j++) {
//          tiles[i][j].image = ImageIO.read(new File("/Users/lukelvhan/eclipse-workspace/My2DGame/res/tiles/Water.png"));
//        }
//      }
//      
//      // fill 2 sides of walls at the top left corner
//      for (int i = tiles.length/4; i < tiles.length/4 + 1; i++) {
//        for (int j = 0; j < tiles[0].length/4; j++) {
//          if (j != tiles[0].length/8) {
//            tiles[i][j].image = ImageIO.read(new File("/Users/lukelvhan/eclipse-workspace/My2DGame/res/tiles/greyBrick.png"));
//          }
//        }
//      }
//      
//      for (int i = 0; i < tiles.length/4 + 1; i++) {
//        for (int j = tiles[0].length/4; j < tiles[0].length/4 + 1; j++) {
//          tiles[i][j].image = ImageIO.read(new File("/Users/lukelvhan/eclipse-workspace/My2DGame/res/tiles/greyBrick.png"));
//        }
//      }
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//  }
    try {
      
      tiles[0] = new Tile();
      tiles[0].image = ImageIO.read(new File("/Users/lukelvhan/eclipse-workspace/My2DGame/res/tiles/grass.png"));
      
      tiles[1] = new Tile();
      tiles[1].image = ImageIO.read(new File("/Users/lukelvhan/eclipse-workspace/My2DGame/res/tiles/greyBrick.png"));
      tiles[1].collision = true;
      
      tiles[2] = new Tile();
      tiles[2].image = ImageIO.read(new File("/Users/lukelvhan/eclipse-workspace/My2DGame/res/tiles/Water.png"));
      tiles[2].collision = true;
      
      tiles[3] = new Tile();
      tiles[3].image = ImageIO.read(new File("/Users/lukelvhan/eclipse-workspace/My2DGame/res/tiles/dirt.png"));
      
      tiles[4] = new Tile();
      tiles[4].image = ImageIO.read(new File("/Users/lukelvhan/eclipse-workspace/My2DGame/res/tiles/flower.png"));
      
      tiles[5] = new Tile();
      tiles[5].image = ImageIO.read(new File("/Users/lukelvhan/eclipse-workspace/My2DGame/res/tiles/treeNoFruits.png"));
      tiles[5].collision = true;
      
      tiles[6] = new Tile();
      tiles[6].image = ImageIO.read(new File("/Users/lukelvhan/eclipse-workspace/My2DGame/res/tiles/tree.png"));
      tiles[6].collision = true;
      
      tiles[7] = new Tile();
      tiles[7].image = ImageIO.read(new File("/Users/lukelvhan/eclipse-workspace/My2DGame/res/tiles/sand.png"));
      
      tiles[8] = new Tile();
      tiles[8].image = ImageIO.read(new File("/Users/lukelvhan/eclipse-workspace/My2DGame/res/tiles/cactus.png"));
      tiles[8].collision = true;
      
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public void loadMap(String res) {
    try {
      // a txt file is a good way of saving the map and change it from a visualized perspective!!!
      // use a variable so that we can load different maps!!
      FileInputStream is = new FileInputStream(new File(res));
      Scanner sc = new Scanner(is);
      for (int i = 0; i < mapTileNumbers.length; i++) {
        for (int j = 0; j < mapTileNumbers[0].length; j++) {
          if (sc.hasNext()) {
            mapTileNumbers[i][j] = sc.nextInt();
            System.out.println(mapTileNumbers[i][j]);
          }
        }
      }
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  public void draw(Graphics2D g2) {
    
    // draw the map
    // converting from the world x, y to screen x, y and draw the ones that are visible in the camera
    // fixed the problem of only moving one block at a time
    int i;
    int j;
    for (i = 0; i < gamePanel.WORLDROW; i++) {
      for (j = 0; j < gamePanel.WORLDCOLUMN; j++) {
        int WorldX = j*gamePanel.TILESIZE;
        int WorldY = i*gamePanel.TILESIZE;
        int ScreenX = WorldX - (gamePanel.player.worldX - gamePanel.player.ScreenX);
        int ScreenY = WorldY - (gamePanel.player.worldY - gamePanel.player.ScreenY);
        if (ScreenX < (gamePanel.MAXCOLUMN + 1)*gamePanel.TILESIZE && 
            ScreenX > -gamePanel.TILESIZE &&
            ScreenY < (gamePanel.MAXROW + 1)*gamePanel.TILESIZE &&
            ScreenY > -gamePanel.TILESIZE) {
          g2.drawImage(tiles[this.mapTileNumbers[i][j]].image, ScreenX, ScreenY, gamePanel.TILESIZE, gamePanel.TILESIZE, null);
        }
      }
    }
    
    // fill the other 4 sides with sea;
    // watch out for the coordinates
    j = gamePanel.WORLDCOLUMN;
    while (j*gamePanel.TILESIZE - (gamePanel.player.worldX - gamePanel.player.ScreenX) < gamePanel.SCREENWIDTH + gamePanel.TILESIZE) {
      i = (gamePanel.player.worldY - gamePanel.player.ScreenY)/gamePanel.TILESIZE - 1;
      while (i*gamePanel.TILESIZE - (gamePanel.player.worldY - gamePanel.player.ScreenY) < gamePanel.SCREENHEIGHT + gamePanel.TILESIZE) {
        g2.drawImage(tiles[2].image, j*gamePanel.TILESIZE - (gamePanel.player.worldX - gamePanel.player.ScreenX), 
            i*gamePanel.TILESIZE - (gamePanel.player.worldY - gamePanel.player.ScreenY), gamePanel.TILESIZE, gamePanel.TILESIZE, null);
        i++;
      }
      j++; 
    }
    
    j = -1;
    while (j*gamePanel.TILESIZE - (gamePanel.player.worldX - gamePanel.player.ScreenX) > -gamePanel.TILESIZE) {
      i = (gamePanel.player.worldY - gamePanel.player.ScreenY)/gamePanel.TILESIZE - 1;
      while (i*gamePanel.TILESIZE - (gamePanel.player.worldY - gamePanel.player.ScreenY) < gamePanel.SCREENHEIGHT + gamePanel.TILESIZE) {
        g2.drawImage(tiles[2].image, j*gamePanel.TILESIZE - (gamePanel.player.worldX - gamePanel.player.ScreenX), 
            i*gamePanel.TILESIZE - (gamePanel.player.worldY - gamePanel.player.ScreenY), gamePanel.TILESIZE, gamePanel.TILESIZE, null);
        i++;
      }
      j--; 
    }
    
    i = gamePanel.WORLDROW;
    while (i*gamePanel.TILESIZE - (gamePanel.player.worldY - gamePanel.player.ScreenY) < gamePanel.SCREENHEIGHT + gamePanel.TILESIZE) {
      j = (gamePanel.player.worldX - gamePanel.player.ScreenX)/gamePanel.TILESIZE - 1;
      while (j*gamePanel.TILESIZE - (gamePanel.player.worldX - gamePanel.player.ScreenX) < gamePanel.SCREENWIDTH + gamePanel.TILESIZE) {
        g2.drawImage(tiles[2].image, j*gamePanel.TILESIZE - (gamePanel.player.worldX - gamePanel.player.ScreenX), 
            i*gamePanel.TILESIZE - (gamePanel.player.worldY - gamePanel.player.ScreenY), gamePanel.TILESIZE, gamePanel.TILESIZE, null);
        j++;
      }
      i++; 
    }
    
    i = -1;
    while (i*gamePanel.TILESIZE - (gamePanel.player.worldY - gamePanel.player.ScreenY) > -gamePanel.TILESIZE) {
      j = (gamePanel.player.worldX - gamePanel.player.ScreenX)/gamePanel.TILESIZE - 1;
      while (j*gamePanel.TILESIZE - (gamePanel.player.worldX - gamePanel.player.ScreenX) < gamePanel.SCREENWIDTH + gamePanel.TILESIZE) {
        g2.drawImage(tiles[2].image, j*gamePanel.TILESIZE - (gamePanel.player.worldX - gamePanel.player.ScreenX), 
            i*gamePanel.TILESIZE - (gamePanel.player.worldY - gamePanel.player.ScreenY), gamePanel.TILESIZE, gamePanel.TILESIZE, null);
        j++;
      }
      i--; 
    }
      
  }
}

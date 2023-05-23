package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import Entity.Player;
import Tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
  // screen setting
  final int ORIGINALTILESIZE = 16; // 16 by 16 tile default size of 2D games
  final int SCALE = 3; // popular scale
  public final int TILESIZE = SCALE*ORIGINALTILESIZE;// 48 x 48
  public final int MAXCOLUMN = 16; 
  public final int MAXROW = 12;// 12x16 tiles will be displayed 4:3
  public final int SCREENWIDTH = MAXCOLUMN*TILESIZE; // 768 pixels
  public final int SCREENHEIGHT = MAXROW*TILESIZE; // 576 pixels
  //FPS: frames per second
  final int FPS = 60; // update the frame 60 times per second
  
  // WORLD SETTINGS
  public final int WORLDROW = 50;
  public final int WORLDCOLUMN = 50;
  public final int WORLDWIDTH = WORLDCOLUMN*TILESIZE;
  public final int WORLDHEIGHT = WORLDROW*TILESIZE;
  
  KeyHandler keyHandler;
  
  public Player player;
  
  public TileManager manager;
  
  public CollisionChecker collisionChecker;
  
  // a time record is needed in our game so that we could refresh to show animation
  // a thread can stop and start 
  // need to implement runnable interface!!
  Thread gameThread;
  
  // set player's default position
//  int playerX = 100;
//  int playerY = 100;
//  int playerSpeed = 4;
  
  public GamePanel() {
    this.setPreferredSize(new Dimension(SCREENWIDTH, SCREENHEIGHT));
    this.setBackground(Color.black); 
    this.setDoubleBuffered(true);// increase quality of animation
    this.keyHandler = new KeyHandler();
    this.player = new Player(this, keyHandler);
    this.manager = new TileManager(this);
    this.collisionChecker = new CollisionChecker(this);
    this.addKeyListener(this.keyHandler);
    // set the panel able to focus the key actions
    this.setFocusable(true);
  }
  
  public void startGameThread() {
    // instantiate a thread
    gameThread = new Thread(this);
    // start method calls the run method and starts execution
    gameThread.start();
  }

  /**
   * a run method is called when the thread starts and threads execute seperately from the main thread
   * and each other
   */
  @Override
  // Game loop using sleep
//  public void run() {
//    
//    double interval = 1000000000/FPS; // the unit is in nanoseconds
//    double nextDrawTime = System.nanoTime() + interval;
//    // main game loop
//    while (gameThread != null) {
//      // System.out.println("The game is running");
//      
//      long currTime = System.nanoTime();
//      //long currTime = System.currentTimeMillis();
//      
//      // 1. UPDATE: such as character positions
//      this.update();
//      // without interval the object moves as fast as it can, it goes out the the frame
//      
//      // 2. REDRAW: characters of the new positions
//      this.repaint(); // this is how the paintComponent method is called
//      
//      
//      try {
//        double remainingTime = nextDrawTime - System.nanoTime();
//        Thread.sleep((long) remainingTime/1000000);
//        // in case the thread doesn't need to sleep
//        if (remainingTime < 0) remainingTime = 0;
//        // new nextDrawTime
//        nextDrawTime += interval;
//      } catch (InterruptedException e) {
//        // TODO Auto-generated catch block
//        e.printStackTrace();
//      }
//      
//    }
//    
//  }
  public void run() {
    double interval = 1000000000/FPS; // the unit is in nanoseconds
    double delta = 0;
    long lastTime = System.nanoTime();
    long currentTime;
    long timer = 0;
    int drawCount = 0;
    while (this.gameThread != null) {
      currentTime = System.nanoTime();
      delta += (currentTime - lastTime)/interval;
      timer += currentTime - lastTime;
      lastTime = currentTime;
      if (delta > 1) {
        // 1.Update the positions
        update();
        // repaint: calling the paint component
        repaint();
        delta--;
        drawCount++;
      }
      
      if (timer > 1000000000) {
        System.out.println("FPS :" + drawCount);
        // reset drawCount and timer
        drawCount = 0;
        timer = 0;
      }
    }
  }
  
  public void update() {
    player.update();
  }
  
  @Override
  public void paintComponent(Graphics g) {
    // the graphics class can draw many objects
    // super.paintComponent(g);
    
    // cast it to Graphics2D to include more sophisticated features
    Graphics2D g2 = (Graphics2D) g;
    manager.draw(g2);
    // make sure to draw the tile first before the player
    player.draw(g2);
    
    
    // Save memory in time
    g2.dispose();
    
  }
}

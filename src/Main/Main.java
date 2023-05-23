package Main;

import javax.swing.JFrame;

public class Main {

  public static void main(String[] args) {
    // TODO Auto-generated method stub
    JFrame window = new JFrame();
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setResizable(false);
    window.setTitle("2D Adventure");
    
    GamePanel gamePanel = new GamePanel();
    window.add(gamePanel);
    
    window.pack();// fit the size of the panel
    
    window.setLocationRelativeTo(null); // keep at the center of the screen
    window.setVisible(true);
    
    gamePanel.startGameThread();
    
    
    
  }

}

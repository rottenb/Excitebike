package excitebike;

import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class Excitebike extends JFrame implements KeyListener {
  public final static int SCREEN_X = 300;
  public final static int SCREEN_Y = 200;
  public final static int SCALE = 2;

  private static GamePanel gamePanel;

  public Excitebike() {
    gamePanel = new GamePanel();

    // setup game window
    setTitle("Excitebike?");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setFocusable(true);
    addKeyListener(this);
  } // Excitebike()

  public static void main(String[] args) {
    final Runnable doGameLoop = new Runnable() {
      @Override
      public void run() {
        Excitebike exciteBikeGame = new Excitebike();

        exciteBikeGame.setResizable(false);
        exciteBikeGame.pack();
        exciteBikeGame.setSize(SCREEN_X * SCALE, SCREEN_Y * SCALE);
        exciteBikeGame.getContentPane().add(gamePanel);
        exciteBikeGame.setLocationRelativeTo(null);
        exciteBikeGame.setVisible(true);
      }
    };
    try {
      EventQueue.invokeAndWait(doGameLoop);
      // GAME LOOP
      while (true) {
        gamePanel.repaint();

        try {
          Thread.sleep(17);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      } // GAME LOOP
    } catch (Exception e) {
      e.printStackTrace();
    }
  } // main()

  @Override
  public void keyPressed(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_UP:
        gamePanel.upArrowPressed();
        break;
      case KeyEvent.VK_DOWN:
        gamePanel.downArrowPressed();
        break;
      case KeyEvent.VK_RIGHT:
        gamePanel.rightArrowPressed();
        break;
      case KeyEvent.VK_LEFT:
        gamePanel.leftArrowPressed();
        break;
      case KeyEvent.VK_SPACE:
        gamePanel.spacePressed();
        break;
      case KeyEvent.VK_W:
        gamePanel.wPressed();
        break;
      case KeyEvent.VK_A:
        gamePanel.aPressed();
        break;
      case KeyEvent.VK_S:
        gamePanel.sPressed();
        break;
      case KeyEvent.VK_D:
        gamePanel.dPressed();
        break;
    }
  } // keyPressed()

  @Override
  public void keyReleased(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_UP:
        gamePanel.upArrowReleased();
        break;
      case KeyEvent.VK_DOWN:
        gamePanel.downArrowReleased();
        break;
      case KeyEvent.VK_RIGHT:
        gamePanel.rightArrowReleased();
        break;
      case KeyEvent.VK_LEFT:
        gamePanel.leftArrowReleased();
        break;
      case KeyEvent.VK_SPACE:
        gamePanel.spaceReleased();
        break;
        case KeyEvent.VK_W:
          gamePanel.wReleased();
          break;
        case KeyEvent.VK_A:
          gamePanel.aReleased();
          break;
        case KeyEvent.VK_S:
          gamePanel.sReleased();
          break;
        case KeyEvent.VK_D:
          gamePanel.dReleased();
          break;
    }
  } // keyRleased()

  @Override
  public void keyTyped(KeyEvent e) {

  } // keyTyped()

} // Excitebike

package excitebike;

import java.io.File;
import java.io.IOException;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ExciteSprite extends JPanel {
  private final static int FRAME_DELAY = 2;

  protected final static int SCALE = Excitebike.SCALE;
  public static int TOP_SPEED = 10;

  protected BufferedImage imageMap;
  protected BufferedImage spriteImage;

  protected int posX, posY;
  protected int width, height;
  protected int speedX, speedY;
  protected int spriteIndex;
  protected int frameIndex = 0;
  private int frameTimer = FRAME_DELAY;

  public boolean visible = true;
  public boolean animate = false;

  public void loadImage(String path) {
    try {
      imageMap = ImageIO.read(new File(
                      Paths.get(".").toAbsolutePath().normalize().toString() +
                      path));
    } catch (IOException e) {
      System.out.println(e);
    }
  }

  public void setSprite(int x, int y, int w, int h) {
    spriteImage = imageMap.getSubimage(x, frameIndex * 24, w, h);
  }

  protected void animate() {
    if (spriteIndex < 0) {
      spriteIndex = 0;
    }
    if (animate) {
      if (frameTimer == 0) {
        frameIndex ^= 1; // flip 0 to 1 to 0
        setSprite(spriteIndex * 24, frameIndex * 24, 24, 24);
        frameTimer = FRAME_DELAY;
      } else {
        frameTimer--;
      }
    }
  } // animate()

} // ExciteSprite

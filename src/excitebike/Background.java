package excitebike;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Background {
  private final static String BACKGROUND_IMAGE_FILE = "/res/arena.png";

  protected BufferedImage bgImage;

  protected static int speedX = 0;
  protected int bgX;

  public Background(int x) {
    bgX = x;

    // Load background image or die trying
    try {
      bgImage = ImageIO.read(new File(
                      Paths.get(".").toAbsolutePath().normalize().toString() +
                      BACKGROUND_IMAGE_FILE));
    } catch (IOException e) {
      System.out.println(e);
    }
  } // Background()

  public void update() {
    bgX -= speedX;
    if (bgX <= -512) {
      bgX +=1024;
    }
  } // update()
} // Background

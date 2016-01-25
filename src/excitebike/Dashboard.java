package excitebike;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Dashboard extends JPanel {
  private final static String dashboardPath = "/res/dashboard.png";

  protected static BufferedImage img;
  protected boolean isDisplayed = false;

  public Dashboard() {
    loadImage(dashboardPath);
  }

  public void loadImage(String path) {
    try {
      img =  (ImageIO.read(new File(
                      Paths.get(".").toAbsolutePath().normalize().toString() +
                      path)));
    } catch (IOException e) {
      System.out.println(e);
    }
  }
} // Dashboard

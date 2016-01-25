package excitebike;

import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.imageio.ImageIO;

public class GamePanel extends JPanel {
  private final static int SCREEN_X = Excitebike.SCREEN_X;
  private final static int SCREEN_Y = Excitebike.SCREEN_Y;
  private final static int SCALE = Excitebike.SCALE;

  private static Background[] bkGrnd = new Background[2];
  private static Rider rider = new Rider();
  private Track track = new Track();
  private static Dashboard dashboard = new Dashboard();

  private static int scroll_speed = 0;

  public GamePanel() {
    // load arena background
    bkGrnd[0] = new Background(-6);
    bkGrnd[1] = new Background(506);

    setBackground(Color.BLACK);

    // text box goes here
  } // Arena()}

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    scroll_speed = rider.speedX;

    track.course[0].speedX = scroll_speed;
  //  track.course[1].speedX = scroll_speed;

    bkGrnd[0].speedX = scroll_speed;
    bkGrnd[1].speedX = scroll_speed;


    rider.update();
    track.updateObstacle(0);
    bkGrnd[0].update();
    bkGrnd[1].update();

    /*  g.drawImage( IMAGE, Dest.start.X, Dest.start.Y,
                            Dest.end.X, Dest.end.Y,
                            Src.start.X, Src.start.X,
                            Src.end.X, Src.end.Y, OBSERVER);
    */

    if (bkGrnd[0].bgX <= 512) {
      g.drawImage(bkGrnd[0].bgImage, bkGrnd[0].bgX * SCALE, 0 * SCALE,
                          (bkGrnd[0].bgX + 512) * SCALE, 142 * SCALE,
                          0, 0, 512, 142, null);
    }
    if (bkGrnd[1].bgX <= 512) {
      g.drawImage(bkGrnd[1].bgImage, bkGrnd[1].bgX * SCALE, 0 * SCALE,
                          (bkGrnd[1].bgX + 512) * SCALE, 142 * SCALE,
                          0, 0, 512, 142, null);
    }

    for (int i = 0; i < track.COURSE_TOTAL; i++) {
//      track.course[i].visible = false;
      if (track.course[i].visible) {

        g.drawImage(track.course[i].imageMap, track.course[i].posX * SCALE,
                            track.course[i].posY * SCALE,
                            (track.course[i].posX + track.course[i].width) * SCALE,
                            (track.course[i].posY + track.course[i].height) * SCALE,
                            0, 0, track.course[i].width,
                            track.course[i].height, null);
        g.setColor(Color.GREEN);
        g.drawRect(track.course[i].posX * SCALE, (track.course[i].posY + 17) * SCALE, 16 * SCALE, 49 * SCALE);
        g.setColor(Color.BLACK);
        g.drawRect((track.course[i].posX + 17)* SCALE, (track.course[i].posY + 17) * SCALE, 8 * SCALE, 49 * SCALE);
        g.setColor(Color.RED);
        g.drawRect((track.course[i].posX + 25)* SCALE, (track.course[i].posY + 17) * SCALE, 16 * SCALE, 49 * SCALE);

      }
    }


    g.setColor(Color.BLUE);
    g.drawRect( rider.posX * SCALE, (rider.posY - rider.hOffset) * SCALE,
                24 * SCALE, 24 * SCALE);

    g.drawImage(rider.spriteImage, rider.posX * SCALE, (rider.posY - rider.hOffset) * SCALE,
                                   (24 + rider.posX) * SCALE,
                                   (24 + rider.posY - rider.hOffset) * SCALE,
                                   0, 0,
                                   24, 24, null);
    if (rider.getShadowVis()) {
      g.drawImage(rider.getShadowImg(), (6 + rider.posX) * SCALE, (20 + rider.posY) * SCALE,
                                    (18 + rider.posX) * SCALE, (24 + rider.posY) * SCALE,
                                    0, 0, 12, 4, null);
    }

    g.drawImage(dashboard.img, 0 * SCALE, 142 * SCALE,
                               SCREEN_X * SCALE, (142 + 40) * SCALE,
                               0, 0, 220, 40, null);
  } // paintComponent()

  public void upArrowPressed() {}

  public void downArrowPressed() {}

  public void rightArrowPressed() {
    rider.setControlState(Rider.ControlState.GAS);
  }

  public void leftArrowPressed() {
    rider.setControlState(Rider.ControlState.BRAKE);
  }

  public void spacePressed() {
//    if (rider.getActState() != Rider.ActivityState.WHEELIE_UP) {
//      rider.setActivityState(Rider.ActivityState.JUMP);
//    }
  }

  public void wPressed() {
    rider.actTurnLeft();
  }

  public void aPressed() {
    rider.setActivityState(Rider.ActivityState.WHEELIE_UP);
  }

  public void sPressed() {
    rider.actTurnRight();
  }

  public void dPressed() {
  }


  public void upArrowReleased() {}

  public void downArrowReleased() {}

  public void rightArrowReleased() {
    rider.setControlState(Rider.ControlState.NEUTRAL);
  }

  public void leftArrowReleased() {
    rider.setControlState(Rider.ControlState.NEUTRAL);
  }

  public void spaceReleased() {
//    rider.lowerHeight(0);
//    rider.setActivityState(Rider.ActivityState.RUN);
  }

  public void wReleased() {
    rider.stopY();
  }

  public void aReleased() {
    rider.setActivityState(Rider.ActivityState.WHEELIE_DOWN);
  }

  public void sReleased() {
    rider.stopY();
  }

  public void dReleased() {}

} // GamePanel

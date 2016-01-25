package excitebike;

import java.util.Random;
import java.awt.Rectangle;

public class Track {
  private final static int MAX_WIDTH = 232;
  private final static int OBS_MAX = 8;

  protected static enum CollisionType {UP, DOWN, FLAT, SLOW, FAST, NONE};


  protected final static int COURSE_TOTAL = 2;

  protected static Obstacle[] course = new Obstacle[COURSE_TOTAL];

  // {y offset, width, height, slope, max}
  protected static int[][] partList = new int[][]{
                                        {63, 40, 65},
                                        {47, 72, 81},
                                        {64, 72, 64},
                                        {47, 40, 81},
                                        {55, 88, 73},
                                        {79, 92, 50},
                                        {32, 232, 96},
                                        {32, 215, 96} };

  public Track() {
    for (int i = 0; i < COURSE_TOTAL; i++) {
      int j = new Random().nextInt(OBS_MAX);
      j = 0;
      int gap = (new Random().nextInt(3));
      course[i] = new Obstacle("/res/" + j + ".png", i * Excitebike.SCREEN_X,
                                partList[j][0], partList[j][1], partList[j][2]);
    }
  } // Track()

  public void updateObstacle(int i) {
    course[i].posX -= course[i].speedX;

    if((course[i].posX <= Excitebike.SCREEN_X + 10) &&
                    (course[i].posX >= -course[i].width)) {
      course[i].visible = true;
    } else if (course[i].posX < 0) {
      int j = new Random().nextInt(OBS_MAX);
      j = 0;
      int gap = (new Random().nextInt(3));

      course[i].getNewObstacle("/res/" + j + ".png", partList[j][0],
                                  partList[j][1], partList[j][2]);
      course[i].visible = false;
      course[i].posX = Excitebike.SCREEN_X + 10;
    } else {
      course[i].visible = false;
    }
  } // updateObstacle()

  class CollisionBox {
    protected CollisionType cType;
    protected Rectangle cRect;

    public void CollisionBox(int x, int y, int w, int h, CollisionType t) {
//      for (int i = 0; i < MAX_BOXES; i++) {
//        cType[i] = t;
//        cRect = new Rectangle(x, y, w, h);
//      }
    }

    public void setBox(CollisionType t, Rectangle r) {
  //    cType = t;
  //    cRect = r;
    }

    private void loadCollisionRect(int index) {
      
    }
  } // CollisionBox


  class Obstacle extends ExciteSprite {
    public Obstacle(String path, int x, int y, int w, int h) {
      loadImage(path);
      spriteImage = imageMap;
      posX = x;
      posY = y;
      width = w;
      height = h;

      if (posX <= Excitebike.SCREEN_X) {
        visible = true;
      } else {
        visible = false;
      }
    } // Obstacle()

    public void getNewObstacle(String path, int y, int w, int h) {
      loadImage(path);
      posY = y;
      width = w;
      height = h;
    } // getNewObstacle()

  } // Obstacle
} // Track

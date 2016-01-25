package excitebike;

import java.awt.image.BufferedImage;


public class Rider extends ExciteSprite {
  private final static int START_POS_X = Excitebike.SCREEN_X / 4;
  private final static int START_POS_Y = 100;
  private final static int JUMP_HEIGHT = 30;

  //private final static int FRAME_DELAY = 2;
  private final static int CRASH_DELAY = 10;
  private final static int WHEELIE_DELAY = 3;
  private final static int JUMP_DELAY = 1;
  private final static int COAST_DELAY = 10;
  private final static int BRAKE_DELAY = 2;

  private final static int STOP_IDX = 0;
  private final static int RUN_IDX = 1;
  private final static int LAND_IDX = 2;
  private final static int UP_IDX = 3;
  private final static int DOWN_IDX = 4;
  private final static int WHEELIE_IDX = 5;
  private final static int BACK_IDX = 6;

  private final static int TRACK_TOP = 56;
  private final static int TRACK_BOTTOM = 104;

  private final static String RIDER_IMAGE_FILE = "/res/rider.png";

  protected static enum ControlState {GAS, NEUTRAL, BRAKE};
  private ControlState ctrlState;
  protected static enum ActivityState {STOP, RUN, JUMP, CRASH, COAST,
                                       WHEELIE_UP, WHEELIE_DOWN};
  private ActivityState actState;

  private int crashTimer = CRASH_DELAY;
  private int generalTimer = WHEELIE_DELAY;
  protected int hOffset = 0;

  protected Shadow shadow = new Shadow();

  public Rider() {
    loadImage(RIDER_IMAGE_FILE);

    spriteIndex = STOP_IDX;
    setSprite(spriteIndex * 24, frameIndex * 24, 24, 24);

    posX = START_POS_X;
    posY = START_POS_Y;
    speedX = 0;
    speedY = 0;
    visible = true;
    animate = true;

    actState = ActivityState.STOP;
    ctrlState = ControlState.NEUTRAL;
  } // Rider()

  public void update() {

    switch (ctrlState) {
      case GAS:
        if (actState != ActivityState.WHEELIE_UP &&
            actState != ActivityState.WHEELIE_DOWN &&
            actState != ActivityState.JUMP) {
          actState = ActivityState.RUN;
        }
        break;
      case BRAKE:
        actState = ActivityState.STOP;
        break;
      case NEUTRAL:
        if (speedX == 0) {
          actState = ActivityState.STOP;
        } else {
          actState = ActivityState.COAST;
        }
        break;
    }

    switch (actState) {
      case STOP:
        actCoast();
        break;
      case RUN:
        actRun();
        break;
      case COAST:
        actCoast();
        break;
      case WHEELIE_UP:
        actWheelieUp();
        break;
      case WHEELIE_DOWN:
        actWheelieDown();
        break;
      case CRASH:
        actCrash();
        break;
      case JUMP:
        actJump();
        break;
      default:
        actRun();
    }

    animate();
  } // update()

  public void raiseHeight(int slope, int max) {
    if (actState != ActivityState.JUMP) {
      if (hOffset < max) {
        hOffset += slope;
      } else {
        hOffset = max;
      }
    } else {
      hOffset += slope;
    }
  } // raiseHeight()

  public void lowerHeight(int y) {
    hOffset = 0;
  }

  public void stopY() {
    if (speedX != 0) {
      spriteIndex = RUN_IDX;
    } else {
      spriteIndex = STOP_IDX;
    }
    speedY = 0;
  } // stopY()

  public void setSpriteIndex(int i) {
    spriteIndex = i;
  } // setSpriteIndex()

  public boolean getShadowVis() {
    if (shadow.visible) {
      return true;
    } else {
      return false;
    }
  }

  public BufferedImage getShadowImg() {
    return shadow.imageMap;
  }

  public ActivityState getActState() {
    return actState;
  }

 /****************
  *** CONTROLS ***
  ****************/
  public void setControlState(ControlState state) {
    ctrlState = state;
  } // setControlState()

 /******************
  *** ACTIVITIES ***
  ******************/
  public void setActivityState(ActivityState state) {
    actState = state;
  }

  private void actRun() {
    // up/down movement
    if (posY <= TRACK_TOP) {
      stopY();
      posY = TRACK_TOP + 1;
    } else if(posY >= TRACK_BOTTOM) {
      stopY();
      posY = TRACK_BOTTOM - 1;
    }

    posY += speedY;

    if (ctrlState == ControlState.GAS) {
      if (speedX >= TOP_SPEED) {
        speedX = TOP_SPEED;
      } else if (generalTimer <= 0) {
        generalTimer = COAST_DELAY;
        speedX += 4;
      } else {
        generalTimer--;
      }
    }
/*
    // start crashing if running on bales too long
    if ((posY <= TRACK_TOP + 5) || (posY >= TRACK_BOTTOM - 5)) {
      speedX = TOP_SPEED / 2;
      crashTimer--;
      if (crashTimer == 0) {
        //  System.out.println("  !!CRASHED ON THE BALES!!");
        actState = ActivityState.CRASH;
        crashTimer = CRASH_DELAY;
      }
    } else {
      crashTimer = CRASH_DELAY;
    }
*/
    if (speedY == 0 && speedX != 0) {
      spriteIndex = RUN_IDX;
    }
  } // actRun()

  private void actStop() {
    actState = ActivityState.STOP;
    spriteIndex = STOP_IDX;
    speedX = 0;
    speedY = 0;
    //  maybe enable menu only if stopped?
  } // actStop()

  private void actCrash() {
    System.out.println("CRASH");
  } // actCrash()

  private void actCoast() {
    if (speedX <= 0) {
      actStop();
      return;
    } else if (generalTimer <= 0) {
      generalTimer = COAST_DELAY;
      if (ctrlState == ControlState.BRAKE) {
        speedX -= TOP_SPEED;
      } else {
        speedX--;
      }
    } else {
      generalTimer--;
    }

    actRun();
  } // actCoast()

  public void actTurnLeft() {
    if (actState == ActivityState.WHEELIE_UP) {
      return;
    }

    if (speedY <= -2) {
      speedY = -2;
    } else {
      speedY--;
    }

    spriteIndex = UP_IDX;
  } // actTurnLeft()

  public void actTurnRight() {
    if (actState == ActivityState.WHEELIE_UP) {
      return;
    }

    if (speedY > 2) {
      speedY = 2;
    } else {
      speedY++;
    }

    spriteIndex = DOWN_IDX;
  } // actTurnRight()

  private void actWheelieUp() {
    if (spriteIndex <= BACK_IDX) {
      spriteIndex = BACK_IDX;
    }

    // balance routine for wheelie
    if (generalTimer == 0) {
      if (spriteIndex == 10) {
      //  System.out.println("  !!WHEELIE CRASH!!");
      } else {
        spriteIndex++;
        generalTimer = WHEELIE_DELAY;
      }
    } else {
      generalTimer--;
    }
  } // actWheelieUp()

  private void actWheelieDown() {
    if (speedX != 0) {
      actState = ActivityState.WHEELIE_DOWN;
    } else {
      actState = ActivityState.STOP;
    }

    if (generalTimer == 0) {
      if (spriteIndex == BACK_IDX) {
        spriteIndex = RUN_IDX;
        actState = ActivityState.RUN;
      } else {
        spriteIndex--;
        generalTimer = WHEELIE_DELAY;
      }
    } else {
      generalTimer--;
    }
  } // actWheelieDown()

  public void actJump() {
    if (hOffset != JUMP_HEIGHT) {
      spriteIndex = BACK_IDX;
      if (generalTimer == 0) {
        hOffset += 5;
        generalTimer = JUMP_DELAY;
        shadow.visible = true;
      } else {
        generalTimer--;
      }
    } else {
      hOffset = 0;
      shadow.visible = false;
      spriteIndex = RUN_IDX;
      actState = ActivityState.RUN;
    }
  } // actJump()

  private class Shadow extends ExciteSprite {
    private final static String PATH = "/res/shadow.png";

    public Shadow() {
      loadImage(PATH);
      visible = false;
      animate = false;
    }

  } // Shadow
} // Rider

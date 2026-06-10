import processing.core.PApplet;
import processing.core.PVector;

public class Shooter {
  private float xshooter;
  private float yshooter;
  private float sizeShooter;
  private PApplet canvas;
  private int color;
  private String colorType;
  private float moveY;
  private float moveX;
  private boolean isXShooting;
  
PVector position = new PVector();
PVector velocity = new PVector();

  public Shooter(PApplet c){
    xshooter = 250;
    yshooter = 735;
    sizeShooter = 30;
    canvas = c;
    isXShooting = false;
    shooterColor();
  }
    public void shooterColor(){
        float random = canvas.random(1);

    if(random < .3333333){
    colorType = "purple";
    color = canvas.color(138, 43, 226);
        }
    else if(random < .6666666 && random > 0.333333){
    colorType = "blue";
    color = canvas.color(70, 130, 180);
        }
    else{
    colorType = "yellow";
    color = canvas.color(255, 215, 0);
    }
    }
    public void display(){   
    canvas.stroke(225);
    canvas.fill(color);
    canvas.ellipse(xshooter, yshooter, sizeShooter,sizeShooter);
    
}
public String getColorShooter(){
    return colorType;
}

public void shoot(float mouseX, float mouseY) {
    if (isXShooting == false) {
        moveX = mouseX - xshooter;
        moveY = mouseY - yshooter;

        moveX = moveX / 40;
        moveY = moveY / 40;

        isXShooting = true;
    }
}
public void move() {
    if (isXShooting == true) {
        xshooter = xshooter + moveX;
        yshooter = yshooter + moveY;

        if (xshooter <= 0 || xshooter >= canvas.width) {
            moveX = -moveX;
        }
       
    }
}
public float getXShooter() {
    return xshooter;
}

public float getYShooter() {
    return yshooter;
}

public float getSizeShooter() {
    return sizeShooter;
}
public PApplet getC(){
    return canvas;
}
public void reset(){
     xshooter = 250;
    yshooter = 735;
    isXShooting = false;
    shooterColor();
    
}
}

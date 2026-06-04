import processing.core.PApplet;

public class Shooter {
  private int xshooter;
  private int yshooter;
  private int sizeShooter;
  private PApplet canvas;
  private int color;
  private String colorType;


  public Shooter(PApplet c){
    xshooter = 250;
    yshooter = 750;
    sizeShooter = 30;
    canvas = c;
  }

    public void display(){   
    canvas.stroke(225);
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
    canvas.ellipse(xshooter, yshooter, sizeShooter,sizeShooter);
}

}

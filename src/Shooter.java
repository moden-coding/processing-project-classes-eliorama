import processing.core.PApplet;

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

    public Shooter(PApplet c) { // sets up the position size and color of the shooter
        xshooter = 250;
        yshooter = 735;
        sizeShooter = 30;
        canvas = c;
        isXShooting = false; // makes sure that the shooter is set as not moving
        shooterColor();
    }

    public void shooterColor() { // same color system as the bubbles
        float random = canvas.random(1);

        if (random < .3333333) {
            colorType = "purple";
            color = canvas.color(138, 43, 226);
        } else if (random < .6666666 && random > 0.333333) {
            colorType = "blue";
            color = canvas.color(70, 130, 180);
        } else {
            colorType = "yellow";
            color = canvas.color(255, 215, 0);
        }
    }

    public void display() { 
        canvas.stroke(225); // the color of the outline is white 
        canvas.fill(color); // fills the shooter with a random  one of the three colors from above 
        canvas.ellipse(xshooter, yshooter, sizeShooter, sizeShooter); // makes the shooter on the screen

    }

    public void shoot(float mouseX, float mouseY) { // shoots the bubble in the direction that was clicked
        if (isXShooting == false) {  
            moveX = mouseX - xshooter; //horizontal distance 
            moveY = mouseY - yshooter; // vertical distance 

            moveX = moveX / 40; // the 40 divides it to move at a speed intead of just going to where the mouse was clicked
            moveY = moveY / 40;

            isXShooting = true; //the shooter is now moving 
        }
    }

    public void move() {
        if (isXShooting == true) { // once the shooter is moving 
            xshooter = xshooter + moveX; //this updates its x and y position 
            yshooter = yshooter + moveY;

            if (xshooter <= 0 || xshooter >= canvas.width) { // if it hits either side of the screen the direction of the movement flips
                moveX = -moveX; 
            }

        }
    }

    public float getXShooter() { // gets the shooters x value 
        return xshooter;
    }

    public float getYShooter() { // gets the shooters y value 
        return yshooter;
    }

    public float getSizeShooter() {// gets the shooters size
        return sizeShooter;
    }

    public PApplet getC() { 
        return canvas;
    }
    public String getColorShooter() { //gets the shooters color
        return colorType;
    }

    public void reset() { // resets the position of the shooter to where is was originally after it hits a bubble 
        xshooter = 250;
        yshooter = 735;
        isXShooting = false; // the shooter isnt moving 
        shooterColor();

    }
}

import java.util.Arrays;

import processing.core.PApplet;

public class Bubble {
private int x;
private float y;
private int size;
private PApplet canvas; 
private int color;
private String colorType;
private float speed;

public Bubble(int xPos, int yPos, PApplet c){ // this creates the bubble setting its position, size, speed, and color
    x = xPos;
    y = yPos;
    size = 30;
    canvas = c;
    speed = 0.4f;
    color = canvas.color(0,255,0);
    


float random = canvas.random(1);  // creates a random decimal between 0 and 1

    if(random < .3333333){ // if the decimal is less then 0.3 than the color will be purple
    colorType = "purple";
    color = canvas.color(138, 43, 226);
        }
    else if(random < .6666666 && random > 0.333333){ // if the decimal is between 0.3 and 0.6 than the color will be blue
    colorType = "blue";
    color = canvas.color(70, 130, 180);
        }
    else{
    colorType = "yellow";
    color = canvas.color(255, 215, 0);
    }
}


public void moveDown(){ // moves the y position of the bubble to move downwards 
    y += speed;
}

public void display(){ // creates the bubble using it assinged color and current position 
canvas.fill(color);
canvas.ellipse(x, y, size, size);

}
public float getX() { // gets x position of bubble
    return x;
}

public float getY() { // gets y position of bubble
    return y;
}

public float getSize() { //gets size
    return size;
}
public String getColor(){ // gets color 
    return colorType;
}
}

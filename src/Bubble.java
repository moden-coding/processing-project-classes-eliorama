import processing.core.PApplet;

public class Bubble {
private int x;
private float y;
private int size;
private PApplet canvas; 
private int color;
private String colorType;
private float speed;


public Bubble(int xPos, int yPos, PApplet c){
    x = xPos;
    y = yPos;
    size = 30;
    canvas = c;
    speed = 0.3f;
    color = canvas.color(0,255,0);


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



    

public void moveDown(){
    y += speed;
}

public void display(){
canvas.fill(color);
canvas.ellipse(x, y, size, size);

}
}

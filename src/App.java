import java.util.ArrayList;

import processing.core.*;

public class App extends PApplet {
    ArrayList<Bubble> bubbles;
    ArrayList<Shooter> shooters;
    Shooter shooter;
    int scene;
    int linex;
    int liney;

    public static void main(String[] args) {
        PApplet.main("App");
    }

    public void setup() {
        scene = 0;
        bubbles = new ArrayList<Bubble>();
        shooters = new ArrayList<Shooter>();
        shooter = new Shooter(this);

        for (int row = 0; row < 90; row++) {
            for (int column = 0; column < 10; column++) {

                int x = 40 + column * 45;
                int y = row * -40;

                if (row % 2 == 1) {
                    x += 22;
                }

                bubbles.add(new Bubble(x, y, this));
            }
        }

    }

    public void settings() {
        size(500, 800);
    }

    public void draw() {

        background(10, 20, 80);

        for (Bubble b : bubbles) {
            b.display();
            b.moveDown();
        }
        shooter.display();
        shootingLine();
       
    }

    public void shootingLine() {
    float x = 0;
    float y = 0;
    float linex = 250;
    float liney = 735;
    float m = (mouseX-linex)/(mouseY-liney);
    float b = liney - m*linex;
    // System.out.println(b);

    if(mouseX < width/2){
        y = b;
        y = m*x+b;

    }
    else{
        y = m*width + b;
        x = width;
        y = m*x+b;

    }

    line(linex,liney,x,y);
    
     
    }
}

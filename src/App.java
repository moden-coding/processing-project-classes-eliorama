import java.util.ArrayList;
import java.util.Arrays;
import processing.core.*;

public class App extends PApplet {
    ArrayList<Bubble> bubbles;
    ArrayList<Shooter> shooters;
    Shooter shooter;
    int scene;
    int linex;
    int liney;
    float x;
    float y;
    float shootx;
    float shooty;
    float moveX;
    float moveY;
    boolean isXshooting = false;
    boolean flying = false;

    public static void main(String[] args) {
        PApplet.main("App");
    }

    public void setup() {
        scene = 0;
        bubbles = new ArrayList<Bubble>();
        shooters = new ArrayList<Shooter>();
        // shooters.add(new Shooter(this));
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
        if (scene == 0) {
            background(255);
            textSize(60);
            fill(0, 140, 140);
            textAlign(CENTER);
            text("Bubble Shooter", width / 2, height / 2);
            fill(0, 155, 155);
            textSize(30);
            text("press return to begin", width / 2, 450);
            keyPressed();
        }

        if (scene == 1) {
            background(10, 20, 80);

            for (Bubble b : bubbles) {
                b.display();
                b.moveDown();
            }
            
            if (flying == false) {
                shootingLine();

            }
            shooter.move();
            shooter.display();

            for (int i = 0; i < bubbles.size(); i++) {
                Bubble b = bubbles.get(i);
                float distance = dist(shooter.getXShooter(), shooter.getYShooter(), b.getX(), b.getY());

                if (distance <= 30) {
                   
                    if (shooter.getColorShooter().equals(b.getColor())) {
                        for(Bubble check: bubbles){

                        }
                        bubbles.remove(i);
                        
                    }
                    shooter.reset();
                    flying = false;
                    shooter.display();
                        break;
                } 

            
            

            }

        }

    }
    public void checkAndPop(){

    }

    public void mousePressed() {
        if(flying == false){
            flying = true;
            shooter.shoot(mouseX, mouseY);
            // isXshooting = true;
        }


        
    }
        public void mouseReleased() {
            isXshooting = false;
            for (int i = 0; i < shooters.size(); i++) {
                shooter.shoot(mouseX, mouseY);
            }
        
    }

    public void keyPressed() {
        if (scene == 0) {
            if (key == ENTER || keyCode == RETURN) {
                scene = 1;
            }
        }

        if(key == 'p'){
            System.out.println();
        }
    }

    public void shootingLine() {
        float x = 0;
        float y = 0;
        float bounceX = 0;
        float bounceY = 0;
        float linex = 250;
        float liney = 735;
        float m = (mouseY - liney) / (mouseX - linex);
        float b = liney - m * linex;

        if (mouseX < width / 2) {
            y = b;
            y = m * x + b;
            bounceX = width;
            bounceY = (-m) * bounceX + (y - (-m) * x);

        } else {
            y = m * width + b;
            x = width;
            y = m * x + b;
            bounceX = 0;
            bounceY = (-m) * bounceX + (y - (-m) * x);
        }
        line(linex, liney, x, y);
        line(x, y, bounceX, bounceY);

    }

}

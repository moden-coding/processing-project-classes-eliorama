import java.util.ArrayList;
import java.util.Scanner;

import processing.core.*;
import java.nio.file.Paths;

public class App extends PApplet {
    ArrayList<Bubble> bubbles;
    ArrayList<Bubble> pops;
    ArrayList<Shooter> shooters;
    ArrayList<Shooter> shoot;
    Shooter shooter;
    boolean homeScene;
    boolean playingScene;
    boolean gameOverScene;
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
    boolean saveScore = false;
    int highscore;
    int score;

    public static void main(String[] args) {
        PApplet.main("App");
    }

    public void setup() {
        ArrayList<String> scores = new ArrayList<>();
        try (Scanner scanner = new Scanner(Paths.get("highscore.txt"))) {

            if (scanner.hasNextLine()) {
                highscore = Integer.valueOf(scanner.nextLine());
            }
               while (scanner.hasNextLine()) {
        // scores.add(Integer.valueOf(scanner.nextLine()));
    }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        homeScene = true;
        playingScene = false;
        gameOverScene = false;
        bubbles = new ArrayList<Bubble>();
        shooters = new ArrayList<Shooter>();
        shooter = new Shooter(this);
        score = 0;

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
        if (homeScene == true) {
            background(255);
            textSize(60);
            fill(0, 140, 140);
            textAlign(CENTER);
            text("Bubble Shooter", width / 2, height / 2);
            fill(0, 155, 155);
            textSize(30);
            text("press return to begin", width / 2, 450);
        }

        if (playingScene == true) {
            background(10, 20, 80);

            for (Bubble b : bubbles) {
                b.display();
                b.moveDown();
                if (b.getY() >= 715) {
                    playingScene = false;
                    gameOverScene = true;
                }

            }

            if (flying == false) {
                shootingLine();

            }
            shooter.move();
            shooter.display();

            for (int i = 0; i < bubbles.size(); i++) {
                Bubble b = bubbles.get(i);
                float distance = dist(shooter.getXShooter(), shooter.getYShooter(), b.getX(), b.getY());

                if (distance <= b.getSize()) {

                    if (shooter.getColorShooter().equals(b.getColor())) {
                        popChain(b);
                        bubbles.remove(i);

                    }
                    shooter.reset();
                    flying = false;
                    shooter.display();
                    break;
                }

            }

        }

        if (gameOverScene == true) {
            endgameOver();
        }

    }
    public void popChain(Bubble b){
        String targetColor = b.getColor();
        ArrayList<Bubble> toVisit = new ArrayList<>(); //make the arraylist of all of the bubbles worth visiting 
        ArrayList<Bubble> toPop = new ArrayList<>();   // list of what the bubbles that need to get popped 
        
        toVisit.add(b);
        while(toVisit.size() > 0){
            int lastPos = toVisit.size()-1;         // increasing the toVisit list size over time
            Bubble current = toVisit.get(lastPos);      
            toVisit.remove(lastPos); 

            if(!current.getColor().equals(targetColor)){  // if its not the right color then skip it 
                continue;
            }

            if(toPop.contains(current)){ // if its already on the list then skip it 
                continue;
            }
            toPop.add(current);  

            for(Bubble bubble: bubbles){//looking through to check what should be visted next to then restart the process  
                float distance = dist(current.getX(), current.getY(), bubble.getX(), bubble.getY());

                if(bubble != current && distance <= 30){
                    // if(bubble.getColor().equals(targetColor)) {
                    if(!toPop.contains(bubble)) {
                      toVisit.add(bubble);
                     score = score+1;
                     if(highscore < score){
                        highscore = score;
                     }

                // }
                
            }
        }
        }
        }
        for(int i = 0; i < toPop.size(); i++){
            bubbles.remove(toPop.get(i));
        }
    }   
    
    public void endgameOver() {

        background(0);
        textAlign(CENTER);
        fill(70, 130, 180);
        textSize(60);
        text("Game Over", width / 2, height / 2);
        textSize(30);
        fill(255);
        text("Press \"R\" to restart", width / 2, height / 2 + 50);
        fill(255);
        textSize(20);
        text("Your score: "+ score, 70, 20);
        fill(138, 43, 226);
        textSize(25);
        text("Highscore: "+ highscore, 80, 40);

    }

    public void mousePressed() {
        if (flying == false) {
            flying = true;
            shooter.shoot(mouseX, mouseY);
        }

    }

    public void mouseReleased() {
        isXshooting = false;
        for (int i = 0; i < shooters.size(); i++) {
            shooter.shoot(mouseX, mouseY);
        }

    }

    public void keyPressed() {
        if (homeScene == true) {
            if (key == ENTER || keyCode == RETURN) {
                homeScene = false;
                playingScene = true;
            }
        }

        if (gameOverScene == true && key == 'r' || key == 'R') {
            System.out.println("print r");
            restart();

        }
    }

    public void restart() {
        homeScene = false;
        playingScene = true;
        gameOverScene = false;
        flying = false;
        isXshooting = false;    
        score = 0;

        background(10, 20, 80);
        bubbles = new ArrayList<Bubble>();

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
        shooter = new Shooter(this);
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

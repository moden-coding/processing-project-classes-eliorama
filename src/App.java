import java.util.ArrayList;
import java.util.Scanner;

import processing.core.*;

import java.io.PrintWriter;
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

    boolean flying = false;
    boolean saveScore = false;

    int highscore;
    int score;

    public static void main(String[] args) {
        PApplet.main("App");
    }

    public void setup() {// sets up the value and creation of the origional arraylists
       

        homeScene = true;
        playingScene = false;
        gameOverScene = false;
        readhighscore();

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
        size(500, 800); // size of screen
    }

    public void draw() {
        if (homeScene == true) { // if its the opening scene of the game then put all of the text
            background(255);
            textSize(60);
            fill(0, 140, 140);
            textAlign(CENTER);
            text("Bubble Shooter", width / 2, height / 2);
            fill(0, 155, 155);
            textSize(30);
            text("press return to begin", width / 2, 450);
        }

        if (playingScene == true) { // if its the playing scene
            background(10, 20, 80);

            for (Bubble b : bubbles) {
                b.display(); // makes the bubbles show up
                b.moveDown(); // method that makes them move
                if (b.getY() >= 700) { // if the y value of the bubbles of the arraylist exeedes 715 then it switches
                                       // to the game over scene
                    playingScene = false;
                    gameOverScene = true;
                }

            }

            if (flying == false) {
                shootingLine(); // if the ball is not in the air then the line shows up

            }
            shooter.move(); // makes the shotter move upwards
            shooter.display(); // makes the shooter on the screen

            for (int i = 0; i < bubbles.size(); i++) {
                Bubble b = bubbles.get(i); // gets the current bubble from the arraylist to be checked for collisions
                float distance = dist(shooter.getXShooter(), shooter.getYShooter(), b.getX(), b.getY()); // finds the
                                                                                                         // distance
                                                                                                         // between the
                                                                                                         // x and y
                                                                                                         // points of
                                                                                                         // the shooter
                                                                                                         // and bubbles

                if (distance <= b.getSize()) { // if the shooter and bubble are touching

                    if (shooter.getColorShooter().equals(b.getColor())) { // and they have the same color
                        popChain(b); // then it pops them and runs through to check the rest around it

                    }
                    shooter.reset(); // creates a new shooter on the bottom
                    flying = false; // the shooter is no longer flying
                    shooter.display(); // calls the shooters display method
                    break;
                }

            }

        }

        if (gameOverScene == true) { // if the game is lost then it calls the gameover scene
            endgameOver();
        }

    }

    public void popChain(Bubble b) {
        String targetColor = b.getColor();

        ArrayList<Bubble> toVisit = new ArrayList<>(); // make the arraylist of all of the bubbles worth visiting
        ArrayList<Bubble> toPop = new ArrayList<>(); // list of what the bubbles that need to get popped

        toVisit.add(b);
        while (toVisit.size() > 0) {
            int lastPos = toVisit.size() - 1; // increasing the toVisit list size over time
            Bubble current = toVisit.get(lastPos);
            toVisit.remove(lastPos);

            if (!current.getColor().equals(targetColor)) { // if its not the right color then skip it
                continue;
            }

            if (toPop.contains(current)) { // if its already on the list then skip it
                continue;
            }
            toPop.add(current);

            for (Bubble bubble : bubbles) {// looking through to check what should be visted next to then restart the
                                           // process
                float distance = dist(current.getX(), current.getY(), bubble.getX(), bubble.getY());

                if (bubble != current && distance < 60) {
                    if (!toPop.contains(bubble)) {
                        toVisit.add(bubble);
                        score = score + 1;
                        if (highscore < score) {
                        highscore = score;
                        savehighscore();
                        }

                    }
                }

            }
        }
        for (int i = 0; i < toPop.size(); i++) {
            bubbles.remove(toPop.get(i));
            delay(5);
        }
    }

    public void endgameOver() { // if the player lost then this creates the gameover screen

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
        text("Your score: " + score, 70, 20);
        fill(138, 43, 226);
        textSize(25);
        text("Highscore: " + highscore, 80, 40);

    }

    public void mousePressed() {
        if (flying == false) { // if the ball is not flying but the mouse is pressed then the ball begins to
                               // fly and followers the path of the mouses position
            flying = true;
            shooter.shoot(mouseX, mouseY);
        }

    }

    public void keyPressed() { // when key is pressed what happens
        if (homeScene == true) { // if it is the homescene and enter/return is pressed then the scene changes to
                                 // the playing scene
            if (key == ENTER || keyCode == RETURN) {
                homeScene = false;
                playingScene = true;
            }
        }

        if (gameOverScene == true && key == 'r' || key == 'R') { // if it is the game over scene and the key r is
                                                                 // pressed then it switches back to the playing scene
            restart(); // this calls the method restart which creates a new scene of the playing one
                       // isntead of picking up where the game was lost

        }
    }

    public void restart() { // turns game playing scene on and the others off
        homeScene = false;
        playingScene = true;
        gameOverScene = false;
        flying = false; // the shooter is not released
        score = 0; // restarts the score

        background(10, 20, 80);
        bubbles = new ArrayList<Bubble>(); // creates a empty new arraylist for the bubbles to restart at the top

        for (int row = 0; row < 90; row++) {
            for (int column = 0; column < 10; column++) {

                int x = 40 + column * 45;
                int y = row * -40;

                if (row % 2 == 1) { // chat gpt helped me make the rows not striaght in order to look more like the
                                    // origional game and allow the ball to fly between two slots
                    x += 22;
                }

                bubbles.add(new Bubble(x, y, this)); // creates a new bubble and adds it to the arraylist
            }
        }
        shooter = new Shooter(this); // puts the shooter on the bottom of the screen with a random color
    }

    public void shootingLine() { // creates the line going from the shooter and aiming at the bubbles
        float x = 0;
        float y = 0;
        float bounceX = 0; // stores the x cordinate where the line will aim after hitting the wall
        float bounceY = 0; // stores the y cordinate where the line will aim after hitting the wall
        float linex = 250; // where the line origonates from
        float liney = 735;
        float m = (mouseY - liney) / (mouseX - linex); // calculates the slope of the line
        float b = liney - m * linex; // finds the y intercept on the line

        if (mouseX < width / 2) { // if its being aimed to the left it checks the y value when x is 0
            y = b;
            y = m * x + b;
            bounceX = width; // the bounce end point on the other side of the screen
            bounceY = (-m) * bounceX + (y - (-m) * x); // makes the reflected line, flipping the direction

        } else {
            y = m * width + b; // if its on the right side then check what the y is when x is the width
            x = width;
            y = m * x + b;
            bounceX = 0; // changes the line to go towards the opposite wall
            bounceY = (-m) * bounceX + (y - (-m) * x);
        }
        line(linex, liney, x, y); // draws the line
        line(x, y, bounceX, bounceY); // draws the line if it bounces off the wall

    }

   public void savehighscore() {
    try (PrintWriter writer = new PrintWriter("src/highscore.txt")) {
        writer.println(highscore);
        System.out.println("Highscore saved: " + highscore);
    } catch (Exception e) {
        System.out.println("Error saving highscore");
        e.printStackTrace();
    }
}

   public void readhighscore() {
    try (Scanner scanner = new Scanner(Paths.get("src/highscore.txt"))) {
        if (scanner.hasNextLine()) {
            String row = scanner.nextLine();
            highscore = Integer.valueOf(row);
        }
        System.out.println("Highscore read: " + highscore);
    } catch (Exception e) {
        System.out.println("Error reading highscore");
        e.printStackTrace();
    }
}
}

package gamecode;

import processing.core.PApplet;
import processing.core.PVector;
import java.util.ArrayList;

public class GameManager {
    PApplet p;
    Player player;
    ArrayList<Zombie> zombies;
    int spawnInterval = 120;
    int score = 0;
    boolean gameOver = false;  // ✅ flag instead of using p.surface

    public GameManager(PApplet p) {
        this.p = p;
        player = new Player(p);
        zombies = new ArrayList<>();
        spawnWave();
    }

    public void update() {
        if (p.frameCount % spawnInterval == 0) {
            spawnWave();
        }

        player.update();

        for (Zombie z : zombies) {
            z.update(player);
            if (Utils.circleCollide(player.pos, player.radius, z.pos, z.radius)) {
                // simple collision handling
                PVector push = PVector.sub(z.pos, player.pos);
                player.pos.sub(push.mult(0.1f));

                gameOver = true; // ✅ signal back instead of touching MainSketch
            }
        }

        if (!gameOver && p.frameCount % 60 == 0) {
            score++;
        }
    }

    public void display() {
        player.display();
        for (Zombie z : zombies) {
            z.display();
        }
        p.fill(0);
        p.textAlign(PApplet.LEFT, PApplet.TOP);
        p.text("Score: " + score, 10, 10);
    }

    public void reset() {
        player.reset();
        zombies.clear();
        score = 0;
        gameOver = false;
        spawnWave();
    }

    void spawnWave() {
        for (int i = 0; i < 5; i++) {
            zombies.add(new Zombie(p));
        }
    }
}

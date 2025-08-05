package gamecode;

import processing.core.PApplet;
import processing.core.PVector;
import java.util.ArrayList;

public class GameManager {
    PApplet p;
    public Player player;
    ArrayList<Zombie> zombies;
    int spawnInterval = 120;
    public int highScore = 0;   // ✅ track best score
    int score = 0;
    boolean gameOver = false;

    // ✅ Damage cooldown (frames)
    int damageCooldown = 30; // ~0.5 sec at 60fps
    int lastDamageFrame = -999;

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

            // ✅ Collision check
            if (Utils.circleCollide(player.pos, player.radius, z.pos, z.radius)) {
                // Only apply damage if cooldown expired
                if (p.frameCount - lastDamageFrame > damageCooldown) {
                    player.takeDamage(10);
                    lastDamageFrame = p.frameCount;
                }

                if (player.isDead()) {
                    gameOver = true;
                }
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

        // ✅ Score
        p.fill(0);
        p.textAlign(PApplet.LEFT, PApplet.TOP);
        p.text("Score: " + score, 10, 10);

        // ✅ Health bar
        float barWidth = 200;
        float barHeight = 20;
        float healthRatio = (float) player.health / player.maxHealth;

        p.noStroke();
        p.fill(150);
        p.rect(10, 40, barWidth, barHeight);

        p.fill(255, 0, 0);
        p.rect(10, 40, barWidth * healthRatio, barHeight);

        p.stroke(0);
        p.noFill();
        p.rect(10, 40, barWidth, barHeight);
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

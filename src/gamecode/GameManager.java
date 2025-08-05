package gamecode;

import processing.core.PApplet;
import processing.core.PVector;
import java.util.ArrayList;

public class GameManager {
    PApplet p;
    public Player player;
    ArrayList<Zombie> zombies;

    ArrayList<PowerUp> powerUps;
    int powerUpSpawnInterval = 600; // 600: spawn every ~10 sec

    int spawnInterval = 120;
    public int score = 0;
    public int highScore = 0;   // ✅ track best score
    public boolean gameOver = false;

    int damageCooldown = 30;
    int lastDamageFrame = -999;

    public GameManager(PApplet p) {
        this.p = p;
        player = new Player(p);
        zombies = new ArrayList<>();
        powerUps = new ArrayList<>();
        spawnWave();
    }

    public void update() {
        if (p.frameCount % spawnInterval == 0) {
            spawnWave();
        }
        if (p.frameCount % powerUpSpawnInterval == 0) {
            spawnRandomPowerUp();
        }

        player.update();

        for (Zombie z : zombies) {
            z.update(player);

            if (Utils.circleCollide(player.pos, player.radius, z.pos, z.radius)) {
                if (p.frameCount - lastDamageFrame > damageCooldown) {
                    player.takeDamage(10);
                    lastDamageFrame = p.frameCount;
                }

                if (player.isDead()) {
                    // ✅ update highscore when player dies
                    if (score > highScore) {
                        highScore = score;
                    }
                    gameOver = true;
                }
            }
        }

        // check powerup collection
        for (int i = powerUps.size() - 1; i >= 0; i--) {
            PowerUp pu = powerUps.get(i);
            if (pu.checkCollision(player)) {
                pu.applyEffect(player);
                powerUps.remove(i); // remove after pickup
            }
        }

        if (!gameOver && p.frameCount % 60 == 0) {
            score++;
        }
    }

    public void display() {
        player.display();
        for (Zombie z : zombies) z.display();
        for (PowerUp pu : powerUps) pu.display();

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

    private void spawnRandomPowerUp() {
        String[] types = {"heart", "speed", "shield"};
        int idx = (int) p.random(types.length);
        powerUps.add(new PowerUp(p, types[idx]));
    }

    void spawnWave() {
        for (int i = 0; i < 5; i++) {
            zombies.add(new Zombie(p));
        }
    }
}

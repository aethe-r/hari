package gamecode;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Player {
    PApplet p;
    public PVector pos;
    public float speed = 4;
    public float radius = 15;

    // ✅ health system
    public int maxHealth = 100;
    public int health = maxHealth;

    private boolean up, down, left, right;
    private PImage sprite;
    private boolean facingLeft = true; // default facing left

    public Player(PApplet p) {
        this.p = p;
        pos = new PVector(p.width / 2f, p.height / 2f);
        sprite = p.loadImage("assets/singleImages/player.png");
    }

    public void reset() {
        pos.set(p.width / 2f, p.height / 2f);
        health = maxHealth; // reset health
    }

    // Take damage
    public void takeDamage(int dmg) {
        health -= dmg;
        if (health < 0) health = 0;
    }

    public boolean isDead() {
        return health <= 0;
    }

    // Handle keys
    public void handleKeyPressed(char key, int keyCode) {
        char k = Character.toLowerCase(key);
        if (k == 'w' || keyCode == PApplet.UP) up = true;
        if (k == 's' || keyCode == PApplet.DOWN) down = true;
        if (k == 'a' || keyCode == PApplet.LEFT) { left = true; facingLeft = true; }
        if (k == 'd' || keyCode == PApplet.RIGHT) { right = true; facingLeft = false; }
    }

    public void handleKeyReleased(char key, int keyCode) {
        char k = Character.toLowerCase(key);
        if (k == 'w' || keyCode == PApplet.UP) up = false;
        if (k == 's' || keyCode == PApplet.DOWN) down = false;
        if (k == 'a' || keyCode == PApplet.LEFT) left = false;
        if (k == 'd' || keyCode == PApplet.RIGHT) right = false;
    }

    public void update() {
        PVector dir = new PVector();
        if (up) dir.y -= 1;
        if (down) dir.y += 1;
        if (left) dir.x -= 1;
        if (right) dir.x += 1;

        if (dir.magSq() > 0) {
            dir.normalize().mult(speed);
            pos.add(dir);
        }

        pos.x = Utils.clamp(pos.x, radius, p.width - radius);
        pos.y = Utils.clamp(pos.y, radius, p.height - radius);
    }

    public void display() {
        p.pushMatrix();
        p.translate(pos.x, pos.y);
        if (facingLeft) {
            p.scale(-1, 1); // flip horizontally
        }
        p.imageMode(PApplet.CENTER);
        p.image(sprite, 0, 0, radius * 3, radius * 3);
        p.popMatrix();
    }
}

package gamecode;

import processing.core.PApplet;
import processing.core.PVector;

public class Player {
    PApplet p;
    public PVector pos;
    public float speed = 4;
    public float radius = 15;

    // ✅ track key states
    private boolean up, down, left, right;

    public Player(PApplet p) {
        this.p = p;
        pos = new PVector(p.width / 2f, p.height / 2f);
    }

    public void reset() {
        pos.set(p.width / 2f, p.height / 2f);
    }

    // ✅ call this from MainSketch.keyPressed()
    public void handleKeyPressed(char key, int keyCode) {
        char k = Character.toLowerCase(key);
        if (k == 'w' || keyCode == PApplet.UP) up = true;
        if (k == 's' || keyCode == PApplet.DOWN) down = true;
        if (k == 'a' || keyCode == PApplet.LEFT) left = true;
        if (k == 'd' || keyCode == PApplet.RIGHT) right = true;
    }

    // ✅ call this from MainSketch.keyReleased()
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

        // clamp inside screen
        pos.x = Utils.clamp(pos.x, radius, p.width - radius);
        pos.y = Utils.clamp(pos.y, radius, p.height - radius);
    }

    public void display() {
        p.fill(0, 0, 255);
        p.ellipse(pos.x, pos.y, radius * 2, radius * 2);
    }
}

package gamecode;

import processing.core.PApplet;
import processing.core.PVector;

public class Player {
    PApplet p;
    public PVector pos;
    public float speed = 4;
    public float radius = 15;

    public Player(PApplet p) {
        this.p = p;
        pos = new PVector(p.width / 2f, p.height / 2f);
    }

    public void reset() {
        pos.set(p.width / 2f, p.height / 2f);
    }

    public void update() {
        PVector dir = new PVector();
        if (p.keyPressed) {
            if (p.key == 'w' || p.keyCode == PApplet.UP) dir.y -= 1;
            if (p.key == 's' || p.keyCode == PApplet.DOWN) dir.y += 1;
            if (p.key == 'a' || p.keyCode == PApplet.LEFT) dir.x -= 1;
            if (p.key == 'd' || p.keyCode == PApplet.RIGHT) dir.x += 1;
        }

        if (dir.magSq() > 0) {
            dir.normalize().mult(speed);
            pos.add(dir);
        }

        pos.x = Utils.clamp(pos.x, radius, p.width - radius);
        pos.y = Utils.clamp(pos.y, radius, p.height - radius);
    }

    public void display() {
        p.fill(0, 0, 255);
        p.ellipse(pos.x, pos.y, radius * 2, radius * 2);
    }
}

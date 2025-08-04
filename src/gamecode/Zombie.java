package gamecode;

import processing.core.PApplet;
import processing.core.PVector;

public class Zombie {
    PApplet p;
    PVector pos;
    float speed = 1.5f;
    float radius = 15;

    public Zombie(PApplet p) {
        this.p = p;
        respawn();
    }

    public void update(Player player) {
        PVector dir = PVector.sub(player.pos, pos);
        if (dir.magSq() > 0) {
            dir.normalize().mult(speed);
            pos.add(dir);
        }
    }

    public void display() {
        p.fill(0, 255, 0);
        p.ellipse(pos.x, pos.y, radius * 2, radius * 2);
    }

    public void respawn() {
        pos = new PVector(p.random(p.width), p.random(p.height));
        // Later you might want to ensure it doesn't spawn right on top of the player
    }
}

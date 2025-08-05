package gamecode;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import java.util.Random;

public class Zombie {
    PApplet p;
    PVector pos;
    float speed = 1.5f;
    float radius = 15;

    private PImage sprite;
    private boolean facingLeft = true;

    public Zombie(PApplet p) {
        this.p = p;
        // random zombie sprite
        int choice = new Random().nextInt(2) + 1;
        sprite = p.loadImage("assets/singleImages/zombie-" + choice + ".png");
        respawn();
    }

    public void update(Player player) {
        PVector dir = PVector.sub(player.pos, pos);
        if (dir.magSq() > 0) {
            dir.normalize().mult(speed);
            pos.add(dir);
        }
        facingLeft = dir.x <= 0; // if moving left, face left
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

    public void respawn() {
        pos = new PVector(p.random(p.width), p.random(p.height));
    }
}

package gamecode;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import java.util.Random;

public class PowerUp {
    PApplet p;
    PVector pos;
    float radius = 15;
    String type;   // e.g. "heart", "speed"
    PImage sprite;

    public PowerUp(PApplet p, String type) {
        this.p = p;
        this.type = type;
        this.pos = new PVector(
            p.random(radius, p.width - radius),
            p.random(radius, p.height - radius)
        );
        loadSprite();
    }

    private void loadSprite() {
        switch (type) {
            case "heart":
                sprite = p.loadImage("assets/singleImages/powerup-heart.png");
                break;
            case "speed":
                sprite = p.loadImage("assets/singleImages/powerup-speed.png");
                break;
            case "shield":
                sprite = p.loadImage("assets/singleImages/powerup-shield.png");
                break;
            default:
                sprite = p.loadImage("assets/singleImages/powerup-heart.png");
                // sprite = p.loadImage("assets/singleImages/powerup-generic.png");
                break;
        }
    }

    public void display() {
        if (sprite != null) {
            p.imageMode(PApplet.CENTER);
            p.image(sprite, pos.x, pos.y, radius * 2, radius * 2);
        } else {
            p.fill(255, 200, 0);
            p.ellipse(pos.x, pos.y, radius * 2, radius * 2);
        }
    }

    public boolean checkCollision(Player player) {
        return Utils.circleCollide(player.pos, player.radius, pos, radius);
    }

    public void applyEffect(Player player) {
        switch (type) {
            case "heart":
                player.health = Math.min(player.maxHealth, player.health + 25);
                break;
            case "speed":
                player.speed += 2;
                break;
            case "shield":
                // temporary bonus health or invincibility
                player.health = Math.min(player.maxHealth, player.health + 50);
                break;
        }
    }
}

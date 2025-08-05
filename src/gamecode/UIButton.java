package gamecode;

import processing.core.PApplet;

public class UIButton {
    PApplet p;
    float x, y, w, h;
    String label;

    public UIButton(PApplet p, float x, float y, float w, float h, String label) {
        this.p = p;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.label = label;
    }

    public void display() {
        // Highlight if hovering
        if (isHovering()) {
            p.fill(200, 50, 50);
        } else {
            p.fill(180);
        }
        p.stroke(0);
        p.rectMode(PApplet.CENTER);
        p.rect(x, y, w, h, 10);

        p.fill(0);
        p.textAlign(PApplet.CENTER, PApplet.CENTER);
        p.text(label, x, y);
    }

    public boolean isHovering() {
        return (p.mouseX > x - w / 2 && p.mouseX < x + w / 2 &&
                p.mouseY > y - h / 2 && p.mouseY < y + h / 2);
    }

    public boolean isClicked() {
        return isHovering() && p.mousePressed;
    }
}

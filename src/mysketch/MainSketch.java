package mysketch;
import processing.core.PApplet;

public class MainSketch extends PApplet {

    public void settings() {
        size(600, 400);
    }

    public void setup() {
        background(100);
    }

    public void draw() {
        ellipse(mouseX, mouseY, 50, 50);
    }

    public static void main(String[] args) {
        PApplet.main("MainSketch");
    }
}

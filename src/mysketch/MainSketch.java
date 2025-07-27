package mysketch;

import processing.core.PApplet;
import processing.event.MouseEvent;

public class MainSketch extends PApplet {

    // Colors & brush
    int bgColor     = color(240);
    int brushColor  = color(0);
    float brushSize = 12;

    boolean showHUD = true;

    public void settings() {
        size(800, 600);
    }

    public void setup() {
        background(bgColor);
        smooth();
        strokeCap(ROUND);
        noFill();
    }

    public void draw() {
        // Nothing continuous needed; drawing happens in mouseDragged().
        if (showHUD) drawHUD();
    }

    // Draw while dragging
    public void mouseDragged() {
        stroke(brushColor);
        strokeWeight(brushSize);
        line(pmouseX, pmouseY, mouseX, mouseY);
    }

    // Mouse wheel = brush size
    public void mouseWheel(MouseEvent event) {
        float e = event.getCount();         // negative = up
        brushSize = constrain(brushSize - e, 1, 200);
    }

    // Keyboard shortcuts
    public void keyPressed() {
        switch (key) {
            case '+': case '=': brushSize = min(brushSize + 2, 200); break;
            case '-': case '_': brushSize = max(brushSize - 2, 1);   break;
            case 'c': case 'C': background(bgColor);                 break;
            case 's': case 'S': saveFrame("savedImages/paint-####.png");          break;
            case 'h': case 'H': showHUD = !showHUD;                   break;
            // Quick color picks
            case '1': brushColor = color(0);                 break;   // black
            case '2': brushColor = color(255);               break;   // white
            case '3': brushColor = color(255, 0, 0);         break;   // red
            case '4': brushColor = color(0, 255, 0);         break;   // green
            case '5': brushColor = color(0, 0, 255);         break;   // blue
        }
    }

    // Simple on-canvas heads-up display
    void drawHUD() {
        pushStyle();
        fill(0, 150);
        noStroke();
        rect(0, 0, 240, 70);
        fill(255);
        textSize(12);
        text("Brush size: " + nf(brushSize, 1, 1) +
             "\n[+/- or mouse wheel] size" +
             "\n1-5 colors | C clear | S save | H hide HUD",
             10, 18);
        popStyle();
    }

    public static void main(String[] args) {
        PApplet.main("mysketch.MainSketch");
    }
}

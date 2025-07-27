package mysketch;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.event.MouseEvent;

public class MainSketch extends PApplet {

    // Canvas & drawing
    PGraphics canvas;

    // Colors & brush
    int bgColor = color(240);
    int brushColor = color(0);
    float brushSize = 12;

    boolean showHUD = true;

    public void settings() {
        size(800, 600);
    }

    public void setup() {
        canvas = createGraphics(width, height);
        clearCanvas();
        smooth();
        strokeCap(ROUND);
        noFill();
    }

    public void draw() {
        background(bgColor);
        image(canvas, 0, 0);

        if (showHUD) {
            drawHUD();
        }
    }

    public void mouseDragged() {
        canvas.beginDraw();
        canvas.stroke(brushColor);
        canvas.strokeWeight(brushSize);
        canvas.line(pmouseX, pmouseY, mouseX, mouseY);
        canvas.endDraw();
    }

    public void mouseWheel(MouseEvent event) {
        float e = event.getCount();
        brushSize = constrain(brushSize - e, 1, 200);
    }

    public void keyPressed() {
        switch (key) {
            case '+':
            case '=':
                brushSize = min(brushSize + 2, 200);
                break;
            case '-':
            case '_':
                brushSize = max(brushSize - 2, 1);
                break;
            case 'c':
            case 'C':
                clearCanvas();
                break;
            case 's':
            case 'S':
                canvas.save("savedImages/paint-" + nf(frameCount, 4) + ".png");
                break;
            case 'h':
            case 'H':
                showHUD = !showHUD;
                redraw(); // ensures screen is refreshed
                break;
            // Colors
            case '1':
                brushColor = color(0);
                break;
            case '2':
                brushColor = color(255);
                break;
            case '3':
                brushColor = color(255, 0, 0);
                break;
            case '4':
                brushColor = color(0, 255, 0);
                break;
            case '5':
                brushColor = color(0, 0, 255);
                break;
        }
    }

    void clearCanvas() {
        canvas.beginDraw();
        canvas.background(bgColor);
        canvas.endDraw();
    }

    void drawHUD() {
        pushStyle();
        fill(0, 150);
        noStroke();
        rect(0, 0, 250, 70);
        fill(255);
        textSize(12);
        text("Brush size: " + nf(brushSize, 1, 1) +
                "\n[+/- or mouse wheel] size" +
                "\n1-5 colors | C clear | S save | H toggle HUD",
                10, 18);
        popStyle();
    }

    @Override
    public void exit() {
        // Add any cleanup code here if needed
        super.exit(); // <-- This actually closes the window
    }

    public static void main(String[] args) {
        PApplet.main("mysketch.MainSketch");
    }
}

package gamecode;

import processing.core.PApplet;
import processing.core.PImage;

public class MenuManager {
    PApplet p;
    GameManager manager;

    // Buttons
    private UIButton playButton, optionsButton, quitButton;
    private UIButton backButton, resumeButton, restartButton, menuButton;

    // Backgrounds
    private PImage bgMain, bgOptions, bgPause, bgGameOver;

    public MenuManager(PApplet p, GameManager manager) {
        this.p = p;
        this.manager = manager;

        // Try loading backgrounds
        bgMain = safeLoad("assets/backgrounds/mainmenu.png");
        bgOptions = safeLoad("assets/backgrounds/options.png");
        bgPause = safeLoad("assets/backgrounds/pause.png");
        bgGameOver = safeLoad("assets/backgrounds/gameover.png");

        // buttons for menus
        playButton = new UIButton(p, p.width / 2f, p.height / 2f - 40, 160, 40, "Play");
        optionsButton = new UIButton(p, p.width / 2f, p.height / 2f + 20, 160, 40, "Options");
        quitButton = new UIButton(p, p.width / 2f, p.height / 2f + 80, 160, 40, "Quit");

        backButton = new UIButton(p, p.width / 2f, p.height - 80, 160, 40, "Back");

        resumeButton = new UIButton(p, p.width / 2f, p.height / 2f, 160, 40, "Resume");
        menuButton = new UIButton(p, p.width / 2f, p.height / 2f + 60, 160, 40, "Main Menu");

        restartButton = new UIButton(p, p.width / 2f, p.height / 2f + 60, 160, 40, "Restart");
    }

    // ✅ Safe loader
    private PImage safeLoad(String path) {
        PImage img = p.loadImage(path);
        if (img == null) {
            System.out.println("⚠️ Background not found: " + path);
        }
        return img;
    }

    public void display(GameState state) {
        switch (state) {
            case MAIN_MENU:
                drawBackground(bgMain, p.color(200, 220, 255));
                displayMainMenu();
                break;
            case OPTIONS:
                drawBackground(bgOptions, p.color(240, 240, 220));
                displayOptions();
                break;
            case PAUSE:
                drawBackground(bgPause, p.color(220, 220, 220));
                displayPause();
                break;
            case GAME_OVER:
                drawBackground(bgGameOver, p.color(255, 200, 200));
                displayGameOver();
                break;
            default:
                break;
        }
    }

    // ✅ Draws either image OR fallback rect
    private void drawBackground(PImage bg, int fallbackColor) {
        if (bg != null) {
            p.imageMode(PApplet.CORNER);
            p.image(bg, 0, 0, p.width, p.height);
        } else {
            p.background(fallbackColor);
        }
    }

    private void displayMainMenu() {
        playButton.display();
        optionsButton.display();
        quitButton.display();

        p.fill(0);
        p.textAlign(PApplet.CENTER, PApplet.CENTER);
        p.text("High Score: " + manager.highScore, p.width / 2f, p.height - 40);
    }

    private void displayOptions() {
        p.fill(0);
        p.textAlign(PApplet.CENTER, PApplet.CENTER);
        p.text("=== OPTIONS ===", p.width / 2f, 80);
        backButton.display();
    }

    private void displayPause() {
        resumeButton.display();
        menuButton.display();
    }

    private void displayGameOver() {
        p.fill(0);
        p.textAlign(PApplet.CENTER, PApplet.CENTER);
        p.text("=== GAME OVER ===", p.width / 2f, p.height / 2f - 100);
        p.text("Score: " + manager.score, p.width / 2f, p.height / 2f - 60);
        p.text("High Score: " + manager.highScore, p.width / 2f, p.height / 2f - 30);

        restartButton.display();
        menuButton.display();
    }

    public GameState handleMouseClick(GameState state) {
        switch (state) {
            case MAIN_MENU:
                if (playButton.isClicked()) {
                    manager.reset();
                    return GameState.PLAY;
                }
                if (optionsButton.isClicked()) return GameState.OPTIONS;
                if (quitButton.isClicked()) p.exit();
                break;

            case OPTIONS:
                if (backButton.isClicked()) return GameState.MAIN_MENU;
                break;

            case PAUSE:
                if (resumeButton.isClicked()) return GameState.PLAY;
                if (menuButton.isClicked()) {
                    manager.reset();
                    return GameState.MAIN_MENU;
                }
                break;

            case GAME_OVER:
                if (restartButton.isClicked()) {
                    manager.reset();
                    return GameState.PLAY;
                }
                if (menuButton.isClicked()) {
                    manager.reset();
                    return GameState.MAIN_MENU;
                }
                break;
        }
        return state;
    }
}

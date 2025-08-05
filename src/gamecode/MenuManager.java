package gamecode;

import processing.core.PApplet;

public class MenuManager {
    PApplet p;
    GameManager manager;

    public MenuManager(PApplet p, GameManager manager) {
        this.p = p;
        this.manager = manager;
    }

    public void display(GameState state) {
        switch (state) {
            case MAIN_MENU:
                displayMainMenu();
                break;
            case OPTIONS:
                displayOptions();
                break;
            case PAUSE:
                displayPause();
                break;
            case GAME_OVER:
                displayGameOver();
                break;
            default:
                break;
        }
    }

    private void displayMainMenu() {
        p.fill(0);
        p.textAlign(PApplet.CENTER, PApplet.CENTER);
        p.text("=== MAIN MENU ===", p.width / 2f, p.height / 2f - 40);
        p.text("Press ENTER to Play", p.width / 2f, p.height / 2f);
        p.text("Press O for Options", p.width / 2f, p.height / 2f + 40);
        p.text("Press ESC to Quit", p.width / 2f, p.height / 2f + 80);
    }

    private void displayOptions() {
        p.fill(0);
        p.textAlign(PApplet.CENTER, PApplet.CENTER);
        p.text("=== OPTIONS ===", p.width / 2f, p.height / 2f - 40);
        p.text("Adjust volume, graphics, etc.", p.width / 2f, p.height / 2f);
        p.text("Press M to return to Menu", p.width / 2f, p.height / 2f + 40);
    }

    private void displayPause() {
        p.fill(0);
        p.textAlign(PApplet.CENTER, PApplet.CENTER);
        p.text("=== PAUSED ===", p.width / 2f, p.height / 2f - 40);
        p.text("Press P to Resume", p.width / 2f, p.height / 2f);
        p.text("Press M for Main Menu", p.width / 2f, p.height / 2f + 40);
    }

    private void displayGameOver() {
        p.fill(0);
        p.textAlign(PApplet.CENTER, PApplet.CENTER);
        p.text("=== GAME OVER ===", p.width / 2f, p.height / 2f - 40);
        p.text("Score: " + manager.score, p.width / 2f, p.height / 2f);
        p.text("Press R to Restart", p.width / 2f, p.height / 2f + 40);
        p.text("Press M for Main Menu", p.width / 2f, p.height / 2f + 80);
    }
}

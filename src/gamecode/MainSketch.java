package gamecode;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.event.MouseEvent;

public class MainSketch extends PApplet {

  GameManager manager;

  enum GameState {
    START, PLAY, GAME_OVER
  }

  GameState gameState = GameState.START;

  public void settings() {
    size(800, 600);
  }

  public void setup() {
    manager = new GameManager(this); // ✅ pass PApplet reference
    textAlign(CENTER, CENTER);
    textSize(20);
  }

  public void draw() {
    background(255);
    switch (gameState) {
      case START:
        displayStart();
        break;
      case PLAY:
        manager.update();
        manager.display();
        break;
      case GAME_OVER:
        manager.display();
        displayGameOver();
        break;
    }
  }

  // public void keyPressed() {
  //   if (gameState == GameState.START && keyCode == ENTER) {
  //     gameState = GameState.PLAY;
  //   } else if (gameState == GameState.GAME_OVER && (key == 'r' || key == 'R')) {
  //     manager.reset();
  //     gameState = GameState.START;
  //   }
  // }
  public void keyPressed() {
      if (gameState == GameState.PLAY) {
          manager.player.handleKeyPressed(key, keyCode); // forward to Player
      }

      // existing game state controls (ENTER, R, etc.)
      if (gameState == GameState.START && keyCode == ENTER) {
          gameState = GameState.PLAY;
      } else if (gameState == GameState.GAME_OVER && (key == 'r' || key == 'R')) {
          manager.reset();
          gameState = GameState.START;
      }
  }

  public void keyReleased() {
      if (gameState == GameState.PLAY) {
          manager.player.handleKeyReleased(key, keyCode); // forward to Player
      }
  }

  public void displayStart() {
    fill(0);
    textAlign(CENTER, CENTER);
    text("Press ENTER to start", width / 2, height / 2);
  }

  public void displayGameOver() {
    fill(0);
    textAlign(CENTER, CENTER);
    text("Game Over! Press R to restart", width / 2, height / 2);
  }

  @Override
  public void exit() {
    // Add any cleanup code here if needed
    super.exit(); // <-- This actually closes the window
  }

  public static void main(String[] args) {
    PApplet.main("gamecode.MainSketch");
  }

}
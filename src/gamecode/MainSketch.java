package gamecode;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.event.MouseEvent;

public class MainSketch extends PApplet {

  GameManager manager;
  MenuManager menu;
  GameState gameState = GameState.MAIN_MENU;

  public void settings() {
    size(800, 600);
  }

  public void setup() {
    manager = new GameManager(this); // ✅ pass PApplet reference
    menu = new MenuManager(this, manager); // ✅ initialize
    textAlign(CENTER, CENTER);
    textSize(20);
  }

  public void draw() {
    background(255);

    switch (gameState) {
      case MAIN_MENU:
      case OPTIONS:
      case PAUSE:
      case GAME_OVER:
        manager.display(); // keep background visible
        menu.display(gameState);
        break;

      case PLAY:
        manager.update();
        manager.display();
        if (manager.gameOver) {
          gameState = GameState.GAME_OVER;
        }
        break;
    }
  }

  // public void keyPressed() {
  // if (gameState == GameState.START && keyCode == ENTER) {
  // gameState = GameState.PLAY;
  // } else if (gameState == GameState.GAME_OVER && (key == 'r' || key == 'R')) {
  // manager.reset();
  // gameState = GameState.START;
  // }
  // }
  @Override
  public void keyPressed() {
    switch (gameState) {
      case MAIN_MENU:
        if (keyCode == ENTER) {
          manager.reset();
          gameState = GameState.PLAY;
        } else if (key == 'o' || key == 'O') {
          gameState = GameState.OPTIONS;
        } else if (keyCode == ESC) {
          exit();
        }
        break;

      case OPTIONS:
        if (key == 'm' || key == 'M') {
          gameState = GameState.MAIN_MENU;
        }
        break;

      case PLAY:
        manager.player.handleKeyPressed(key, keyCode);
        if (key == 'p' || key == 'P') {
          gameState = GameState.PAUSE;
        }
        break;

      case PAUSE:
        if (key == 'p' || key == 'P') {
          gameState = GameState.PLAY;
        } else if (key == 'm' || key == 'M') {
          gameState = GameState.MAIN_MENU;
        }
        break;

      case GAME_OVER:
        if (key == 'r' || key == 'R') {
          manager.reset();
          gameState = GameState.PLAY;
        } else if (key == 'm' || key == 'M') {
          gameState = GameState.MAIN_MENU;
        }
        break;
    }
  }

  @Override
  public void keyReleased() {
    if (gameState == GameState.PLAY) {
      manager.player.handleKeyReleased(key, keyCode);
    }
  }

  @Override
  public void mousePressed() {
    // forward mouse clicks to the menu system
    gameState = menu.handleMouseClick(gameState);
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
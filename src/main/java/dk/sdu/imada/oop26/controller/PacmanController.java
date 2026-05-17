package dk.sdu.imada.oop26.controller;

import dk.sdu.imada.oop26.model.Pacman;
import javafx.scene.input.KeyEvent;

public class PacmanController {

    private final Pacman pacman;

    public PacmanController(Pacman pacman) {
        this.pacman = pacman;
    }

    public void handleKey(KeyEvent e) {
        switch (e.getCode()) {
            case LEFT, A -> pacman.moveLeft();
            case RIGHT, D -> pacman.moveRight();
            case UP, W -> pacman.moveUp();
            case DOWN, S -> pacman.moveDown();
            default -> {
            } // Ignore other keys
        }
    }
}

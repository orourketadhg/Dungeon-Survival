package ie.ucd.tor;

import ie.ucd.tor.engine.core.systems.GameController;
import ie.ucd.tor.game.core.DungeonSurvival;

public class Main {
    public static void main(String[] args) {
        GameController game = new DungeonSurvival();
        game.play();
    }
}

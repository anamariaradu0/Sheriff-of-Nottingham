package com.tema1.players;

public abstract class PlayerFactory {
    public static Player getPlayer(final String inputPlayer) {
        switch (inputPlayer) {
            case "basic":
                return new BasicPlayer();
            case "greedy":
                return new GreedyPlayer();
            case "bribed":
                return new BribedPlayer();
            default:
                return null;
        }
    }
}

package com.tema1.main;

import com.tema1.common.GameFlow;

import java.util.ArrayList;
import java.util.Map;

public final class Main {

    public static void main(final String[] args) {
        GameInputLoader gameInputLoader = new GameInputLoader(args[0], args[1]);
        GameInput gameInput = gameInputLoader.load();

        GameFlow.getInput(gameInput);

        GameFlow.play(gameInput.getRounds());

        ArrayList<Map.Entry<String, Integer>> leaderBoard = GameFlow.getResults();

        for (Map.Entry<String, Integer> pair : leaderBoard) {
            System.out.println(pair.getValue() % 10 + " " + pair.getKey() + " " + pair.getValue() / 10);
        }
    }
}

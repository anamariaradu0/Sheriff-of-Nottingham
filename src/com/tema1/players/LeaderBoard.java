package com.tema1.players;


import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

/**
 * Clasa implementeaza interfata LeaderBoardUpdaterVisitor pentru a implementa "vizitarea"
 * fiecarui jucator in vederea adaugarii scorului acestuia in clasament.
 */
public final class LeaderBoard implements ILeaderBoard {
    @Override
    public void update(final BasicPlayer basicPlayer,
                       final ArrayList<Map.Entry<String, Integer>> leaderBoard, final int playerID) {
        leaderBoard.add(new AbstractMap.SimpleEntry<>("BASIC", basicPlayer.computeScore() * 10 + playerID));
    }

    @Override
    public void update(final GreedyPlayer greedyPlayer,
                       final ArrayList<Map.Entry<String, Integer>> leaderBoard, final int playerID) {
        leaderBoard.add(new AbstractMap.SimpleEntry<>("GREEDY", greedyPlayer.computeScore() * 10 + playerID));
    }

    @Override
    public void update(final BribedPlayer bribedPlayer,
                                  final ArrayList<Map.Entry<String, Integer>> leaderBoard, int playerID) {
        leaderBoard.add(new AbstractMap.SimpleEntry<>("BRIBED", bribedPlayer.computeScore() * 10 + playerID));
    }
}


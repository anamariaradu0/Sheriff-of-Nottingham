package com.tema1.players;

import java.util.ArrayList;
import java.util.Map;

public interface ILeaderBoard {
    void update(BasicPlayer basicPlayer, ArrayList<Map.Entry<String, Integer>> leaderBoard, int playerID);
    void update(GreedyPlayer greedyPlayer, ArrayList<Map.Entry<String, Integer>> leaderBoard, int playerID);
    void update(BribedPlayer bribedPlayer, ArrayList<Map.Entry<String, Integer>> leaderBoard, int playerID);

}

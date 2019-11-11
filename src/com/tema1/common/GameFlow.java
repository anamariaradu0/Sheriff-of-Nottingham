package com.tema1.common;

import com.tema1.goods.Goods;
import com.tema1.goods.GoodsFactory;
import com.tema1.goods.GoodsType;
import com.tema1.goods.LegalGoods;
import com.tema1.main.GameInput;
import com.tema1.players.LeaderBoard;
import com.tema1.players.Player;
import com.tema1.players.PlayerFactory;

import java.util.*;

public class GameFlow {
    private static LinkedList<Goods> deckFlow;
    private static ArrayList<Player> players;
    private static int deckSize;

    // start game by initializing deck and players
    public static void getInput(final GameInput gameInput) {
        deckFlow = new LinkedList<>();
        players = new ArrayList<>();
        List<String> names = gameInput.getPlayerNames();

        Deck.getInstance().initializeDeck(gameInput.getAssetIds());
        deckFlow = Deck.getDeckFlow();
        deckSize = deckFlow.size();

        for (String name : names) {
            players.add(PlayerFactory.getPlayer(name));
        }

    }

    public static void play(int rounds) {
        // int numTurns = rounds * players.size();
        for (int j = 0; j < rounds; ++j) {
            for (int i = 0; i < players.size(); ++i) { // pentru fiecare runda

                Player currentSheriff = players.get(i % players.size()); // fiecare player ajunge serif o data
                for (Player currentPLayer : players) {
                    if (!currentPLayer.equals(currentSheriff)) {
                        currentPLayer.drawGoods();
                        currentPLayer.merchantRole(j);
                    }
                }
                // face bine players size System.out.println(players.size() + "..//..");
                currentSheriff.sheriffRole(players);
            }

//        for (Player currentPLayer : players) {
//            currentPLayer.printMerchantStand();
//            System.out.println();
////        }
//        for (Player player : players) {
//            System.out.println(player.computeScore());
//        }

//            for (Player p : players) p.emptyStand();
        }
        computeFinalScore();
    }

    private static void computeFinalScore() {
        int pLen = players.size();

        int[][] bonusTable = new int[pLen][Constants.getMaxCardTypes() + 10];
        int posInPlayers = 0;

        for (Player currPlayer : players) {
            currPlayer.countGoods(bonusTable, posInPlayers++);
        }

//        for (int i = 0; i < bonusTable.length; ++i) {
//            for (int j = 0; j < bonusTable[i].length; ++j) {
//                System.out.print(bonusTable[i][j] + " ");
//            }
//
//            System.out.println();
//        }

        for (int j = 0; j < Constants.getMaxCardTypes() + 10; ++j) {
            int king = 0;
            int queen = 0;

            for (int i = 0; i < pLen; ++i) {
                if (bonusTable[i][j] > king) {
                    queen = king;
                    king = bonusTable[i][j];
                } else if (bonusTable[i][j] > queen && bonusTable[i][j] <= king) {
                    queen = bonusTable[i][j];
                }
            }

            //System.out.println(king + " " + queen);
            for (int i = 0; i < pLen; ++i) {
                if (bonusTable[i][j] == king && king != 0) {
                    Goods good = GoodsFactory.getInstance().getGoodsById(j);
                    if (good.getType() == GoodsType.Legal) {
                        LegalGoods legal = (LegalGoods) good;
                        players.get(i).addCoins(legal.getKingBonus());
                        // System.out.println(legal.getKingBonus());
                        king += 1;
                    }
                } else if (bonusTable[i][j] == queen && queen != 0) {
                    Goods good = GoodsFactory.getInstance().getGoodsById(j);
                    if (good.getType() == GoodsType.Legal) {
                        LegalGoods legal = (LegalGoods) good;
                        players.get(i).addCoins(legal.getQueenBonus());
                        // System.out.println(legal.getQueenBonus());
                        queen += 1;
                    }
                }

            }
        }
    }

    public static ArrayList<Map.Entry<String, Integer>> getResults() {
        ArrayList<Map.Entry<String, Integer>> leaderBoard = new ArrayList<>();
        LeaderBoard updater = new LeaderBoard();
        int playerID = 0;
        for (Player currPlayer : players) {
            currPlayer.updateLeaderBoard(updater, leaderBoard, playerID);
            ++playerID;
        }

        leaderBoard.sort((Map.Entry<String, Integer> pair1, Map.Entry<String, Integer> pair2) ->
                 new Integer(pair2.getValue() / 10).compareTo(pair1.getValue() / 10));

        return leaderBoard;
    }

}

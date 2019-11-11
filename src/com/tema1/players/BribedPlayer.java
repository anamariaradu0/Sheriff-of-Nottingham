package com.tema1.players;

import com.tema1.common.Constants;
import com.tema1.goods.Goods;
import com.tema1.goods.GoodsFactory;
import com.tema1.goods.GoodsType;
import java.util.ArrayList;
import java.util.Map;

public class BribedPlayer extends BasicPlayer {
    private Player leftPlayer = null;
    private Player rightPlayer = null;

    @Override
    public final void sheriffRole(final ArrayList<Player> players) {
        // System.out.println(super.getTotalCoins());

        //if (super.getTotalCoins() > 16) {
            // System.out.println("INTRU AICI"); // nu intra deci nu are bani
            if (leftPlayer == null) {
                findPlayer(players);
            }

            if (leftPlayer != this) {
                // daca am bani
                checkMerchant(leftPlayer);
                leftPlayer.putOnStand();
            }

            if (rightPlayer != leftPlayer) {
                // daca am bani
                checkMerchant(rightPlayer);
                //checkMerchant(rightPlayer);
                rightPlayer.putOnStand();
            }
//        } else {
//            for (Player p : players) {
//                p.putOnStand();
//            }
//        }


        for (Player p : players) {
            if (p != leftPlayer && p != rightPlayer && p.hasBribe()) {
                this.addCoins(p.getBribe());
            }
        }
    }

    private void findPlayer(final ArrayList<Player> players) {
        int bribedIndex = players.indexOf(this);

        leftPlayer = players.get(bribedIndex == 0
                ? players.size() - 1 : bribedIndex - 1);

        rightPlayer = players.get((bribedIndex + 1) % players.size());
    }

    @Override
    public final void merchantRole(int rounds) {
        ArrayList<Goods> illegals = new ArrayList<>();

        for (Goods good : super.getCurrentGoods()) {
            if (good.getType() == GoodsType.Illegal) {
                illegals.add(good);
            }
        }

        if (illegals.size() == 0 || super.getTotalCoins() < Constants.getLowestCoins()) {
            super.setBribe(0);
            super.useLegalGood();
        } else {
            int coins = super.getTotalCoins();



            int maxGoods = super.getTotalCoins() < Constants.getBigBribe() ?
                    5 : Constants.getMaxSelectedCards();
            //System.out.println(maxGoods);

            super.setDeclaredGood(GoodsFactory.getInstance().getGoodsById(Constants.getAppleId()));
            illegals.sort((final Goods g1, final Goods g2) -> g2.getProfit() - g1.getProfit());
            int nrIllegals = 0;
            for (int i = 0; i < maxGoods && i < illegals.size(); ++i) {
                Goods cGood = illegals.get(i);
                super.getBag().add(cGood);
                ++nrIllegals;
                super.getCurrentGoods().remove(cGood);
            }

            if (nrIllegals <= 2) {
                super.setBribe(Constants.getLittleBribe());
            } else {
                super.setBribe(Constants.getBigBribe());
            }

            if (nrIllegals < 8) {
                int availableCoins = 0;
                for (int i = 0; i < super.getBag().size(); ++i) {
                    availableCoins += super.getBag().get(i).getPenalty();
                }

                int penalty = 0;
                availableCoins = super.getTotalCoins() - availableCoins - 5 - super.brb();

                ArrayList<Goods> possible = new ArrayList<>();

                possible = this.checkMostProfitable(8 - super.getBag().size());
//
//                System.out.println("POSSIBLE");
//                for (int  i = 0; i < possible.size(); ++i) {
//                    System.out.print(possible.get(i).getId() + " ");
//                }
//                System.out.println();

                if (possible != null) {
                    for (int i = 0; i < possible.size(); ++i) {
                        penalty += possible.get(i).getPenalty();
                    }

                    if (penalty < availableCoins) {
                        super.getBag().addAll(possible);
                    }
                    super.getCurrentGoods().removeAll(possible);
                }
            }
        }
    }

    @Override
    public void updateLeaderBoard(final ILeaderBoard visitor,
                                  final ArrayList<Map.Entry<String, Integer>> leaderBoard, int playerID) {
        visitor.update(this, leaderBoard, playerID);
    }

}

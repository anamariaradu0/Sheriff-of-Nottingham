package com.tema1.players;

import com.tema1.common.Constants;
import com.tema1.goods.Goods;

import java.util.ArrayList;
import java.util.Map;

public class GreedyPlayer extends BasicPlayer{
//    static private int round;
//
//    GreedyPlayer() {
//        round = 1;
//    }

//    @Override
//    final int checkMerchant(Player currentMerchant) {
//        // int declaredId = currentMerchant.getDeclaredGood().getId();
//        int exchangedCoins = 0;
//
//        exchangedCoins = currentMerchant.computePenalty();
//        // System.out.println(exchangedCoins + " EXCHANGED");
//        super.addCoins(exchangedCoins);
//        currentMerchant.removeCoins(exchangedCoins);
//
//        return exchangedCoins;
//    }


    @Override
    public final void sheriffRole(final ArrayList<Player> merchants) {
        for (Player currentMerchant : merchants) {
            if (currentMerchant.equals(this)) {
                continue;
            } else {
                if (currentMerchant.hasBribe()) {
                    super.addCoins(currentMerchant.getBribe());
//                    System.out.println("BAG AFTER GIVING BRIBE");
//                    for (int i = 0; i < currentMerchant.getBag().size(); ++i) {
//                        System.out.print(currentMerchant.getBag().get(i).getId() + " ");
//                    }
//                    System.out.println();
                } else {
                    checkMerchant(currentMerchant);

                }
//                System.out.println("BAG BEFORE SHERIFF PUT");
//                for (int i = 0; i < currentMerchant.getBag().size(); ++i) {
//                    System.out.print(currentMerchant.getBag().get(i).getId() + " ");
//                }
//                System.out.println();
                currentMerchant.putOnStand();
//                System.out.println("MERCHANT STAND AFTER SHERIFF PUT");
//                Map<Goods, Integer> m = currentMerchant.getMerchantStand();
//                for (Map.Entry<Goods, Integer> e : m.entrySet()) {
//                    System.out.println(e.getKey().getId() + " " + e.getValue());
//                }
            }
        }
    }

    @Override
    public final void merchantRole(int round) {
        super.merchantRole(round);
        // System.out.println(round);

        if (round % 2 == 1 && super.getBag().size() < Constants.getMaxCards() - 2) {
            //System.out.println("INTRU AICI");
            useIllegalGood();
        }
    }

    @Override
    public void updateLeaderBoard(final ILeaderBoard visitor,
                                  final ArrayList<Map.Entry<String, Integer>> leaderBoard, int playerID) {
        visitor.update(this, leaderBoard, playerID);
    }
}

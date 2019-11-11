package com.tema1.players;

import com.tema1.goods.GoodsType;
import com.tema1.common.Constants;
import com.tema1.goods.Goods;
import com.tema1.goods.GoodsFactory;

import java.lang.reflect.Array;
import java.util.*;

public class BasicPlayer extends Player {

    @Override
    int checkMerchant(Player currentMerchant) {
//        if (currentMerchant.hasBribe()) {
//            currentMerchant.returnBribe();
//        }

        // int declaredId = currentMerchant.getDeclaredGood().getId();
        int exchangedCoins = 0;

        exchangedCoins = currentMerchant.computePenalty();
//        System.out.println(exchangedCoins + " EXCHANGED");
        super.addCoins(exchangedCoins);
        currentMerchant.removeCoins(exchangedCoins);

        return exchangedCoins;
    }

    @Override
    public void sheriffRole(final ArrayList<Player> merchants) {
        for (Player currentMerchant : merchants) {
            if (currentMerchant.equals(this)) {
                continue;
            } else {
                checkMerchant(currentMerchant);
                currentMerchant.putOnStand();
            }
        }
    }

    public void merchantRole(int round) {
        useLegalGood();

        if (super.getBag().isEmpty()) {
            useIllegalGood();
            setDeclaredGood(GoodsFactory.getInstance().getGoodsById(Constants.getAppleId()));
        }
    }

    final void useLegalGood() {
        // using a HashMap to store and sort the good
        Map<Goods, Integer> goodsFrequency = new HashMap<Goods, Integer>();

        int maxFreq = 0;
        int maxProfit = 0;
        Goods declared;

        LinkedList<Goods> currentGoods = super.getCurrentGoods();
        // System.out.println(getCurrentGoods().size());

        for (Goods currentGood : currentGoods) {
            if (goodsFrequency.containsKey(currentGood)) {
                goodsFrequency.put(currentGood, goodsFrequency.get(currentGood) + 1);
            } else {
                goodsFrequency.put(currentGood, 1);
            }
        }

//        System.out.println("DETERMINING GOODS");
//        //for (int i = 0; i < goodsFrequency.size(); ++i) {
//            for (Map.Entry<Goods, Integer> entry : goodsFrequency.entrySet()) {
//                System.out.println(entry.getKey().getId() + "  " + entry.getValue());
//            }
//        //}
        int maxId = 0;
        for (Map.Entry<Goods, Integer> entry : goodsFrequency.entrySet()) {
            int cProfit = entry.getKey().getProfit();
            if (entry.getKey().getType() == GoodsType.Legal) {
                if (maxFreq < entry.getValue()) {
                    maxFreq = entry.getValue();
                    setDeclaredGood(entry.getKey());
                    maxProfit = cProfit;
                    maxId = entry.getKey().getId();
                }
                if (maxFreq == entry.getValue()) {
                    if (maxProfit < cProfit) {
                        maxFreq = entry.getValue();
                        setDeclaredGood(entry.getKey());
                        maxProfit = cProfit;
                        maxId = entry.getKey().getId();
                    }
                    if (maxProfit == cProfit) {
                        if (maxId < entry.getKey().getId()) {
                            maxFreq = entry.getValue();
                            setDeclaredGood(entry.getKey());
                            maxProfit = cProfit;
                            maxId = entry.getKey().getId();
                        }
                    }
                }

            }
        }

        //pun in bag
        declared = super.getDeclaredGood();

        for (int i = 0; i < maxFreq; ++i) {
            this.addToBag(declared);
            this.dropGood(declared);
        }
    }

    final ArrayList<Goods> checkMostProfitable(int size) {
        // using a HashMap to store and sort the good
        // Map<Goods, Integer> goodsFrequency = new HashMap<Goods, Integer>();
        ArrayList<Goods> check = new ArrayList<>();
        // int maxFreq = 0;
        int maxProfit = 0;
        Goods declared;

        LinkedList<Goods> currentGoods = super.getCurrentGoods();
        currentGoods.sort((Goods g, Goods ga) -> ga.getId() - g.getId());
        currentGoods.sort((Goods g1, Goods g2) -> g2.getProfit() - g1.getProfit());

        for (int i = 0; i < size; ++i) {
            check.add(currentGoods.get(i));
        }

        return check;
    }

    final void useIllegalGood() {
        Goods compromise = null;

        for (Goods currentGood : super.getCurrentGoods()) {
            if (currentGood.getType() == GoodsType.Illegal &&
                    (compromise == null || compromise.getProfit() < currentGood.getProfit())) {
                compromise = currentGood;
            }
        }
        // System.out.println("COPROMISE" + compromise.getId());

        if (compromise != null) {
            addToBag(compromise);
            // setDeclaredGood();
            dropGood(compromise);
        }
    }

    @Override
    public void updateLeaderBoard(final ILeaderBoard visitor,
                                  final ArrayList<Map.Entry<String, Integer>> leaderBoard, int playerID) {
        visitor.update(this, leaderBoard, playerID);
    }
}

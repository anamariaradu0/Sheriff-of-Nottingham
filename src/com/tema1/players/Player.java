package com.tema1.players;

import com.tema1.goods.Goods;
import com.tema1.common.Constants;
import com.tema1.goods.GoodsType;
import com.tema1.common.Deck;
import com.tema1.goods.IllegalGoods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.LinkedList;

public abstract class Player {
    private Map<Goods, Integer> merchantStand;
    private LinkedList<Goods> currentGoods;
    private ArrayList<Goods> bag;
    private int totalCoins;
    private int bribe;
    private Goods declaredGood;

    public Player() {
        merchantStand = new HashMap<>();
        currentGoods = new LinkedList<>();
        bag = new ArrayList<>();

        totalCoins = Constants.getStartCoins();
        bribe = 0;
        declaredGood = null;
    }

    public Map<Goods, Integer> getMerchantStand() {
        return merchantStand;
    }

    // Getters + Setters
    protected final boolean hasBribe() {
        return bribe > 0;
    }

    protected final void returnBribe() {
        totalCoins += bribe;
        bribe = 0;
    }

    final int brb() {
        return bribe;
    }

    final int getBribe() {
        totalCoins -= bribe;
        return bribe;
    }

    final int getTotalCoins() { return totalCoins; }

    public final void emptyStand() {
        merchantStand.clear();
    }

    final void setDeclaredGood(final Goods declaredGood) {
        this.declaredGood = declaredGood;
    }

    final void setBribe(final int bribe) { this.bribe = bribe; }

    public final LinkedList<Goods> getCurrentGoods() {
        return currentGoods;
    }

    public final ArrayList<Goods> getBag() {
        return bag;
    }

    final Goods getDeclaredGood() {
        return declaredGood;
    }

    protected final void confiscateGood(Goods good) {
        bag.remove(good);
    }

    public final void drawGoods() {
        if (currentGoods.size() != 0) {
            currentGoods.clear();
        }
        currentGoods.addAll(Deck.getInstance().drawGoods(Constants.getMaxCards()));
    }

    final void putOnStand() {
        for (Goods currentGood : bag) {
            addToHashMap(currentGood);

            // se adauga bunurile bonus pentru cartile ilegale
            if (currentGood.getType() == GoodsType.Illegal) {
                IllegalGoods somegood = (IllegalGoods) currentGood;
                Map<Goods, Integer> illegalBonus = somegood.getIllegalBonus();
                for (Map.Entry<Goods, Integer> e : illegalBonus.entrySet()) {
                    // this.addCoins(e.getKey().getProfit());
                    for (int i = 0; i < e.getValue(); ++i) {
                        addToHashMap(e.getKey());
                    }
                }
                // merchantStand.putAll(illegalBonus);
            }
        }

        bag.clear();
    }

    private void addToHashMap(Goods currentGood) {
        if (merchantStand.get(currentGood) != null) { // daca deja exista pe taraba acest tip de bunuri
            merchantStand.put(currentGood, merchantStand.get(currentGood) + 1);
        } else { // daca nu EXISTA
            merchantStand.put(currentGood, 1);
        }
    }

    final void addToBag(final Goods good) {
        bag.add(good);
    }

    final void dropGood(final Goods good) {
        currentGoods.remove(good);
    }

    int checkMerchant(Player merchant){
        return 0;
    }

//    public void printMerchantStand() {
//        for (Map.Entry<Goods, Integer> entry : merchantStand.entrySet()) {
//            System.out.println(entry.getKey().getId() + " " + entry.getValue());
//        }
//    }

    public void merchantRole(int round) {}

    public void sheriffRole(final ArrayList<Player> merchants) {}

    public final void countGoods(final int[][] bonusTable, final int posInPlayers) {
//        System.out.println("MERCHANT STAND");
//        for (Map.Entry<Goods, Integer> cg : merchantStand.entrySet()) {
//            System.out.println(cg.getKey().getId() + " " + cg.getValue());
//        }
        for (Map.Entry<Goods, Integer> currentGood : merchantStand.entrySet()) {
            Goods currentGoodKey = currentGood.getKey();
            int count = currentGood.getValue();

            int id = currentGoodKey.getId();

            bonusTable[posInPlayers][id] = count;
        }
    }

    final int computePenalty() {
        int confiscatedCoins = 0;
        boolean isLiar = false;
        ArrayList<Goods> confiscatedGoods = new ArrayList<>();

//        System.out.println("THIS IS MY BAG");
//        for (int i = 0; i < bag.size(); ++i) {
//            System.out.println(bag.get(i).getId() + " ");
//        }


        for (Goods currentGood : bag) {
            // System.out.println(currentGood.getId() + "///////");
            if (currentGood.getType() == GoodsType.Illegal ||
                !(currentGood.equals(declaredGood))) {
                //
                // System.out.println(declaredGood.getId());
                isLiar = true;
                confiscatedCoins += currentGood.getPenalty();
                confiscatedGoods.add(currentGood);
                Deck.getInstance().addGood(currentGood);
            }
        }

        // System.out.println(isLiar);
        if (!isLiar) {
            for (Goods currentGood : bag) {
                confiscatedCoins -= currentGood.getPenalty();
            }
        }

        bag.removeAll(confiscatedGoods);
        // System.out.println(confiscatedGoods.size());
        return confiscatedCoins;
    }

//    public void printBag() {
//        for (int i = 0; i < bag.size(); ++i) {
//            System.out.print(bag.get(i).getId());
//        }
//    }

    public void removeCoins(final int coins) {
        totalCoins -= coins;
    }

    public void addCoins(final int coins) {
        totalCoins += coins;
    }

    public final int computeScore() {
        int score = totalCoins;
        // System.out.println(totalCoins + " COINS COMPUTE SCORE");

        // System.out.print(totalCoins + " ");
        for (Map.Entry<Goods, Integer> currItem : merchantStand.entrySet()) {
            score += (currItem.getKey().getProfit() * currItem.getValue());
        }

        // System.out.println(score);
        return score;
    }

    public abstract void updateLeaderBoard(ILeaderBoard visitor,
                                           ArrayList<Map.Entry<String, Integer>> leaderBoard, int playerID);

}

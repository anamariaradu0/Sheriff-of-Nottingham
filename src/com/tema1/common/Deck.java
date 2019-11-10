package com.tema1.common;

import com.tema1.goods.Goods;
import com.tema1.goods.GoodsFactory;

import java.util.LinkedList;
import java.util.List;

public final class Deck {
    private static LinkedList<Goods> deckFlow;
    private static final Deck DECK = new Deck();

    // Constructor
    public Deck() {
        deckFlow = new LinkedList<Goods>();
    }

    public static Deck getInstance() {
        return DECK;
    }

    public static LinkedList<Goods> getDeckFlow() { return deckFlow; }

    // Methods
    public Deck initializeDeck(final List<Integer> assets) {
        for (Integer asset : assets) {
            deckFlow.add(GoodsFactory.getInstance().getGoodsById(asset)); // le pune si aici
        }

        return this;
    }

    public void addGood(Goods good) {
        deckFlow.add(good);
    }

    public LinkedList<Goods> drawGoods(int goodsToGet) {
        LinkedList<Goods> playerGoods = new LinkedList<Goods>();
        // System.out.print("I DRAW THIS ");
        for (int i = 0; i < goodsToGet; ++i) {
            playerGoods.add(deckFlow.getFirst());
            //System.out.print(deckFlow.getFirst().getId() + " ");
            deckFlow.removeFirst();
        }
        //System.out.println();
        return playerGoods;
    }

}
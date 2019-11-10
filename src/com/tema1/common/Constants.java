package com.tema1.common;

public final class Constants {
    // add/delete any constants you think you may use
    private static final int MAX_SHERIFF = 5;
    private static final int MAX_CARDS = 10;
    private static final int DIFF = 10;
    private static final int MAX_SELECTED_CARDS = 8;
    private static final int MAX_CARD_TYPES = 15;
    private static final int START_COINS = 80;
    private static final int APPLE_ID = 0;
    private static final int LOWEST_COINS = 16;
    private static final int LITTLE_BRIBE = 5;
    private static final int BIG_BRIBE = 10;

    public static int getLowestCoins() {
        return LOWEST_COINS;
    }

    public static int getLittleBribe() {
        return LITTLE_BRIBE;
    }

    public static int getBigBribe() {
        return BIG_BRIBE;
    }

    public static int getMaxSheriff() {
        return MAX_SHERIFF;
    }

    public static int getMaxCards() {
        return MAX_CARDS;
    }

    public static int getMaxCardTypes() {
        return MAX_CARD_TYPES;
    }

    public static int getStartCoins() {
        return START_COINS;
    }

    public static int getMaxSelectedCards() {
        return MAX_SELECTED_CARDS;
    }

    public static int getAppleId() {
        return APPLE_ID;
    }

    public static int getDIFF() { return DIFF; }
}

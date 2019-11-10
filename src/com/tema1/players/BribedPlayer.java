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
        if (leftPlayer == null) {
            findPlayer(players);
        }

        if (leftPlayer != this) {
            checkMerchant(leftPlayer);
            leftPlayer.putOnStand();
        }

        if (rightPlayer != leftPlayer) {
            checkMerchant(rightPlayer);
            rightPlayer.putOnStand();
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
            int maxGoods = super.getTotalCoins() < Constants.getBigBribe() ?
                    Constants.getLowestCoins() : Constants.getMaxCards();

            super.setDeclaredGood(GoodsFactory.getInstance().getGoodsById(Constants.getAppleId()));
            illegals.sort((final Goods g1, final Goods g2) -> g2.getProfit() - g1.getProfit());

            for (int i = 0; i < maxGoods && i < illegals.size(); ++i) {
                Goods cGood = illegals.get(i);

                super.getBag().add(cGood);
                super.getCurrentGoods().remove(cGood);
            }

            super.setBribe(super.getBag().size() <= Constants.getLowestCoins() ? Constants.getLittleBribe() : Constants.getBigBribe());

        }
    }

    @Override
    public void updateLeaderBoard(final ILeaderBoard visitor,
                                  final ArrayList<Map.Entry<String, Integer>> leaderBoard, int playerID) {
        visitor.update(this, leaderBoard, playerID);
    }

}

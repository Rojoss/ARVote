package com.josroossien.arvote.reward;

import com.josroossien.arvote.ARVote;
import com.josroossien.arvote.reward.internal.Reward;

import java.util.List;
import java.util.Map;

public class MoneyReward extends Reward {

    public MoneyReward(String[] categories, float percentage, int value) {
        super(categories, percentage, value);
    }

    public MoneyReward(String[] categories, float percentage, int minVal, int maxVal) {
        super(categories, percentage, minVal, maxVal);
    }

    public MoneyReward(String[] categories, float percentage, List<Integer> values) {
        super(categories, percentage, values);
    }

    public MoneyReward(String[] categories, float percentage, Map<Integer, Double> valuesPerc, boolean addTogether) {
        super(categories, percentage, valuesPerc, addTogether);
    }


    @Override
    public String execute(String player) {
        int coins = getValue();

        ARVote.inst().getEconomy().depositPlayer(player, coins);

        return "" + coins + " Coins";
    }

}
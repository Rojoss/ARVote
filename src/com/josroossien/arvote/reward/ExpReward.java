package com.josroossien.arvote.reward;

import com.clashwars.cwcore.utils.ExpUtil;
import com.josroossien.arvote.reward.internal.Reward;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class ExpReward extends Reward {

    public ExpReward(String[] categories, float percentage, int value) {
        super(categories, percentage, value);
    }

    public ExpReward(String[] categories, float percentage, int minVal, int maxVal) {
        super(categories, percentage, minVal, maxVal);
    }

    public ExpReward(String[] categories, float percentage, List<Integer> values) {
        super(categories, percentage, values);
    }

    public ExpReward(String[] categories, float percentage, Map<Integer, Double> valuesPerc, boolean addTogether) {
        super(categories, percentage, valuesPerc, addTogether);
    }

    @Override
    public String execute(String player) {
        int xp = getValue();

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(player);
        if (offlinePlayer != null && offlinePlayer.isOnline()) {
            ExpUtil expUtil = new ExpUtil((Player)offlinePlayer);
            expUtil.setExp(expUtil.getCurrentExp() + xp);
        } else {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "queue " + player + " cmd exp give {PLAYER} " + xp);
        }

        return "" + xp + " XP";
    }

}